package org.firstinspires.ftc.teamcode.resources;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;

public class OpenCVManager implements OpenCvCamera.AsyncCameraOpenListener {

    public OpenCvCamera camera;

    private ArrayList<OpenCvPipeline> pipelines;

    public OpenCVManager(HardwareMap hardwareMap){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        // With live preview
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        camera.openCameraDeviceAsync(this);
    }

    public void addPipeline(OpenCvPipeline pipeline){
        this.pipelines.add(pipeline);
    }

    public void setPipeline(int id){
        if(id > pipelines.size()){
            // Throw error
        }else{
            camera.setPipeline(pipelines.get(id));
        }
    }

    public void setPipeline(OpenCvPipeline pipeline){
        camera.setPipeline(pipeline);
    }

    @Override
    public void onOpened() {
        camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
    }

    @Override
    public void onError(int i) {

    }
}
