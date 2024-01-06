package org.firstinspires.ftc.teamcode.resources;

public class RotateWithoutPreviousPathException extends RuntimeException {
    public RotateWithoutPreviousPathException() {
        super("RoadRunnerHelper::Rotate called without a previous path!");
    }

    public RotateWithoutPreviousPathException(String message) {
        super(message);
    }
}
