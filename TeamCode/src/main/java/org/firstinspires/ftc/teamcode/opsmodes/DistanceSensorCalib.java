package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.resources.HardwareController;

@TeleOp(name = "Distance Sensor Calib")
public class DistanceSensorCalib extends LinearOpMode {
    HardwareController hardwareController;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        hardwareController = new HardwareController(hardwareMap);

        while (opModeIsActive()) {
            double leftDistance = hardwareController.getLeftDistance();
            double rightDistance = hardwareController.getRightDistance();

            telemetry.addLine("left distance sensor: " + leftDistance + " in");
            telemetry.addLine("right distance sensor: " + rightDistance + " in");
            telemetry.update();
        }
    }
}
