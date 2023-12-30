package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Lift {
    public static String HARDWARE_NAME = "lift";
    private static int TOP_POSITION = 1550;
    private static int LIFTED_POSITION = 1400;

    private DcMotorEx motor;
    private int position;
    private boolean velocitySet = false;

    public Lift(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, Lift.HARDWARE_NAME);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void liftGoToPosition(int pos){
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(pos);
    }

    public void liftToTop() {
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(TOP_POSITION);
    }

    public void liftRobot() {
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setTargetPosition(LIFTED_POSITION);
    }

    public void adjustLift(int increment) {
//        if (!velocitySet) {
//            motor.setVelocity(300);
//            velocitySet = true;
//        }
        position -= increment;
        if (position > TOP_POSITION)
            position = TOP_POSITION;
        if (position < 0)
            position = 0;
        motor.setTargetPosition(position);
    }

    public int getLiftPos(){
        return motor.getCurrentPosition();
    }

    public boolean isLiftBusy(){
        return motor.isBusy();
    }

    public int getTargetPos() {
        return position;
    }

    public void setLiftPower(float s){
        motor.setPower(s);
    }

    public double getLiftCurrentDraw() { return motor.getCurrent(CurrentUnit.AMPS); }
}
