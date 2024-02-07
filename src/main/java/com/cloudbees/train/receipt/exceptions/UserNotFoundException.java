package com.cloudbees.train.receipt.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message){
        super(message);
    }
}
