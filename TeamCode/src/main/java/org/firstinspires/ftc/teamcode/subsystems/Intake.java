package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Intake {
    public static final String HARDWARE_NAME = "intake";
    private final DcMotor motor;

    public Intake(DcMotor motor) {
        this.motor = motor;
        this.motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }
}
