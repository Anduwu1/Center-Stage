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

    public Point calculate(float _d, float bearing, float pitch){
        this.d = _d;
        this.dr = (float) (this.d * (Math.cos((double) pitch + 30.0)));

        // Used to be dr but that looks wrong im ngl
        float _x = (float) (this.x - this.d * Math.sin(bearing));
        float _y = (float) (this.y - this.d * Math.cos(bearing));

        return new Point(_x,_y);
    }


}
