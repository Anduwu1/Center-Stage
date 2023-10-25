package org.firstinspires.ftc.teamcode.objects;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.firstinspires.ftc.teamcode.opsmodes.AutonomousOpsMode;

public class Robot {
    public Point position;
    public Vision vision = new Vision();

    public AutonomousOpsMode.Alliance alliance;

    public static class Vision {
        // TODO: Make do stuff usefull

        public int getLastDetetectedTeamPropLoc() {
            return 0; // TODO: Make this return the last team prop location seen
            // If returns 0 no team prop found
        }
    }

    public Robot(AutonomousOpsMode.Alliance alliance, AutonomousOpsMode.StartPos start) {
        this.alliance = alliance;
        // Sets Robot Start Position
        // TODO: Verify that these locations are correct
        float x, y;
        switch (alliance) {
            case RED_ALLIANCE:
                x = (float) RobotSettings.FULL_FIELD_INCHES;
                break;
            default:
                x = 0;
        }

        switch(start) {
            case AUDIENCE:
                y = (float) (RobotSettings.FULL_FIELD_INCHES - RobotSettings.FULL_TILE_INCHES);
                break;
            case BACKSTAGE:
                y = (float) (RobotSettings.HALF_FIELD_INCHES - RobotSettings.FULL_TILE_INCHES);
                break;
            default:
                y = 0;
        }

        this.position = new Point(x, y);

    }

    public void speak(String s) {
        // telemetry.addLine(s); // Talks out loud maybe?
        // telemetry.speak(s);
    }



}
