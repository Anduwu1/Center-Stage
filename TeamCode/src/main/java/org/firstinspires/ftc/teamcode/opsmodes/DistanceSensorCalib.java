package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Distance;

@TeleOp(name = "Distance Sensor Calib")
public class DistanceSensorCalib extends LinearOpMode {
    Distance distance;

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();

        distance = new Distance(hardwareMap);

        while (opModeIsActive()) {
            double leftDistance = distance.getLeftDistance();
            double rightDistance = distance.getRightDistance();

            telemetry.addLine("left distance sensor: " + leftDistance + " in");
            telemetry.addLine("right distance sensor: " + rightDistance + " in");
            telemetry.update();
        }
    }
}
