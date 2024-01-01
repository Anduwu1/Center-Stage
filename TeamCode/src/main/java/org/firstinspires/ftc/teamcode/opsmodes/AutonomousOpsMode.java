package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
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

        int pixelPos = 0;
        while(!isStarted()){
            // Get pos
            pixelPos = pipe.getX();
            telemetry.addData("X:","%d", pixelPos);
            telemetry.update();

        }
        String pos = "None";
        // Move to position based on pixelPos
        if(pixelPos > RobotSettings.PIXEL_CENTER){
            pos = "RIGHT";
            movePixelToPos(3);
        }else if(pixelPos > RobotSettings.PIXEL_LEFT){
            pos = "CENTER";
            movePixelToPos(2);
        }else{
            pos = "LEFT";
            movePixelToPos(1);
        }
        // Do whatever else

        // idle
        while (!isStopRequested() && opModeIsActive()){
            telemetry.addData("X:","%d", pipe.getX());
            telemetry.addLine(pos);
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
                Trajectory moveForwardCenter = drive.trajectoryBuilder(new Pose2d()).forward(31).build();
                drive.followTrajectory(moveForwardCenter);
                claw.open();
                Trajectory moveBackCenter = drive.trajectoryBuilder(moveForward.end()).back(6).build();
                drive.followTrajectory(moveBackCenter);
                break;
            // Right
            case 3:
                drive.followTrajectory(moveForward);
                Trajectory rotateL90 = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX(), moveForward.end().getY()), Math.toRadians(90.0)).build();
                drive.followTrajectory(rotateL90);
                Trajectory moveBackRight = drive.trajectoryBuilder(rotateL90.end()).back(20).build();
                drive.followTrajectory(moveBackRight);
                claw.open();
                moveBackRight = drive.trajectoryBuilder(moveBackRight.end()).back(5).build();
                drive.followTrajectory(moveBackRight);

                break;
            default:
                break;
        }
    }

    private void driveToBackdrop(Trajectory start){

    }
}
