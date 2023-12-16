package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.opsmodes.AutonomousOpsMode_OLD;

public class Robot {
    public Point position;
    public Vision vision;

    public AutonomousOpsMode_OLD.Alliance alliance;

    public HardwareMap hardCont;


    public Robot(AutonomousOpsMode_OLD.StartPos start, HardwareMap hM) {
        this.hardCont = hM;
        vision = new Vision(this.hardCont);

        alliance = AutonomousOpsMode_OLD.Alliance.RED_ALLIANCE;

        vision.initVision();
        // Sets Robot Start Position
        // TODO: Verify that these locations are correct
        float x, y;
        switch (alliance) {
            case RED_ALLIANCE:
                x = (float) ((float) RobotSettings.FULL_FIELD_INCHES - (RobotSettings.ROBOT_LENGTH/2));
                break;
            default:
                x = (float) (RobotSettings.ROBOT_LENGTH/2);
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
