package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;

import java.util.ArrayList;
import java.util.List;

@Autonomous(group="drive")
public class AutoCLICKTHISTheSequel extends LinearOpMode {

    HardwareController hardwareController;

    SampleMecanumDrive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        hardwareController = new HardwareController(hardwareMap);
        waitForStart();
        movePixelToPos(1);
        while (!isStopRequested() && opModeIsActive()) ;
    }


    /*
        This needs to be fixed to be better
        AutoAction class maybe
        or just general cleanup
     */
    private void movePixelToPos(int i) {
        Trajectory moveForward = drive.trajectoryBuilder(new Pose2d()).forward(25).build();
        // Go to position
        switch(i){
            // Left
            case 1:
                Trajectory goLeft = drive.trajectoryBuilder(moveForward.end()).strafeLeft(13).build();
                drive.followTrajectory(moveForward);
                drive.followTrajectory(goLeft);
                hardwareController.servoMove(0.7f, HardwareController.Servo_Type.DOOR_SERVO);
                Trajectory moveBack = drive.trajectoryBuilder(goLeft.end()).back(6).build();
                drive.followTrajectory(moveBack);
                break;
            // Center
            case 2:
                drive.followTrajectory(moveForward);
                hardwareController.servoMove(0.7f, HardwareController.Servo_Type.DOOR_SERVO);
                Trajectory moveBackCenter = drive.trajectoryBuilder(moveForward.end()).back(6).build();
                drive.followTrajectory(moveBackCenter);
                break;
            // Right
            case 3:
                Trajectory goRight = drive.trajectoryBuilder(moveForward.end()).strafeRight(13).build();
                drive.followTrajectory(moveForward);
                drive.followTrajectory(goRight);
                hardwareController.servoMove(0.7f, HardwareController.Servo_Type.DOOR_SERVO);
                Trajectory moveBackRight = drive.trajectoryBuilder(goRight.end()).back(6).build();
                drive.followTrajectory(moveBackRight);
                break;
            default:
                break;
        }
    }
}
