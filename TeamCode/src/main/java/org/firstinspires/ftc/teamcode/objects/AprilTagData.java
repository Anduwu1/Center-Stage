package org.firstinspires.ftc.teamcode.objects;

import java.io.IOException;

public class AprilTagData {
    private int index;
    private float x, y;
    private float d, dr, dx, dy;
    public AprilTagData(int _index, float _x, float _y){
        this.index = _index;
        this.x = _x;
        this.y = _y;
        String fName = Integer.toString(_index) + "_debug.txt";
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
    public float get_x() {
        return this.x;
    }

    public float get_y() {
        return this.y;
    }

}
