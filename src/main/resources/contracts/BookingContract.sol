// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

import "./Booking.sol";
import "./IBookingContract.sol";

contract BookingContract is IBookingContract {

    mapping(address => Booking) private bookings;

    address private bookingCreator;
    address private propertyOwner;

    modifier onlyAllowed() {
        require(
            msg.sender == bookingCreator || msg.sender == propertyOwner,
            "Only allowed parties can execute this contract"
        );
        _;
    }

    function addBooking(Booking memory booking) override public onlyAllowed {
        bookings[msg.sender] = booking;
    }

    function getBookingByOwner(address owner) public onlyAllowed override view returns (Booking) {
        return bookings[msg.sender];
    }

}
