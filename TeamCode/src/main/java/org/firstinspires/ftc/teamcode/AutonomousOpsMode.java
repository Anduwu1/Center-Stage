package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


// OpenCV
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

/*
    Class for all the Autonomous stuff
 */

@Config
@Autonomous(group = "drive")
public class AutonomousOpsMode extends LinearOpMode implements OpenCvCamera.AsyncCameraOpenListener{
    // Constants
    public static int CAM_WIDTH = 432;
    public static int CAM_HEIGHT = 240;

    // OpenCV Camera
    private OpenCvCamera camera;

    @Override
    public void runOpMode() throws InterruptedException {

        // Grab the webcam and init it
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "webcam");

        // Create live preview
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        camera.openCameraDeviceAsync(this);
        // [TODO] set a pipeline here

        while(!isStarted()){
            // Do something
        }

        // Start autonomous
    }

    @Override
    public void onOpened() {
        camera.startStreaming(CAM_WIDTH, CAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
    }
    
}
