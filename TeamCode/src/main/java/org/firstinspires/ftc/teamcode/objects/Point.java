package org.firstinspires.ftc.teamcode.objects;

import androidx.annotation.NonNull;

public class Point {
    float x, y;
    public Point(float _x, float _y){
        x = _x;
        y = _y;
    }

    public float get_x() {
        return this.x;
    }

    public float get_y() {
        return this.y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
