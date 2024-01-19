package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.resources.RoadRunnerHelper;

@Disabled
public abstract class AutonomousBaseNearSide extends AutonomousBase {

    @Override
    public void markerOnLeft() {
        driveHelper.forward(27)
                .turn(90 * flip)
                .forward(5);
        claw.open();
        driveHelper.reverse(5, RoadRunnerHelper.REVERSE_FAST);
        claw.close();
        driveHelper.reverse(22.5);

        dropPixels(Position.LEFT);
    }

    @Override
    public void markerOnCenter() {
        driveHelper.forward(28, 38)
                .reverse(1)
                .turn(90 * flip)
                .strafeRight(10 * flip)
                .reverse(5);
        claw.openSlightly();
        driveHelper.reverse(5, RoadRunnerHelper.REVERSE_FAST);
        claw.close();
        driveHelper.reverse(12.5)
                .strafeLeft(12 * flip);

        dropPixels(Position.CENTER);
    }

    @Override
    public void markerOnRight() {
        driveHelper.forward(27)
                .turn(90 * flip)
                .reverse(16);
        claw.open();
        driveHelper.reverse(6, RoadRunnerHelper.REVERSE_FAST);
        claw.close();

        dropPixels(Position.RIGHT);
    }

    public boolean isNear() {
        return true;
    }

}