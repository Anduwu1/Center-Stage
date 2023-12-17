package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Distance {
    public static final String HARDWARE_NAME_LEFT = "lSense";
    public static final String HARDWARE_NAME_RIGHT = "rSense";

    public static final float desiredDistance = 4;

    private DistanceSensor left;
    private DistanceSensor right;

    public Distance(HardwareMap hardwareMap) {
        this.left = hardwareMap.get(DistanceSensor.class, Distance.HARDWARE_NAME_LEFT);
        this.right =hardwareMap.get(DistanceSensor.class, Distance.HARDWARE_NAME_RIGHT);;
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
