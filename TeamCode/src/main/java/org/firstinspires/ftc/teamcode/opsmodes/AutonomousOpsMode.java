package org.firstinspires.ftc.teamcode.opsmodes;
// Basic stuff
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

// New vision system
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.android.util.Size;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.objects.AprilTagData;
import org.firstinspires.ftc.teamcode.objects.Point;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibrationIdentity;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

// Default java stuff
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Class for all the Autonomous stuff
 */

@Autonomous(group="drive")
public class AutonomousOpsMode extends LinearOpMode {

    public enum Alliance {
        RED_ALLIANCE,
        BLUE_ALLIANCE
    }

    public enum StartPos {

    }



    @Override
    public void runOpMode() throws InterruptedException {

    }
}