package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.resources.OpenCVManager;
import org.firstinspires.ftc.teamcode.resources.Pipelines.AutoPipeLine;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Claw;

@Autonomous(group="drive")
public class AutonomousOpsMode extends LinearOpMode {

    Claw claw;

    SampleMecanumDrive drive;

    OpenCVManager camMan;
    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        claw = new Claw(hardwareMap);
        camMan = new OpenCVManager(hardwareMap);
        AutoPipeLine pipe = new AutoPipeLine();
        camMan.setPipeline(pipe);
        waitForStart();
       // movePixelToPos(1);
        while (!isStopRequested() && opModeIsActive()){
            telemetry.addLine("WOW!");
            telemetry.addData("X:","%f", pipe.getX());
            telemetry.update();
        }
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
                claw.open();
                Trajectory moveBack = drive.trajectoryBuilder(goLeft.end()).back(6).build();
                drive.followTrajectory(moveBack);
                break;
            // Center
            case 2:
                drive.followTrajectory(moveForward);
                claw.open();
                Trajectory moveBackCenter = drive.trajectoryBuilder(moveForward.end()).back(6).build();
                drive.followTrajectory(moveBackCenter);
                break;
            // Right
            case 3:
                Trajectory goRight = drive.trajectoryBuilder(moveForward.end()).strafeRight(13).build();
                drive.followTrajectory(moveForward);
                drive.followTrajectory(goRight);
                claw.open();
                Trajectory moveBackRight = drive.trajectoryBuilder(goRight.end()).back(6).build();
                drive.followTrajectory(moveBackRight);
                break;
            default:
                break;
        }
    }
}
