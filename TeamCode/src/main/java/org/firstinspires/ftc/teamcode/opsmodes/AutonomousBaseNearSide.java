package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.resources.AutonomousConstants;
import org.firstinspires.ftc.teamcode.resources.RoadRunnerHelper;

@Disabled
public abstract class AutonomousBaseNearSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        driveHelper.forward(27)
            .turn(90)
            .forward(5);
        claw.open();
        driveHelper.reverse(5);
        claw.close();
        driveHelper.reverse(22.5);

        redDropPixels(Position.LEFT);
    }

    @Override
    public void markerOnCenter() {
        driveHelper.forward(28, 38)
                .reverse(1)
                .turn(90)
                .strafeRight(10)
                .reverse(5);
        claw.open();
        driveHelper.reverse(5, RoadRunnerHelper.REVERSE_FAST);
        claw.close();
        driveHelper.reverse(12.5)
                .strafeLeft(12);

        redDropPixels(Position.CENTER);
    }

    @Override
    public void markerOnRight() {
        driveHelper.forward(27)
                .turn(90)
                .reverse(16);
        claw.open();
        driveHelper.reverse(6, RoadRunnerHelper.REVERSE_FAST);
        claw.close();

        redDropPixels(Position.RIGHT);
    }

}
