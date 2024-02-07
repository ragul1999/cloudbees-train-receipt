package com.cloudbees.train.receipt.model.builder;

import com.cloudbees.train.receipt.model.TrainReceipt;
import com.cloudbees.train.receipt.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrainReceiptBuilder {
    private Integer id;
    private String fromStation;
    private String toStation;
    private User passenger;
    private BigDecimal price;
    private String currencyCode;
    private Integer seatNumber;
    private String section;

    public TrainReceiptBuilder setId(Integer id) {
        this.id = id;
        return this;
    }

    public TrainReceiptBuilder setFromStation(String fromStation) {
        this.fromStation = fromStation;
        return this;
    }

    public TrainReceiptBuilder setToStation(String toStation) {
        this.toStation = toStation;
        return this;
    }

    public TrainReceiptBuilder setPassenger(User passenger) {
        this.passenger = passenger;
        return this;
    }

    public TrainReceiptBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public TrainReceiptBuilder setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public TrainReceiptBuilder setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public TrainReceiptBuilder setSection(String section) {
        this.section = section;
        return this;
    }

    public TrainReceipt build(){
        return new TrainReceipt(this);
    }

}
