package com.fidness.exceptions;
public class FidnessException extends Exception {
    public FidnessException(String message) { super(message); }
    public FidnessException(String message, Throwable cause) { super(message, cause); }
}
