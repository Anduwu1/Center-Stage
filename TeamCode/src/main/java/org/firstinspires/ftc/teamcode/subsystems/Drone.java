package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Drone {
    public static final String drone = "servoD";

    public static final float DRONE_READY = 0.38f;
    public static final float DRONE_LAUNCHED = 0.65f;

    public Servo droneServo = null;

    public void reset() {
        droneServo.setPosition(DRONE_READY);
    }

    public void launch() {
        droneServo.setPosition(DRONE_LAUNCHED);
    }

    public double getPosition() {
        return droneServo.getPosition();
    }
}
