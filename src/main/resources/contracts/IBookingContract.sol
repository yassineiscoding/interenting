// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "./Booking.sol";

abstract contract IBookingContract {

    function addBooking(Booking memory booking) public;

    function getBookingByOwner(address owner) public view returns (Booking);
}
