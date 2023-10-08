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

    public Point calculate(float _d, float bearing, float pitch){
        this.d = _d;
        // this.dr = (float) (this.d * (Math.cos((double) pitch + 30.0)));
        //

        // convert bearing from deg to rad
        bearing = (float) Math.toRadians(bearing);
        float _x = (float) (this.x - (this.d * Math.cos(bearing)));
        float _y = (float) (this.y - (this.d * Math.sin(bearing)));

        try{
            log.log("Bearing: " + Float.toString(bearing)/*(float)(3.1415)) + "pi"*/);
            log.log("Range: " + Float.toString(_d) + "in");
            // log.log("X-Component: " + _x + "in");
            //log.log("Y-Component: " + _y + "in");
            log.log("");

        } catch (NullPointerException n) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        return new Point(_x,_y);
    }


}
