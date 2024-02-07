package com.cloudbees.train.receipt.dto;

import com.cloudbees.train.receipt.model.User;

public record SeatAllocationDTO(User passenger, Integer seatNumber) {
}
