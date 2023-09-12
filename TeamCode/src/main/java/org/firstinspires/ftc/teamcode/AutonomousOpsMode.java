package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;




// New vision system
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
/*
    Class for all the Autonomous stuff
 */

@Config
@Autonomous(group = "drive")
public class AutonomousOpsMode extends LinearOpMode implements OpenCvCamera.AsyncCameraOpenListener{

    // Used to manage the video source
    private VisionPortal visionPortal;
    // Used for managing the AprilTag detection process.
    private AprilTagProcessor aprilTag;
    // Used to hold the data for a detected AprilTag
    private AprilTagDetection desiredTag = null;

    @Override
    public void runOpMode() throws InterruptedException {


        initAprilTagDetection();

        while(!isStarted()){
            // Do something
        }

        // Start autonomous
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
    }

    private void initAprilTagDetection() {
        aprilTag = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder().setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .build();
    }


}
