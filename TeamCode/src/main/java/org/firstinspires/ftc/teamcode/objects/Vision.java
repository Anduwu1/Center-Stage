package org.firstinspires.ftc.teamcode.objects;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationIdentity;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Vision {

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
            visionPortal = new VisionPortal.Builder().setCamera(hardCont.get(WebcamName.class, "webcam"))
                    .addProcessor(aprilTag)
                    .build();
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


    }
}
