// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.8.18;

/*
 * Booking representation in the contract
 */
    struct Booking {
        uint256 id;
        bytes32 hash;
        bool isCheckedIn;
        bool isCheckedOut;
    }
