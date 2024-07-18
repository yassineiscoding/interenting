// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.18;

contract BookingContract {
    /*
     * Booking representation in the contract
     */
    struct Booking {
        uint256 id;
        bytes32 hash;
        bool isCheckedIn;
        bool isCheckedOut;
    }

    // The list of saved bookings to manipulate them
    Booking[] private bookings;

    // Creator / owner of the booking, the one who rented / purchased the property
    address private bookingCreator;

    // Helpful events for the eventual frontend app
    event BookingAdded();
    event BookingUpdated();
    event BookingCancelled();
    event CheckedIn(uint256 id);
    event CheckedOut(uint256 id);

    constructor() {
        bookingCreator = msg.sender;
    }

    // check if the booking is free
    modifier isNotBooked(uint256 id) {
        require(
            (id > bookings.length) ||
            (id < bookings.length &&
                !bookings[id].isCheckedIn &&
                !bookings[id].isCheckedOut)
        );
        _;
    }

    // check if the booking has been checked in
    modifier isBookingCheckedIn(uint256 id) {
        require(bookings[id].isCheckedIn);
        _;
    }

    // check if the booking has been checked out
    modifier isBookingCheckedOut(uint256 id) {
        require(bookings[id].isCheckedOut);
        _;
    }

    // Save a booking to manage it
    function addBooking(uint256 id, string memory booking)
    public
    returns (uint256)
    {
        bookings.push(
            Booking(id, keccak256(abi.encodePacked(booking)), false, false)
        );

        emit BookingAdded();

        return bookings.length - 1;
    }

    // find the booking by its hash
    function getBookingByHash(string memory hash)
    public
    view
    returns (Booking memory)
    {
        uint256 index = 0;

        for (uint256 i = 0; i < bookings.length; i++) {
            if (bookings[i].hash == keccak256(abi.encodePacked(hash))) {
                index = i;
                break;
            }
        }

        return bookings[index];
    }

    // update the booking's hash to maintain integrity
    function updateBooking(uint256 id, string memory newHash) public {
        bookings[id].hash = keccak256(abi.encodePacked(newHash));

        emit BookingUpdated();
    }

    // remove the booking
    function cancelBooking(string memory id) public {
        uint256 index = 0;

        for (; index < bookings.length; index++) {
            if (bookings[index].hash == keccak256(abi.encodePacked(id))) {
                break;
            }
        }

        // Move the last element into the place to delete
        bookings[index] = bookings[bookings.length - 1];

        // Remove the last element
        bookings.pop();
    }

    // set the checkin status and emit the event
    function checkIn(uint256 bookingId) public isNotBooked(bookingId) {
        require(!bookings[bookingId].isCheckedIn, "Already checked in");

        bookings[bookingId].isCheckedIn = true;

        emit CheckedIn(bookingId);
    }

    // set the checkout status and emit the event
    function checkOut(uint256 bookingId) public isBookingCheckedIn(bookingId) {
        require(bookings[bookingId].isCheckedIn, "Not checked in");

        require(!bookings[bookingId].isCheckedOut, "Already checked out");

        bookings[bookingId].isCheckedOut = true;
        bookings[bookingId].isCheckedIn = false;

        emit CheckedOut(bookingId);
    }
}
