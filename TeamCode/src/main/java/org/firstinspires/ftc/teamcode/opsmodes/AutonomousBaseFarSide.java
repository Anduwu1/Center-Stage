package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.AutonomousConstants;
import org.firstinspires.ftc.teamcode.resources.OpenCVManager;
import org.firstinspires.ftc.teamcode.resources.Pipelines.AutoPipeLine;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Distance;

@Disabled
public abstract class AutonomousBaseFarSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        Trajectory moveForward = drive.trajectoryBuilder(new Pose2d()).forward(AutonomousConstants.RedFarSide.BaseMoveForward).build();

        drive.followTrajectory(moveForward);
        Trajectory rotateL90Left = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX() + 0.01, moveForward.end().getY() - 0.01) , Math.toRadians(90.0)).build();
        drive.followTrajectory(rotateL90Left);
        moveForward = drive.trajectoryBuilder(rotateL90Left.end()).forward(AutonomousConstants.RedFarSide.LeftForwardForPixelPlace).build();
        drive.followTrajectory(moveForward);
        claw.open();
        Trajectory moveBack = drive.trajectoryBuilder(moveForward.end()).back(AutonomousConstants.RedFarSide.LeftBackwardToReachBackDropFunctionHandOff).build();
        drive.followTrajectory(moveBack);
        claw.close();
        driveToBackdrop(moveBack, Position.LEFT);
    }

    @Override
    public void markerOnCenter() {
        Trajectory moveForward = drive.trajectoryBuilder(new Pose2d()).forward(AutonomousConstants.RedFarSide.BaseMoveForward).build();
        Trajectory moveForwardCenter = drive.trajectoryBuilder(new Pose2d()).forward(AutonomousConstants.RedFarSide.CenterMoveForward).build();
        drive.followTrajectory(moveForwardCenter);
        claw.open();
        Trajectory moveBackCenter = drive.trajectoryBuilder(moveForward.end()).back(AutonomousConstants.RedFarSide.CenterMoveBack).build();
        drive.followTrajectory(moveBackCenter);
        claw.close();
        Trajectory rotateL90Center = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX() + 0.01, moveForward.end().getY() - 0.01) , Math.toRadians(90.0)).build();
        drive.followTrajectory(rotateL90Center);
        moveBackCenter = drive.trajectoryBuilder(rotateL90Center.end()).back(AutonomousConstants.RedFarSide.CenterReachBackDropFunctionHandOff).build();
        drive.followTrajectory(moveBackCenter);
        driveToBackdrop(moveBackCenter, Position.CENTER);
    }

    @Override
    public void markerOnRight() {
        Trajectory moveForward = drive.trajectoryBuilder(new Pose2d()).forward(AutonomousConstants.RedFarSide.BaseMoveForward).build();
        drive.followTrajectory(moveForward);
        Trajectory rotateL90 = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX() + 0.01, moveForward.end().getY() - 0.01) , Math.toRadians(90.0)).build();
        drive.followTrajectory(rotateL90);
        Trajectory moveBackRight = drive.trajectoryBuilder(rotateL90.end()).back(AutonomousConstants.RedFarSide.RightMoveBackPlacePixel).build();
        drive.followTrajectory(moveBackRight);
        claw.open();
        moveBackRight = drive.trajectoryBuilder(moveBackRight.end()).back(AutonomousConstants.RedFarSide.RightMoveBackBackDropFunctionHandOff).build();
        drive.followTrajectory(moveBackRight);
        claw.close();
        driveToBackdrop(moveBackRight, Position.RIGHT);
    }

    private void driveToBackdrop(Trajectory start, Position position){
        Trajectory moveBackBg = drive.trajectoryBuilder(start.end()).back(66).build();
        drive.followTrajectory(moveBackBg);
        arm.moveToUpPosition();
        bucket.moveToDropPosition();
        //bucket.moveToDropPositionAndWait();
        sleep(2500);
        while(distance.getLeftDistance() > 2 && distance.getRightDistance() > 2){
            rightBackDrive.setPower(-0.1);
            leftBackDrive.setPower(-0.1);
        }
        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);

        // move left/right based on position of team prop position
        if (position == Position.LEFT)
            drive.followTrajectory(drive.trajectoryBuilder(moveBackBg.end()).strafeRight(6).build());
        else if (position == Position.RIGHT)
            drive.followTrajectory(drive.trajectoryBuilder(moveBackBg.end()).strafeLeft(10).build());

        // Drop em
        claw.open();
    }
}
