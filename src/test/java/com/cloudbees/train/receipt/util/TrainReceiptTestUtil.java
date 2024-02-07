package com.cloudbees.train.receipt.util;

import com.cloudbees.train.receipt.model.TrainReceipt;
import com.cloudbees.train.receipt.model.User;
import com.cloudbees.train.receipt.model.builder.TrainReceiptBuilder;

public class TrainReceiptTestUtil {

    private TrainReceiptTestUtil(){}

    public static TrainReceipt getTraineeReceipt(){
        TrainReceiptBuilder trainReceiptBuilder = new TrainReceiptBuilder()
                .setFromStation("Chennai").setToStation("Bangalore").setPassenger(getPassenger())
                .setSeatNumber(1).setSection("X");
        return trainReceiptBuilder.build();
    }

    public static User getPassenger(){
        return new User(1, "test", "user", "test@yopmail.com");
    }
}
