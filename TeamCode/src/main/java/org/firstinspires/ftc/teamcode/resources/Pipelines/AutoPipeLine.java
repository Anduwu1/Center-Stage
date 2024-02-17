package org.firstinspires.ftc.teamcode.resources.Pipelines;

import org.firstinspires.ftc.teamcode.objects.Marker;
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
    private Marker marker;
    Mat hsvMat = new Mat(), hierarchyMat = new Mat(), hsvThresholdMat = new Mat(), erosionElement = new Mat(), dilationElement = new Mat(), cutOff = new Mat();

    //configurations
    int erosionKernelSize = 2;
    int dilationKernelSize = 8;
    int elementType = Imgproc.CV_SHAPE_RECT;

    int x = 0;

    public AutoPipeLine(Marker marker){
        this.marker = marker;
    }

    @Override
    public Mat processFrame(Mat inputRaw) {
        Imgproc.cvtColor(inputRaw, hsvMat, Imgproc.COLOR_RGB2HSV);

        Rect roi = new Rect(0, (inputRaw.rows() / 3), inputRaw.cols(), inputRaw.rows() - ((inputRaw.rows() / 3)));
        Mat input = new Mat(inputRaw, roi);

        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);

        Scalar lowHSV = new Scalar(marker.getHueMin(), 120, 80); // FILL IN WITH VALS
        Scalar highHSV = new Scalar(marker.getHueMax(), 255, 255);

        Core.inRange(hsvMat, lowHSV, highHSV, hsvThresholdMat);

        if(marker.getHueWrapAroundMin() > 1) {
            lowHSV = new Scalar(marker.getHueWrapAroundMin(), 120, 100); // FILL IN WITH VALS
            highHSV = new Scalar(marker.getHueWrapAroundMax(), 255, 255);

            Core.inRange(hsvMat, lowHSV, highHSV, hsvThresholdMat);
        }

        //erode then dilate the image
        erosionElement = Imgproc.getStructuringElement(elementType, new Size(2 * erosionKernelSize + 1, 2 * erosionKernelSize + 1), new Point(erosionKernelSize, erosionKernelSize));
        Imgproc.erode(hsvThresholdMat, hsvThresholdMat, erosionElement);
        dilationElement = Imgproc.getStructuringElement(elementType, new Size(2 * dilationKernelSize + 1, 2 * dilationKernelSize + 1), new Point(dilationKernelSize, dilationKernelSize));
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
