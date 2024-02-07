package com.cloudbees.train.receipt.model;

import com.cloudbees.train.receipt.model.builder.TrainReceiptBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TrainReceipt {
    private Integer id;
    private String fromStation;
    private String toStation;
    private User passenger;
    private BigDecimal price;
    private String currencyCode;
    private Integer seatNumber;
    private String section;

    public TrainReceipt(TrainReceiptBuilder builder){
        this.id = builder.getId();
        this.fromStation = builder.getFromStation();
        this.toStation = builder.getToStation();
        this.passenger = builder.getPassenger();
        this.price = builder.getPrice();
        this.currencyCode = builder.getCurrencyCode();
        this.section = builder.getSection();
        this.seatNumber = builder.getSeatNumber();
    }
}
