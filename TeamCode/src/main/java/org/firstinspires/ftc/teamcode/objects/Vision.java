package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationIdentity;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

public class Vision {
    public ArrayList<AprilTagData> aprilTags;
    public Point location;
    public Vision(HardwareMap hardCont){
        try {
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
            visionPortal = new VisionPortal.Builder().setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                    .addProcessor(aprilTag)
                    .build();
            visionPortal.liv
        } catch (Exception e){
            // REMOVE THIS TRY CATCH LATER
        }
    }

    // TODO: Make do stuff useful

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
            if (tmp != null) {
                if (tmp.ftcPose.range > detection.ftcPose.range)
                    tmp = detection;
            } else {
                tmp = detection;
            }
        }

        return tmp;
    }

    public void initVision(){
        aprilTags = new ArrayList<>();

        fillList(aprilTags);
    }

    ArrayList<Point> points = new ArrayList<>();

    public void updateAprilTags() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        for (AprilTagDetection detection : currentDetections) {
            points.add(aprilTags.get(detection.id - 1).calculate((float) detection.ftcPose.y, (float) detection.ftcPose.bearing, (float) detection.ftcPose.elevation, (float) detection.ftcPose.yaw));
        }
    }

    public Point getLocation() {
        updateAprilTags();

        if (points.isEmpty())
            return new Point(-1, -1);

        float totalX = 0;
        float totalY = 0;

        for (Point p : points) {
            if (p != null) {
                totalX += p.get_x();
                totalY += p.get_y();
            }
        }

        points.clear();
        return new Point(totalX / points.size(), totalY / points.size());
    }
    public void fillList(ArrayList<AprilTagData> tags) {
        // 1 - 3
        AprilTagData aprilTag1 = new AprilTagData(1, (float) 29.4, 7.5f);
        tags.add(aprilTag1);

        AprilTagData aprilTag2 = new AprilTagData(2, (float) 35.4, 7.5f);
        tags.add(aprilTag2);

        AprilTagData aprilTag3 = new AprilTagData(3, (float) 39.4, 7.5f);
        tags.add(aprilTag3);

        // 4 - 6
        AprilTagData aprilTag4 = new AprilTagData(4, (float) (RobotSettings.FULL_FIELD_INCHES - 29.4), 7.5f);
        tags.add(aprilTag4);

        AprilTagData aprilTag5 = new AprilTagData(5, (float) (RobotSettings.FULL_FIELD_INCHES - 34.4), 7.5f);
        tags.add(aprilTag5);

        AprilTagData aprilTag6 = new AprilTagData(6, (float) (RobotSettings.FULL_FIELD_INCHES - 39.4), 7.5f);
        tags.add(aprilTag6);
    }
}
