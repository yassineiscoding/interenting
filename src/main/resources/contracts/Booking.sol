// SPDX-License-Identifier: MIT

pragma solidity ^0.8.18;

    enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    struct Booking {
        uint64 id;
        uint64 propertyId;
        uint64 guestId;
        string checkIn;
        string checkOut;
        BookingStatus status;
        string smartContractId;
    }
