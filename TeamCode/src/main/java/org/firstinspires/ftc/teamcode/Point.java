package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

public class Point {
    float x, y;
    public Point(float _x, float _y){
        x = _x;
        y = _y;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
