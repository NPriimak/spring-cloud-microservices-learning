package com.learning.deposit.exceptions;

public class DepositNotFoundException extends RuntimeException{

    public DepositNotFoundException(String message) {
        super(message);
    }
}
