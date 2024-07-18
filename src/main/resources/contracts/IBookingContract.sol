// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.18;

import "contracts/Booking.sol";

abstract contract IBookingContract {
    function addBooking(uint256 id, string memory booking)
    public
    virtual
    returns (uint256);

    function getBookingByHash(string memory hash)
    public
    view
    virtual
    returns (Booking memory);

    function updateBooking(uint256 id, string memory newHash) public virtual;

    function cancelBooking(string memory id) public virtual;

    function checkIn(uint256 bookingId) public virtual;

    function checkOut(uint256 bookingId) public virtual;
}
