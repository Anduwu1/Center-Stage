package org.firstinspires.ftc.teamcode.jbbfi.exceptions;

public class JBBFIScriptNotFoundException extends RuntimeException{
    public JBBFIScriptNotFoundException(String name) {
        super("Could not find script at "+ name + "!");
    }
}
