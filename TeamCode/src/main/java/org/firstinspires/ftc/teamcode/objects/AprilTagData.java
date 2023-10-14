package org.firstinspires.ftc.teamcode.objects;

import java.io.IOException;

public class AprilTagData {
    private int index;
    private float x, y;
    private float d, dr, dx, dy;
    RobotLog log;
    public AprilTagData(int _index, float _x, float _y) throws IOException {
        this.index = _index;
        this.x = _x;
        this.y = _y;
        String fName = Integer.toString(_index) + "_debug.txt";
        this.log = new RobotLog(fName);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Point calculate(float _d, float bearing, float pitch, float yaw){
        bearing = (float) Math.toRadians(bearing);
        yaw = (float) Math.toRadians(yaw);

        float _y = (float) (this.y + (_d * Math.cos(bearing) * Math.cos(yaw)) + (Math.sqrt(Math.pow(Math.cos(bearing),2) + Math.pow(_d,2))) * Math.cos(yaw));
        // float _x = (float) (this.x + Math.sqrt(Math.pow(_d * Math.cos(bearing),2) - Math.pow(_y,2)));
        float _x = (float) 1; // TODO: MAKE REAL VALUE
        return new Point(_x,_y);
    }
    public float get_x() {
        return this.x;
    }

    public float get_y() {
        return this.y;
    }

}
