package org.firstinspires.ftc.teamcode.opsmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.resources.HardwareController;

@TeleOp(name="LiftCalib")
public class LiftCalibration extends LinearOpMode {

    HardwareController hardwareController;

    float position = 0.0f;

    @Override
    public void runOpMode() throws InterruptedException {
        hardwareController = new HardwareController(hardwareMap);
        while(opModeIsActive()){
            position += gamepad1.left_stick_y;

            // make this better if you want
            // (awesome ap csa question)
            if(position > 30.0f){
                position = 30.0f;
            }else if(position < 0.0f){
                position = 0.0f;
            }

            hardwareController.liftGoToPosition((int)position);

            sleep(250);

            telemetry.addData("Running to",  " %7d", position);
            telemetry.addData("Currently at",  " at %7d", hardwareController.getLiftPos());
            telemetry.update();
        }
    }
}
