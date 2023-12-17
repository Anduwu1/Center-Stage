package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Distance {
    public static final String HARDWARE_NAME_LEFT = "lSense";
    public static final String HARDWARE_NAME_RIGHT = "rSense";

    public static final float desiredDistance = 4;

    private DistanceSensor left;
    private DistanceSensor right;

    public Distance(DistanceSensor left, DistanceSensor right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Get the distance from the right sensor (in inches)
     */
    public double getRightDistance() {
        return right.getDistance(DistanceUnit.INCH);
    }

    /**
     * Get the distance from the left sensor (in inches)
     */
    public double getLeftDistance() {
        return left.getDistance(DistanceUnit.INCH);
    }
}
