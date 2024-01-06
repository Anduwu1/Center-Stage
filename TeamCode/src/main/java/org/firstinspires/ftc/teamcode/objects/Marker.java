package org.firstinspires.ftc.teamcode.objects;

public enum Marker {
    RED(1, 40, 170, 180),
    BLUE(60, 100, 100, 100);

    Marker(int hueMin, int hueMax, int hueWrapAroundMin, int hueWrapAroundMax) {
        this.hueMin = hueMin;
        this.hueMax = hueMax;
        this.hueWrapAroundMin = hueWrapAroundMin;
        this.hueWrapAroundMax = hueWrapAroundMax;
    }

    private int hueMin;
    private int hueMax;
    private int hueWrapAroundMin;
    private int hueWrapAroundMax;

    public int getHueMin() {
        return hueMin;
    }

    public int getHueMax() {
        return hueMax;
    }

    public int getHueWrapAroundMin() {
        return hueWrapAroundMin;
    }

    public int getHueWrapAroundMax() {
        return hueWrapAroundMax;
    }
}
