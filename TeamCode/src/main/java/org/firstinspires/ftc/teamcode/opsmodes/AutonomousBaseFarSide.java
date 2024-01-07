package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.resources.AutonomousConstants;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Disabled
public abstract class AutonomousBaseFarSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        // Trajectory Sequence
        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d())
            .forward(27)
            .turn(Math.toRadians(180), 20, 20)
            .strafeRight(11)
            .back(6)
            .build();
        drive.followTrajectorySequence(trajSeq);

        // The same thing but using driveHelper
        driveHelper.forward(27)
                        .turn(180, 20, 20)
                        .strafeRight(11)
                        .reverse(6);

        claw.open();
        trajSeq = drive.trajectorySequenceBuilder(trajSeq.end()).back(6).build();
        drive.followTrajectorySequence(trajSeq);
        claw.close();

        trajSeq = drive.trajectorySequenceBuilder(trajSeq.end()).turn(Math.toRadians(90), 20, 20).build();
        drive.followTrajectorySequence(trajSeq);

        driveToBackdrop(Position.LEFT);
    }

    @Override
    public void markerOnCenter() {
        driveHelper.forward(27)
                .turn(180)
                .reverse(26);
        claw.open();
        driveHelper.reverse(5)
                .turn(90);

        driveToBackdrop(Position.CENTER);
    }

    @Override
    public void markerOnRight() {
        driveHelper.forward(AutonomousConstants.RedFarSide.BaseMoveForward)
                .turn(-90)
                .forward(2);
        claw.open();
        driveHelper.reverse(2);
        claw.close();
        driveHelper.strafeLeft(24)
                    .turn(180);

        driveToBackdrop(Position.RIGHT);
    }

    private void driveToBackdrop(Position position){
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
