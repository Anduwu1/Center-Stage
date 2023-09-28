package org.firstinspires.ftc.teamcode;
// Basic stuff
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

// New vision system
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

// Default java stuff
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


    // POSITION
    float x = 0.0f, y = 0.0f;
    @Override
    public void runOpMode() throws InterruptedException {
        // Init
        initAprilTagDetection();

        // wait
        while(!isStarted()){
            // Do something
        }

        // go
        while(opModeIsActive()) {
            // Start autonomous (wow)
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();
            float loc_X = 0.0f, loc_Y = 0.0f;
            for (AprilTagDetection detection : currentDetections) {

                telemetry.update();
                // Print out all debug information about the current tag (yay)
                /*telemetry.addData("Target", "ID %d (%s)", detection.id, detection.metadata.name);
                telemetry.addData("Range", "%5.1f inches", detection.ftcPose.range);
                telemetry.addData("Bearing", "%3.0f degrees", detection.ftcPose.bearing);
                telemetry.addData("Yaw", "%3.0f degrees", detection.ftcPose.yaw);
                telemetry.update();*/
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
