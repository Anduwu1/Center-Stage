package org.firstinspires.ftc.teamcode;

public class AprilTag {

    public AprilTag(int index, float x, float y){

    }

    public Point calculate(float range, float bearing, float yaw){
        float x = this.x - range * Math.cos(bearing);
        float y = this.y - range * Math.sin(bearing);

        return new Point(x,y);
    }
}
