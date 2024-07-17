// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.18;

// Compile with remix for remote imports to work - otherwise keep precompiles locally
import "https://github.com/hashgraph/hedera-smart-contracts/blob/main/contracts/system-contracts/hedera-token-service/HederaTokenService.sol";
import "https://github.com/hashgraph/hedera-smart-contracts/blob/main/contracts/system-contracts/hedera-token-service/IHederaTokenService.sol";

contract BookingContract is HederaTokenService {
    struct Booking {
        address creator;
        bytes32 hash;
    }

    Booking[] private bookings;

    address private bookingCreator;

    event BookingAdded();
    event BookingCancelled();
    event CheckedIn(address owner);
    event CheckedOut(address owner);

    constructor() {
        bookingCreator = msg.sender;
    }

    modifier onlyAllowed() {
        require(
            msg.sender == bookingCreator,
            "Only allowed parties can execute this contract"
        );
        _;
    }

    function addBooking(string memory booking) public onlyAllowed {
        bookings.push(
            Booking(msg.sender, keccak256(abi.encodePacked(booking)))
        );
        emit BookingAdded();
    }

    function cancelBooking(address owner) public onlyAllowed {
        uint256 index = 0;

        for (; index < bookings.length; index++) {
            if (bookings[index].creator == owner) {
                break;
            }
        }

        // Move the last element into the place to delete
        bookings[index] = bookings[bookings.length - 1];
        // Remove the last element
        bookings.pop();
    }

    function getBookingByOwner(address owner)
    public
    view
    onlyAllowed
    returns (uint256, bytes32)
    {
        uint256 index = 0;

        for (uint256 i = 0; i < bookings.length; i++) {
            if (bookings[i].creator == owner) {
                index = i;
                break;
            }
        }

        return (index, bookings[index].hash);
    }

    //============================================
    // GETTING HBAR TO THE CONTRACT
    //============================================
    receive() external payable {}

    fallback() external payable {}

    function tokenAssociate(address _account, address _htsToken)
    external
    payable
    {
        require(msg.value > 2000000000, "Send more HBAR");

        int256 response = HederaTokenService.associateToken(
            _account,
            _htsToken
        );
        if (response != HederaResponseCodes.SUCCESS) {
            revert("Token association failed");
        }
    }

    //============================================
    // GETTING HBAR FROM THE CONTRACT
    //============================================
    function transferHbar(address payable _receiverAddress, uint256 _amount)
    public
    {
        _receiverAddress.transfer(_amount);
    }

    function sendHbar(address payable _receiverAddress, uint256 _amount)
    public
    {
        require(_receiverAddress.send(_amount), "Failed to send Hbar");
    }

    function callHbar(address payable _receiverAddress, uint256 _amount)
    public
    {
        (bool sent, ) = _receiverAddress.call{value: _amount}("");
        require(sent, "Failed to send Hbar");
    }

    //============================================
    // CHECKING THE HBAR BALANCE OF THE CONTRACT
    //============================================
    function getBalance() public view returns (uint256) {
        return address(this).balance;
    }
}
