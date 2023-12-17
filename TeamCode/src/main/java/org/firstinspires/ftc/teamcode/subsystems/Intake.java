package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    public static final String HARDWARE_NAME = "intake";
    private final DcMotor motor;

    public Intake(DcMotor motor) {
        this.motor = motor;
    }

    public void setPower(double power) {
        motor.setPower(power);
    }
}
