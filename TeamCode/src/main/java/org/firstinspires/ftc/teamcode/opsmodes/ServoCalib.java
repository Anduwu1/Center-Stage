package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.resources.HardwareController;

public class ServoCalib extends LinearOpMode {


    HardwareController hardwareController = new HardwareController(hardwareMap);
    Robot robot = new Robot(AutonomousOpsMode.Alliance.BLUE_ALLIANCE, AutonomousOpsMode.StartPos.BACKSTAGE, hardwareMap);

    // Float
    float armPos = 0.0f, bucketPos = 0.0f, doorPos = 0.0f;

    HardwareController.Servo_Type cur = HardwareController.Servo_Type.ARM_SERVO;

    boolean changeDown = false;

    @Override
    public void runOpMode() throws InterruptedException {

        // Switch type
        if(gamepad1.dpad_right){
            if(changeDown = false){
                // There is a WAY better way to do this
                // but im lazy so
                if(cur == HardwareController.Servo_Type.ARM_SERVO){
                    cur = HardwareController.Servo_Type.BUCKET_SERVO;
                }
                if(cur == HardwareController.Servo_Type.BUCKET_SERVO){
                    cur = HardwareController.Servo_Type.DOOR_SERVO;
                }
                if(cur == HardwareController.Servo_Type.DOOR_SERVO){
                    cur = HardwareController.Servo_Type.ARM_SERVO;
                }
            }
            changeDown = true;
        }else{
            changeDown = false;
        }

        telemetry.addData("ARM", "%f", armPos);
        telemetry.addData("BUCKET", "%f", bucketPos);
        telemetry.addData("DOOR", "%f", doorPos);
        telemetry.addLine("=======================");
        switch (cur){
            case ARM_SERVO:
                if(gamepad1.dpad_up) armPos += 0.1f;
                if(gamepad1.dpad_down) armPos -= 0.1f;
                hardwareController.servoMove(armPos, cur);
                telemetry.addLine("CURRENT IS ARM");
                break;
            case DOOR_SERVO:
                if(gamepad1.dpad_up) doorPos += 0.1f;
                if(gamepad1.dpad_down) doorPos -= 0.1f;
                hardwareController.servoMove(doorPos, cur);
                telemetry.addLine("CURRENT IS BUCKET");
                break;
            case BUCKET_SERVO:
                if(gamepad1.dpad_up) bucketPos += 0.1f;
                if(gamepad1.dpad_down) bucketPos -= 0.1f;
                hardwareController.servoMove(bucketPos, cur);
                telemetry.addLine("CURRENT IS DOOR");
                break;
        }
        telemetry.update();
    }
}
