package org.firstinspires.ftc.teamcode.objects;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import static java.sql.Types.NULL;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationIdentity;
import org.firstinspires.ftc.teamcode.opsmodes.AutonomousOpsMode;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Robot {
    public Point position;
//    /public Vision vision = new Vision();

    public AutonomousOpsMode.Alliance alliance;

    public static HardwareMap hardCont;
    public Vision vision;


    public Robot(AutonomousOpsMode.StartPos start) {

        vision = new Vision(this.hardCont);

        alliance = AutonomousOpsMode.Alliance.RED_ALLIANCE;
        //vision = new Vision();
        //vision.initVision();
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
