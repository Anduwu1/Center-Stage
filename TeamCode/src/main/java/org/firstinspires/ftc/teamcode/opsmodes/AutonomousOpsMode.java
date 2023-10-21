package org.firstinspires.ftc.teamcode.opsmodes;
// Basic stuff
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import java.util.Locale;
// New vision system
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    public enum RobotState {
        RUNNING,
        STOP
    }
    public enum Alliance {
        RED_ALLIANCE,
        BLUE_ALLIANCE
    }

    private enum AutonomousState
    {
        START,
        PLACE_PURPLE_PIXEL,
        DO_DELAY,
        DRIVE_TO_LOOKOUT,
        FIND_APRILTAG,
        DRIVE_TO_APRILTAG,
        PLACE_YELLOW_PIXEL,
        PARK_AT_BACKSTAGE,
        DONE
    }


    public enum StartPos {
        AUDIENCE,
        BACKSTAGE
    }

    /* public enum ParkPos {
        CORNER,
        CENTER
    }*/




    public static class AutoChoices {
        public RobotState state = RobotState.RUNNING;
        public double delay = 0.0;
        public Alliance alliance = Alliance.RED_ALLIANCE;
        public StartPos startPos = StartPos.BACKSTAGE;
        // public ParkPos parkPos = ParkPos.CORNER;
        public AutonomousState autonomousStage = AutonomousState.START;
        public double xTarget = 0.0;
        public double yTarget = 0.0;
        public double turnTarget = 0.0; // Angle in deg
        public double driveTime = 0.0;
        public double drivePower = 0.0;

        @Override
        public String toString()
        {
            return String.format(
                    Locale.US,
                            "state=\"%s\" " +
                            "delay=%.0f " +
                            "alliance=\"%s\" " +
                            "startPos=\"%s\" " +
                            // "parkPos=\"%s\" " +
                            "autostage=\"%s\" " +
                            "xTarget=%.1f " +
                            "yTarget=%.1f " +
                            "turnTarget=%.0f " +
                            "driveTime=%.0f " +
                            "drivePower=%.1f",
                    state, delay, alliance,startPos, /*parkPos,*/ autonomousStage, xTarget, yTarget, turnTarget, driveTime, drivePower);
        }
    }

    public static final AutoChoices autoChoices = new AutoChoices();
    private Robot robot;



    @Override
    public void runOpMode() throws InterruptedException {
        while (autoChoices.state == RobotState.RUNNING) {
            // Update Telemetry
            telemetry.addLine(autoChoices.toString());
            telemetry.update();

            // switch for
        }
    }

}