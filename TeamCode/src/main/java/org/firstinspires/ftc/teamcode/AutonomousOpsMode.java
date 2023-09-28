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
import java.util.ArrayList;
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
    public final int FIELDSIZE = 144;

    // POSITION
    float x = 0.0f, y = 0.0f;

    public void fillList(ArrayList<AprilTagData> tags) {
        // 1 - 3
        AprilTagData aprilTag = new AprilTagData(1, (float) 29.4, 10);
        tags.add(aprilTag);

        aprilTag = new AprilTagData(2, (float) 34.4, 10);
        tags.add(aprilTag);

        aprilTag = new AprilTagData(3, (float) 39.4, 10);
        tags.add(aprilTag);

        // 4 - 6
        aprilTag = new AprilTagData(4, (float) (FIELDSIZE - 29.4), 10);
        tags.add(aprilTag);

        aprilTag = new AprilTagData(5, (float) (FIELDSIZE - 34.4), 10);
        tags.add(aprilTag);

        aprilTag = new AprilTagData(6, (float) (FIELDSIZE - 39.4), 10);
        tags.add(aprilTag);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        // Init
        initAprilTagDetection();

        ArrayList<AprilTagData> aprilTags = new ArrayList<>();
        // Fill april tags
        fillList(aprilTags);

        // wait
        while(!isStarted()){
            // Do something
        }
        ArrayList<Point> points = new ArrayList<>();
        // go
        while(opModeIsActive()) {
            // Start autonomous (wow)
            List<AprilTagDetection> currentDetections = aprilTag.getDetections();

            for (AprilTagDetection detection : currentDetections) {
                if(detection.id <= aprilTags.size())
                    points.add(aprilTags.get(detection.id).calculate((float) detection.ftcPose.range, (float) detection.ftcPose.bearing, (float) detection.ftcPose.yaw));

                // Print out all debug information about the current tag (yay)
                /*telemetry.addData("Target", "ID %d (%s)", detection.id, detection.metadata.name);
                telemetry.addData("Range", "%5.1f inches", detection.ftcPose.range);
                telemetry.addData("Bearing", "%3.0f degrees", detection.ftcPose.bearing);
                telemetry.addData("Yaw", "%3.0f degrees", detection.ftcPose.yaw);
                telemetry.update();*/
            }
            // Average out
            float tmpX = 0.0f, tmpY = 0.0f;
            for (Point p:
                 points) {
                tmpX += p.x;
                tmpY += p.y;

            }
            x = tmpX / points.size();
            y = tmpY / points.size();
            points.clear();
            telemetry.addData("X", "%5.1f", x);
            telemetry.addData("Y", "%5.1f", y);
            telemetry.update();
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
