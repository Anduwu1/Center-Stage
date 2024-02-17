package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This class represents the servo that rotates the bucket.
 */
public class Bucket {
    public static final String HARDWARE_NAME = "servoR";

    private static final float POSITION_DROP = 0;
    private static final float POSITION_LOW_DROP = 0.1f;
    private static final float POSITION_AUTO_DROP = 0.192f;
    private static final float POSITION_INTAKE = 0.47f;

    private final Servo servo;
    private double position = POSITION_INTAKE;
    private boolean isIntakePosition;

    public Bucket(HardwareMap hardwareMap) {
        this.servo = hardwareMap.get(Servo.class, Bucket.HARDWARE_NAME);
        this.isIntakePosition = true;
        servo.setPosition(position); //init the position to intake
    }

    public void adjustPosition(double adjustment) {
        // Manual control of the bucket
        position += adjustment;

        // Clamps the bucket servo position
        if(position > Bucket.POSITION_DROP)
            position = Bucket.POSITION_DROP;
        if(position < Bucket.POSITION_INTAKE)
            position = Bucket.POSITION_INTAKE;

        servo.setPosition(position);
    }

    /**
     * If the claw is closed, open it. If the claw is open, close it
     */
    public void toggleBucket() {
        if (isIntakePosition)
            moveToDropPosition();
        else
            moveToIntakePosition();
    }

    /**
     * Close the claw, preventing a pixel from dropping out of the bucket
     */
    public void moveToIntakePosition() {
        position = POSITION_INTAKE;
        servo.setPosition(position);
        isIntakePosition = true;
    }

    /**
     * Open the claw, allowing a pixel to drop out of the bucket
     */
    public void moveToDropPosition() {
        position = POSITION_DROP;
        servo.setPosition(position);
        isIntakePosition = false;
    }

    /**
     * Open the bucket stuff for the Autonomus
     */
    public void moveToAutoDropPos(){
        position = POSITION_AUTO_DROP;
        servo.setPosition(position);
        isIntakePosition = false;
    }

    public void moveToLowDropPosition() {
        position = POSITION_LOW_DROP;
        servo.setPosition(position);
        isIntakePosition = false;
    }

    /**
     * Similar to moveToDropPosition, but blocks and doesn't return until the movement is completed
     */
    public void moveToDropPositionAndWait() {
        position = POSITION_DROP;
        servo.setPosition(position);
        isIntakePosition = false;
        while (Math.abs(servo.getPosition() - position) > 5) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
