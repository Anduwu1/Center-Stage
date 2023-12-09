package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class Drone {
    public static final String drone = "servoD";

    public static final float DRONE_READY = 0f;
    public static final float DRONE_LAUNCHED = 1f;

    public Servo droneServo = null;

    public void init() {
        droneServo.setPosition(DRONE_READY);
    }

    public void launch() {
        droneServo.setPosition(DRONE_LAUNCHED);
    }
}
