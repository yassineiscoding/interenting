// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.18;

contract BookingContract {
    struct Booking {
        address creator;
        bytes32 hash;
    }

    struct GetBooking {
        uint256 index;
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
    returns (GetBooking memory)
    {
        uint256 index = 0;

        for (uint256 i = 0; i < bookings.length; i++) {
            if (bookings[i].creator == owner) {
                index = i;
                break;
            }
        }

        return GetBooking(index, bookings[index].hash);
    }
}
