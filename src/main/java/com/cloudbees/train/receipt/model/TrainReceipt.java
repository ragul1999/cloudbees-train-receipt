package com.cloudbees.train.receipt.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrainReceipt {
    private Integer id;
    private String fromStation;
    private String toStation;
    private User passenger;
    private BigDecimal price;
    private String currencyCode;
    private Integer seatNumber;
    private String section;
}
