package com.treelevel.app.training.controller.exception;

public class ProgramNotFoundException extends RuntimeException {
    public ProgramNotFoundException(Long id) {
        super("Could not find programm " + id);
    }
}
