package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.resources.HardwareController;

@TeleOp(name="Servo Calib")
public class ServoCalib extends LinearOpMode {
    HardwareController hardwareController;
    Robot robot;

    // Float
    float armPos = 0.75f, bucketPos = 0.0f, doorPos = 0.0f;

    HardwareController.Servo_Type cur = HardwareController.Servo_Type.ARM_SERVO;

    boolean changeDown = false;

    float diff = 0.0001f;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();


        robot = new Robot(AutonomousOpsMode.Alliance.BLUE_ALLIANCE, AutonomousOpsMode.StartPos.BACKSTAGE, hardwareMap);
        hardwareController = new HardwareController(hardwareMap, robot);
        while (opModeIsActive()) {

            // Switch type
            if (gamepad1.dpad_right) {
                if (changeDown == false) {
                    // There is a WAY better way to do this
                    // but im lazy so
                    if (cur == HardwareController.Servo_Type.ARM_SERVO) {
                        cur = HardwareController.Servo_Type.BUCKET_SERVO;
                    }
                    else if (cur == HardwareController.Servo_Type.BUCKET_SERVO) {
                        cur = HardwareController.Servo_Type.DOOR_SERVO;
                    }
                    else if (cur == HardwareController.Servo_Type.DOOR_SERVO) {
                        cur = HardwareController.Servo_Type.ARM_SERVO;
                    }
                }
                changeDown = true;
            } else {
                changeDown = false;
            }

            if(armPos < 0.0f)armPos = 0.7f;
            if(armPos > 1.0f)armPos = 1.0f;
            if(bucketPos < 0.0f)bucketPos = 0.0f;
            if(bucketPos > 1.0f)bucketPos = 1.0f;
            if(doorPos < 0.0f)doorPos = 0.0f;
            if(doorPos > 1.0f)doorPos = 1.0f;


            telemetry.addData("ARM", "%f", armPos);
            telemetry.addData("BUCKET", "%f", bucketPos);
            telemetry.addData("DOOR", "%f", doorPos);
            telemetry.addLine("=======================");
            switch (cur) {
                case ARM_SERVO:

                    if (gamepad1.dpad_up) armPos += diff;
                    if (gamepad1.dpad_down) armPos -= diff;
                    hardwareController.servoMove(armPos, cur);
                    telemetry.addLine("CURRENT IS ARM");
                    break;
                case DOOR_SERVO:
                    if (gamepad1.dpad_up) doorPos += diff;
                    if (gamepad1.dpad_down) doorPos -= diff;
                    hardwareController.servoMove(doorPos, cur);
                    telemetry.addLine("CURRENT IS DOOR");
                    break;
                case BUCKET_SERVO:
                    if (gamepad1.dpad_up) bucketPos += diff;
                    if (gamepad1.dpad_down) bucketPos -= diff;
                    hardwareController.servoMove(bucketPos, cur);
                    telemetry.addLine("CURRENT IS BUCKET");
                    break;
            }
            telemetry.update();
        }
    }
}
