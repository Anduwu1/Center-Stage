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
    public Vision vision = new Vision();

    public AutonomousOpsMode.Alliance alliance;

    public static HardwareMap hardCont;

    public static class Vision {

        public Vision(){
            initVision();
        }

        // TODO: Make do stuff usefull

        private VisionPortal visionPortal;
        private AprilTagProcessor aprilTag;

        public int getLastDetetectedTeamPropLoc() {
            return 0; // TODO: Make this return the last team prop location seen
            // If returns 0 no team prop found
        }

        public AprilTagDetection getClosestTag(){
            AprilTagDetection tmp = null;

            List<AprilTagDetection> currentDetections = aprilTag.getDetections();


            for (AprilTagDetection detection : currentDetections) {
                if(tmp != null) {
                    if(tmp.ftcPose.range > detection.ftcPose.range)
                        tmp = detection;
                }else{
                    tmp = detection;
                }
            }

            return tmp;
        }

        public void initVision(){
            aprilTag = new AprilTagProcessor.Builder().build();
            CameraCalibrationIdentity cci = new CameraCalibrationIdentity() {
                @Override
                public boolean isDegenerate() {
                    return false;
                }
            };
            float co[] = { -0.0347492f, -0.0148858f,0, 0, -0.0072575f,  0.0121186f, -0.156374f, 0};
            CameraCalibration goof = new CameraCalibration(cci, new Size(640, 480), 499.542f, 499.542f, 341.04f,225.8f, co, false, false);
            aprilTag.init(640, 480, goof);
            try {
                visionPortal = new VisionPortal.Builder().setCamera(hardCont.get(WebcamName.class, "webcam"))
                        .addProcessor(aprilTag)
                        .build();
            } catch (Exception e){
                // REMOVE THIS TRY CATCH LATER
            }
        }
    }

    public Robot(AutonomousOpsMode.StartPos start, HardwareMap hard) {
        this.hardCont = hard;
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
