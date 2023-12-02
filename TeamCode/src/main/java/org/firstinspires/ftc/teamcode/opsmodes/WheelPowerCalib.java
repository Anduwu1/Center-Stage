package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.HardwareController;

import java.util.Arrays;
import java.util.List;

@TeleOp(name = "Wheel Calib")
public class WheelPowerCalib extends LinearOpMode {
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



        waitForStart();
        ElapsedTime runtime = new ElapsedTime();
        hardwareController = new HardwareController(hardwareMap);

        for(DcMotorEx motor : motors){
            motor.setPower(1);
        }



        while (opModeIsActive()) {
            if(runtime.seconds() > 5) {
                for (DcMotorEx motor : motors) {
                    motor.setPower(0);
                }
            }
            telemetry.addData("leftFrontDrive", leftFrontDrive.getCurrentPosition());
            telemetry.addData("leftBackDrive", leftBackDrive.getCurrentPosition());
            telemetry.addData("rightFrontDrive", rightFrontDrive.getCurrentPosition());
            telemetry.addData("rightBackDrive", rightBackDrive.getCurrentPosition());

            telemetry.update();

        }
    }
}
