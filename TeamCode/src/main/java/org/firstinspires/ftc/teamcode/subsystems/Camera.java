package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Camera {
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    public Camera(HardwareMap hardwareMap){
        aprilTag = new AprilTagProcessor.Builder().build();
        // TODO maybe add calibration
        visionPortal = new VisionPortal.Builder().setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }

    public List<AprilTagDetection> getAllVisibleAprilTags(){
        return aprilTag.getDetections();
    }

}
