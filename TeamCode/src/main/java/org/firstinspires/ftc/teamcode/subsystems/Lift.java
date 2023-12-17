package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Lift {
    public static String HARDWARE_NAME = "lift";

    private DcMotorEx motor;

    public Lift(DcMotorEx motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.motor = motor;
    }

    public void liftGoToPosition(int pos){
        motor.setTargetPosition(pos);
    }

    public int getLiftPos(){
        return motor.getCurrentPosition();
    }

    public boolean isLiftBusy(){
        return motor.isBusy();
    }

    public void setLiftPower(float s){
        motor.setPower(s);
    }
}
