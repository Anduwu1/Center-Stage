package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.resources.AutonomousConstants;

@Disabled
public abstract class AutonomousBaseNearSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        driveHelper.forward(27);
        driveHelper.turn(90);
        driveHelper.forward(6);
        claw.open();
        driveHelper.reverse(6);
        claw.close();
        driveHelper.reverse(24);
        driveHelper.turn(90);
    }

    @Override
    public void markerOnCenter() {
        driveHelper.forward(27);
        driveHelper.turn(90);
        driveHelper.strafeRight(12);
        driveHelper.reverse(6);
        claw.open();
        driveHelper.reverse(6);
        claw.close();
        driveHelper.reverse(12);
        driveHelper.strafeLeft(12);
    }

    @Override
    public void markerOnRight() {
        driveHelper.forward(27);
        driveHelper.turn(90);
        driveHelper.reverse(16);
        claw.open();
        driveHelper.reverse(6);
        claw.close();
        driveHelper.reverse(9);
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
