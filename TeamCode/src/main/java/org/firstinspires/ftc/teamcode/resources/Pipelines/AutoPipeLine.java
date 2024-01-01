package org.firstinspires.ftc.teamcode.resources.Pipelines;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AutoPipeLine extends OpenCvPipeline {
    Mat hsvMat = new Mat();
    Mat hierarchyMat = new Mat();
    Mat mask = new Mat();

    float redHueMin = 1;
    float redHueMax = 40;
    float redWrapAround = 170;
    float redWrapAroundMax = 180;


    //configurations
    int erosionKernelSize = 2;
    int dilationKernelSize = 8;
    int elementType = Imgproc.CV_SHAPE_RECT;


    int x = 0;


    public AutoPipeLine(){

    }

    @Override
    public Mat processFrame(Mat input) {
        //Core.flip(input, input, 1);
        // For mask
        Mat mask = new Mat(input.rows(), input.cols(), CvType.CV_8U, Scalar.all(0));

        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        // Get RED
        Mat hsvThresholdMat = new Mat();
        Scalar lowHSV = new Scalar(redHueMin, 100, 100); // FILL IN WITH VALS
        Scalar highHSV = new Scalar(redHueMax, 255, 255);

        Core.inRange(hsvMat, lowHSV, highHSV, hsvThresholdMat);

        lowHSV = new Scalar(redWrapAround, 100, 100); // FILL IN WITH VALS
        highHSV = new Scalar(redWrapAroundMax, 255, 255);

        Core.inRange(hsvMat, lowHSV, highHSV, hsvThresholdMat);

        //erode then dilate the image
        Mat erosionElement = Imgproc.getStructuringElement(elementType, new Size(2 * erosionKernelSize + 1, 2 * erosionKernelSize + 1), new Point(erosionKernelSize, erosionKernelSize));
        Imgproc.erode(hsvThresholdMat, hsvThresholdMat, erosionElement);
        Mat dilationElement = Imgproc.getStructuringElement(elementType, new Size(2 * dilationKernelSize + 1, 2 * dilationKernelSize + 1), new Point(dilationKernelSize, dilationKernelSize));
        Imgproc.dilate(hsvThresholdMat, hsvThresholdMat, dilationElement);

        List<MatOfPoint> contourPoints = new ArrayList<>();
        Imgproc.findContours(hsvThresholdMat, contourPoints, hierarchyMat, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint largest = null;
        if(contourPoints.size() > 0)
            largest = contourPoints.get(getLargestContourSize(contourPoints));

        if(largest != null){
            // It exist so get its position

            Moments moment = Imgproc.moments(largest);

            x = (int) (moment.get_m10() / moment.get_m00());

            Imgproc.putText(
                    hsvThresholdMat,
                    String.format(
                            Locale.US,
                            "Position %d",
                            (int) x
                    ),
                    new Point(0, 20),
                    Imgproc.FONT_HERSHEY_SIMPLEX,
                    .75,
                    new Scalar(120.0,100.0, 100.0),
                    2
            );
        }


        return hsvThresholdMat;
    }

    private int getLargestContourSize(List<MatOfPoint> contours) {
        int index = 0;
        double large = 0.0f;
        for(int i = 0; i < contours.size(); i++){
            if(Imgproc.contourArea(contours.get(i)) > large){
                large = Imgproc.contourArea(contours.get(i));
                index = i;
            }
        }
        return index;
    }

    public int getX() {
        return x;
    }
}
