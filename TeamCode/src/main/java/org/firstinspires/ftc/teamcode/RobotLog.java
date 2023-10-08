package org.firstinspires.ftc.teamcode;

import java.io.FileWriter;
import java.io.IOException;

/*
 * Very awesome
 */
public class RobotLog {
    FileWriter writer;
    public RobotLog(String name) throws IOException {
        writer = new FileWriter("/sdcard/"+name);

    }
    public void log(String val) throws IOException {
        writer.write(val + "\n");
    }


}