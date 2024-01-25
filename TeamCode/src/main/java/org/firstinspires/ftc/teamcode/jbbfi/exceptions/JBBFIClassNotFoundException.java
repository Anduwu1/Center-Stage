package org.firstinspires.ftc.teamcode.jbbfi.exceptions;

public class JBBFIClassNotFoundException extends RuntimeException{
    public JBBFIClassNotFoundException(String name) {
        super("Could not find class with name "+ name + "!");
    }
}
