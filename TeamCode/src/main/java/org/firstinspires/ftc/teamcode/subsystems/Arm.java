package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    public static final String HARDWARE_NAME = "servoA";

    private static final float POSITION_UP = 0.63f;
    private static final float POSITION_DOWN = 0.275f;
    private static final float POSITION_HOVER = 0.295f;

    private final Servo servo;
    private double position = POSITION_DOWN;
    private boolean isUp;

    public Arm(HardwareMap hardwareMap) {
        this.servo = hardwareMap.get(Servo.class, Arm.HARDWARE_NAME);
        this.isUp = false;
    }

    public void moveToUpPosition() {
        position = POSITION_UP;
        servo.setPosition(position);
        isUp = true;
    }

    public void moveToDownPosition() {
        position = POSITION_DOWN;
        servo.setPosition(position);
        isUp = false;
    }

    public void moveToHoverPosition() {
        position = POSITION_HOVER;
        servo.setPosition(position);
        isUp = false;
    }

    /**
     * Is the arm in the up position?
     */
    public boolean isUp() {
        return isUp;
    }

    /**
     * Adjust the position of the servo by an incremental amount.
     * The position will be clamped to the min and max we have set for the servo.
     */
    public void adjustPosition(double adjustment) {
        // Manual control of the bucket
        position += adjustment;

        // Clamps the bucket servo position
        if(position > POSITION_UP)
            position = POSITION_UP;
        if(position < POSITION_DOWN)
            position = POSITION_DOWN;

        servo.setPosition(position);
    }

}
