package com.nikhil.springboot.AtithiStay.exceptions;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message){
        super(message);
    }
}
