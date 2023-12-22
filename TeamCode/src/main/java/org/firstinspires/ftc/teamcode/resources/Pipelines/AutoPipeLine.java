package org.firstinspires.ftc.teamcode.resources.Pipelines;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class AutoPipeLine extends OpenCvPipeline {
    Mat hsvMat = new Mat();
    Mat hierarchyMat = new Mat();
    Mat mask = new Mat();
    @Override
    public Mat processFrame(Mat input) {
        // For mask
        Mat mask = new Mat(input.rows(), input.cols(), CvType.CV_8U, Scalar.all(0));

        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        //



        return null;
    }
}
