package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.resources.AutonomousConstants;
import org.firstinspires.ftc.teamcode.resources.RoadRunnerHelper;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

@Disabled
public abstract class AutonomousBaseFarSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        driveHelper.forward(27)
                        .turn(180 * flip)
                        .strafeRight(10)
                        .reverse(6);
        claw.open();
        driveHelper.reverse(6, RoadRunnerHelper.REVERSE_FAST);
        claw.close();

        driveHelper.reverse(10)
                .turn(-90 * flip)
                .reverse(10);

        driveToBackdrop(Position.LEFT);
    }

    @Override
    public void markerOnCenter() {
        driveHelper.forward(27)
                .turn(180 * flip)
                .reverse(15.5);
        claw.open();
        driveHelper.reverse(6.5, RoadRunnerHelper.REVERSE_FAST)
                .turn(-90 * flip);
        claw.close();
        driveToBackdrop(Position.CENTER);
    }

    @Override
    public void markerOnRight() {
        driveHelper.forward(27)
                .turn(-90 * flip)
                .forward(5);
        claw.open();
        driveHelper.reverse(5, RoadRunnerHelper.REVERSE_FAST);
        claw.close();
        driveHelper.strafeLeft(22)
                    .turn(180 * flip);

        driveToBackdrop(Position.RIGHT);
    }

    private void driveToBackdrop(Position position){
        // Move to that tile
    }
}
