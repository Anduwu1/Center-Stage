package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

import java.util.List;

public class AprilTag {

    private final Camera camera;

    public AprilTag(HardwareMap hardwareMap) {
        this.camera = new Camera(hardwareMap);
    }

    // Camera stuff
    public List<AprilTagDetection> getAllAprilTags(){
        return camera.getAllVisibleAprilTags();
    }

    public AprilTagPoseFtc getAprilTagWithId(int id){
        AprilTagPoseFtc tmp = new AprilTagPoseFtc(0,0,0,0,0,0,-100,0,0); // default
        for(AprilTagDetection april : this.getAllAprilTags()){
            if(april.id == id){
                tmp = april.ftcPose;
                break;
            }
        }
        return tmp;
    }
}
