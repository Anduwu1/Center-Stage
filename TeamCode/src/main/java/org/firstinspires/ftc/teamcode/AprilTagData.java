package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.robot.Robot;

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

        float _y = (float) (this.y + (_d*Math.cos(bearing)*Math.cos(yaw)));
        float _x = (float) (this.x + Math.sqrt(Math.pow(_d*Math.cos(bearing),2)-Math.pow(_y,2)));

        return new Point(_x,_y);
    }


}
