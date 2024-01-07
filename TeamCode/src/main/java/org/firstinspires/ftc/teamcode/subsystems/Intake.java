package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public static final String HARDWARE_NAME = "intake";
    private final DcMotor motor;

    public Intake(HardwareMap hardwareMap) {
        this.motor = hardwareMap.get(DcMotor.class, Intake.HARDWARE_NAME);
        this.motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setPower(double power) {
        motor.setPower(power);
    }

    public void slowlyEject() {
        motor.setPower(0.15);
    }

    public void stop() {
        motor.setPower(0);
    }
}
