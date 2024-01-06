package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.resources.AutonomousConstants;

@Disabled
public abstract class AutonomousBaseFarSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        driveHelper.forward(AutonomousConstants.RedFarSide.BaseMoveForward);
        driveHelper.turn(90);
        driveHelper.forward(5);
        claw.open();
        intake.slowlyEject();
        driveHelper.reverse(5);
        intake.stop();
        driveHelper.strafeRight(22);

        //driveToBackdrop(moveBack, Position.LEFT);
    }

    @Override
    public void markerOnCenter() {
        driveHelper.forward(AutonomousConstants.RedFarSide.CenterMoveForward);
        driveHelper.reverse(AutonomousConstants.RedFarSide.CenterMoveBack);
        driveHelper.turn(90);
        driveHelper.reverse(AutonomousConstants.RedFarSide.CenterReachBackDropFunctionHandOff);

        //driveToBackdrop(moveBackCenter, Position.CENTER);
    }

    @Override
    public void markerOnRight() {
        driveHelper.forward(AutonomousConstants.RedFarSide.BaseMoveForward);
        driveHelper.turn(90);
        driveHelper.reverse(AutonomousConstants.RedFarSide.RightMoveBackPlacePixel);
        claw.open();
        driveHelper.reverse(AutonomousConstants.RedFarSide.RightMoveBackBackDropFunctionHandOff);
        claw.close();

        //driveToBackdrop(moveBackRight, Position.RIGHT);
    }

    private void driveToBackdrop(Trajectory start, Position position){
        driveHelper.reverse(66);
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
            driveHelper.strafeRight(6);
        else if (position == Position.RIGHT)
            driveHelper.strafeLeft(10);

        // Drop em
        claw.open();
    }
}
