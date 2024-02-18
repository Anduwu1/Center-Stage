package org.firstinspires.ftc.teamcode.objects;

public enum Marker {
    RED(165, 180, -1, 180),
    BLUE(90, 100, -1, 110);

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
