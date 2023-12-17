package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public static String HARDWARE_NAME = "lift";

    private DcMotorEx motor;

    public Lift(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, Lift.HARDWARE_NAME);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
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
