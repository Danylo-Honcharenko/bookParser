package com.perser;

public enum Message {
    FILE_NOT_FOUND("File not found!");

    private final String message;

    Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
