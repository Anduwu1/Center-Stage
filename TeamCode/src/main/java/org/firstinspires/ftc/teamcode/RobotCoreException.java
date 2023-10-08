package org.firstinspires.ftc.teamcode;

public class RobotCoreException extends Exception {

    private static final long serialVersionUID = 1L;

    private Exception chainedException = null;

    public RobotCoreException(String message) {
        super(message);
    }

    public RobotCoreException(String message, Exception e) {
        super(message);
        chainedException = e;
    }

    public boolean isChainedException() {
        return (chainedException != null);
    }

    public Exception getChainedException() {
        return chainedException;
    }

}
