package org.firstinspires.ftc.teamcode.resources;

/**
 * Basically I'm too lazy to make a system that rotates without a
 * previous path so if that ever happens, we just throw this exception
 */
public class RotateWithoutPreviousPathException extends RuntimeException {
    public RotateWithoutPreviousPathException() {
        super("RoadRunnerHelper::Rotate called without a previous path!");
    }

    // Why use it, idk lamo
    public RotateWithoutPreviousPathException(String message) {
        super(message);
    }
}
