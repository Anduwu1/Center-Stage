package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.HardwareController;

import java.util.Arrays;
import java.util.List;

@TeleOp(name = "Wheel Calib")
public class WheelMotorCalib extends LinearOpMode {
    HardwareController hardwareController;

    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx rightBackDrive = null;

    @Override
    public void runOpMode() throws InterruptedException {
        leftFrontDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LFDRIVE_MOTOR);
        leftBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LBDRIVE_MOTOR);
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RFDRIVE_MOTOR);
        rightBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RBDRIVE_MOTOR);
        List<DcMotorEx> motors = Arrays.asList(leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive);

        //set mode
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        waitForStart();

        hardwareController = new HardwareController(hardwareMap);

        while (opModeIsActive()) {
            for (DcMotorEx motor : motors) {
                motor.setTargetPosition(384);
            }
            double leftDistance = hardwareController.getLeftDistance();
            double rightDistance = hardwareController.getRightDistance();

            telemetry.addLine("left distance sensor: " + leftDistance + " in");
            telemetry.addLine("right distance sensor: " + rightDistance + " in");
            telemetry.update();
        }
    }
}
