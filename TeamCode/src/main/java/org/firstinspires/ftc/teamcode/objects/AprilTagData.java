package org.firstinspires.ftc.teamcode.objects;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.Locale;

public class AprilTagData {
    private int index;
    private float d, dr, dx, dy;

        float x;
        float y;
        float z;
        float yaw;
        float pitch;
        float roll;
        float range;
        float bearing;
        float elevation;

    public AprilTagData(int _index, float _x, float _y){
        this.index = _index;
        this.x = _x;
        this.y = _y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point calculate(float _d, float bearing, float pitch, float yaw){
        // bearing = (float) Math.toRadians(bearing);
        // yaw = (float) Math.toRadians(yaw);

        double v = Math.toRadians(bearing + (180 - (360 - 90 - (90 - bearing) - (90 - yaw))));
        float _y = (float) (this.y + (_d * Math.sin(v)));
        float _x = (float) (this.x + (_d * Math.cos(v)));
        // float _x = (float) (this.x + Math.sqrt(Math.pow(_d * Math.cos(bearing),2) - Math.pow(_y,2)));

        return new Point(_x,_y);
    }

    public Point calculate() {
        double v = Math.toRadians(bearing + (180 - (360 - 90 - (90 - bearing) - (90 - yaw))));
        float _y = (float) (this.y + (range * Math.sin(v)));
        float _x = (float) (this.x + (range * Math.cos(v)));

        _x += this.x;
        _y += this.y;

        return new Point(_x, _y);
    }
    public float get_x() {
        return this.x;
    }

    public float get_y() {
        return this.y;
    }

    public String toString() {
        return String.format(
                Locale.US,

                /*"x: %f%n" +
                        "y: %f%n" +*/
                        "z: %f%n%n" +

                        "yaw: %f%n" +
                        "pitch: %f%n" +
                        "roll: %f%n" +

                        "range: %f%n%n" +
                        "bearing: %f%n" +
                        "elevation: %f%n",
                /*this.x, this.y,*/ this.z, this.yaw, this.pitch, this.roll, this.range, this.bearing, this.elevation
        );
    }

    public void update(AprilTagDetection detection) {
        // this.x = (float) detection.ftcPose.x;
        // this.y = (float) detection.ftcPose.y;
        this.z = (float) detection.ftcPose.z;
        this.yaw = (float) detection.ftcPose.yaw;
        this.pitch = (float) detection.ftcPose.pitch;
        this.roll = (float) detection.ftcPose.roll;
        this.range = (float) detection.ftcPose.range;
        this.bearing = (float) detection.ftcPose.bearing;
        this.elevation = (float) detection.ftcPose.elevation;
    }


}
