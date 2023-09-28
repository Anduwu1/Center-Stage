package org.firstinspires.ftc.teamcode;

public class AprilTagData {
    private int index;
    private float x, y;
    private float d, dr, dx, dy;
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

    public Point calculate(float _d, float bearing, float yaw){
        this.d = _d;
        this.dr = (float) (this.d * Math.cos((double) yaw + 30.0));

        float _x = (float) (this.x - this.dr * Math.cos(bearing));
        float _y = (float) (this.y + this.dr * Math.sin(bearing));

        return new Point(_x,_y);
    }


}
