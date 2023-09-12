package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

// New vision system
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;
/*
    Class for all the Autonomous stuff
 */

@Autonomous(group = "drive")
public class AutonomousOpsMode extends LinearOpMode {

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

        while(opModeIsActive()) {
            // Start autonomous
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : currentDetections) {
                // Print out all debug information about the current tag
                telemetry.addData("Target", "ID %d (%s)", detection.id, detection.metadata.name);
                telemetry.addData("Range", "%5.1f inches", detection.ftcPose.range);
                telemetry.addData("Bearing", "%3.0f degrees", detection.ftcPose.bearing);
                telemetry.addData("Yaw", "%3.0f degrees", detection.ftcPose.yaw);
                telemetry.update();
            }
            // TODO make it like do stuff
        }
    }

    /*
        Initializes the new camera system
     */
    private void initAprilTagDetection() {
        aprilTag = new AprilTagProcessor.Builder().build();

        visionPortal = new VisionPortal.Builder().setCamera(hardwareMap.get(WebcamName.class, "webcam"))
                .addProcessor(aprilTag)
                .build();
    }


}
