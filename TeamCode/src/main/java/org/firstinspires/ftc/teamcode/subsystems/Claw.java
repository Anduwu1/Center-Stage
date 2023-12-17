package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    public static final String HARDWARE_NAME = "servoT";

    private static float POSITION_OPEN = 0.7f;
    private static float POSITION_CLOSED = 1.0f;
    private boolean isOpen; //used to track the position of the claw

    private final Servo servo;

    public Claw(HardwareMap hardwareMap) {
        this.servo = hardwareMap.get(Servo.class, Claw.HARDWARE_NAME);
        this.isOpen = false;
    }

    /**
     * If the claw is closed, open it. If the claw is open, close it
     */
    public void toggleClaw() {
        if (isOpen)
            close();
        else
            open();
    }

    /**
     * Close the claw, preventing a pixel from dropping out of the bucket
     */
    public void close() {
        servo.setPosition(POSITION_CLOSED);
        isOpen = false;
    }

    /**
     * Open the claw, allowing a pixel to drop out of the bucket
     */
    public void open() {
        servo.setPosition(POSITION_OPEN);
        isOpen = true;
    }
}
