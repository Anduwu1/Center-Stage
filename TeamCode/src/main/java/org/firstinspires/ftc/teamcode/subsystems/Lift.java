package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class Lift {
    public static String HARDWARE_NAME = "lift";
    private static int TOP_POSITION = 1550;

    private DcMotorEx motor;
    private int position = 1;
    private boolean runOnce = false;

    public Lift(HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, Lift.HARDWARE_NAME);
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void adjustLift(int increment) {
        if (!runOnce) {
            motor.setTargetPosition(1);
            runOnce = true;
        }
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(1.0);
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

    public int getTargetPos() {
        return position;
    }

    public double getLiftCurrentDraw() {
        return motor.getCurrent(CurrentUnit.AMPS);
    }
}
