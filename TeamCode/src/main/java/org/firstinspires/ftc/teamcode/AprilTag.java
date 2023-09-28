package org.firstinspires.ftc.teamcode;

public class AprilTag {
    private int index;
    private float x, y;
    private float d, dr, dx, dy;
    public AprilTag(int _index, float _x, float _y){
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

    public Point calculate(float range, float bearing, float yaw){
        float _x = (float) (this.x - range * Math.cos(bearing));
        float _y = (float) (this.y - range * Math.sin(bearing));

        return new Point(_x,_y);
    }


}
