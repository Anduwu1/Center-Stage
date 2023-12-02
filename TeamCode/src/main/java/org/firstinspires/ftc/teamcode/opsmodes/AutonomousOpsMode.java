package org.firstinspires.ftc.teamcode.opsmodes;
// Basic stuff
import android.media.audiofx.DynamicsProcessing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.teamcode.resources.taskManagment.AutoTask;
import org.firstinspires.ftc.teamcode.resources.taskManagment.StageState;
import org.firstinspires.ftc.teamcode.resources.tasks.ParkTask;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
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
    public static enum Alliance {
        RED_ALLIANCE,
        BLUE_ALLIANCE
    }

    public enum Orientation {
        NORTH,
        WEST,
        SOUTH,
        EAST
    }

    private enum AutonomousState {
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


    public static enum StartPos {
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
        public double xLocation = 0.0f;
        public double yLocation = 0.0f;
        public Orientation orientation = Orientation.WEST;

        @Override
        public String toString()
        {
            return String.format(
                    Locale.US,
                    "x loc=%.0f %n" +
                            "y loc=%.0f %n" +
                            "orientation=\"%s\" %n" +
                            // "state=\"%s\" %n" +
                            "delay=%.0f %n" +
                            "alliance=\"%s\" %n" +
                            "startPos=\"%s\" %n" +
                            // "parkPos=\"%s\" %n" +
                            "autostage=\"%s\" %n" +
                            "xTarget=%.1f %n" +
                            "yTarget=%.1f %n" +
                            "turnTarget=%.0f %n" +
                            "driveTime=%.0f %n" +
                            "drivePower=%.1f",
                    xLocation, yLocation, orientation.toString(), delay, alliance.toString(), startPos.toString(), /*parkPos,*/ autonomousStage.toString(), xTarget, yTarget, turnTarget, driveTime, drivePower);
        }
    }

    public static final AutoChoices autoChoices = new AutoChoices();

    //public ArrayList<AutoTask> tasks;

    ParkTask pTask = new ParkTask();

    StageState stageState = new StageState();

    HardwareController hardCont;

    private Robot robot;
    public Point location;

    // PURPLE PIXEL PLACING STUFF
    public final int PROP_ID = 420; // APRIL TAG ID
    public final float LEFT_PROP_X_POS = 0.0f; // POSITION OF WHERE THE TEAM PROP NEEDS TO GREATER THAN
                                               // BE FOR IT TO BE CONSIDERED "LEFT"
    public final float CENTER_PROP_X_POS = 0.0f; // POSITION OF WHERE THE TEAM PROP NEEDS TO GREATER THAN
                                                // BE FOR IT TO BE CONSIDERED "CENTER"
    public final float RIGHT_PROP_X_POS = 0.0f; // POSITION OF WHERE THE TEAM PROP NEEDS TO GREATER THAN
                                                // BE FOR IT TO BE CONSIDERED "RIGHT"
    private void moveToTarget() {
        double xDif = autoChoices.xLocation - autoChoices.xTarget; // target < location = +
        double yDif = autoChoices.yLocation - autoChoices.yTarget;
        double temp;

        switch (autoChoices.orientation) {
            // Accounts for the possible orientations of the bot
            case NORTH:
                xDif *= -1;

                temp = xDif;
                xDif = yDif;
                yDif = temp;
                break;
            case EAST:
                yDif *= -1;
                break;
            case WEST:
                xDif *= -1;
                break;
            case SOUTH:
                yDif *= -1;

                temp = xDif;
                xDif = yDif;
                yDif = temp;
                break;
            default:
                break;
        }

        //hardCont.driveTo((float) xDif, (float) yDif);
        hardCont.driveBackwards((float) xDif);

        autoChoices.xLocation = autoChoices.xTarget;
        autoChoices.yLocation = autoChoices.yTarget;
    }

    public void runOpMode() throws InterruptedException {
        robot = new Robot(autoChoices.startPos, hardwareMap);
        hardCont = new HardwareController(hardwareMap);

        switch (autoChoices.alliance) {
            case BLUE_ALLIANCE:
                autoChoices.xLocation = RobotSettings.ROBOT_LENGTH  / 2;
                break;
            default:
                autoChoices.xLocation = RobotSettings.FULL_FIELD_INCHES - (RobotSettings.ROBOT_LENGTH / 2);
                break;
        }

        switch (autoChoices.startPos) {
            case AUDIENCE:
                autoChoices.yLocation = (5 * RobotSettings.FULL_TILE_INCHES) - (RobotSettings.ROBOT_WIDTH / 2);
                break;
            default:
                autoChoices.yLocation = (2 * RobotSettings.FULL_TILE_INCHES) + (RobotSettings.ROBOT_WIDTH / 2);
                break;
        }

        int teamPropPos = 0;

        waitForStart();
        while (opModeIsActive()) {

            // switch for what we do
            switch (autoChoices.autonomousStage) {
                case START:
                    // TODO: Implement code to determine position of the team prop
                    // POSSIBLE 10 Extra Points

                     // means nothing rn

                        /*if (robot.vision != null) {
                            teamPropPos = robot.vision.getLastDetetectedTeamPropLoc();

                            if (teamPropPos > 0) {
                                msg = "Team Prop found at position " + teamPropPos;
                                // robot.speak(msg);
                            }
                        }

                        if (teamPropPos == 0) {
                            // Vision did not find the team prop, set to default position.
                            teamPropPos = 2;
                            msg = "No team prop found, default to position " + teamPropPos;
                            telemetry.addLine(msg);
                            // robot.speak(msg);
                        }*/
                        // Currently the only other option is Place Purple Pixel
                        autoChoices.autonomousStage = AutonomousState.PLACE_PURPLE_PIXEL;
                        break;
                    case PLACE_PURPLE_PIXEL:
                        // Detect position
                        AprilTagPoseFtc pose = hardCont.getAprilTagWithId(PROP_ID);

                        if(pose.x > RIGHT_PROP_X_POS){
                            // Prop is at the far right (politically) (haha) (laugh)

                        }else if(pose.x > CENTER_PROP_X_POS){
                            // Prop is at the far center (politically) (haha) (laugh) (again)

                        }else{
                            // Prop is at the far left (politic- bit unfunny now isn't it)

                        }

                        // Sets Position of the vertical tape strip relative to the robots start position to be passed into RoadRunner
                        switch (autoChoices.alliance) {
                            case BLUE_ALLIANCE:
                                autoChoices.xTarget =  2 * RobotSettings.FULL_TILE_INCHES - RobotSettings.ROBOT_LENGTH;
                                break;
                            default:
                                autoChoices.xTarget = RobotSettings.FULL_FIELD_INCHES - (2 * RobotSettings.FULL_TILE_INCHES) + RobotSettings.ROBOT_LENGTH;
                                break;
                        }

                        switch (autoChoices.startPos) {
                            case AUDIENCE:
                            default:
                                autoChoices.yTarget = autoChoices.yLocation;
                                break;
                        }

                        // TODO: Add Code for using KNOWN team prop location to drive to correct spike strip for +10 points
                        switch (teamPropPos) {
                            case 0:
                            default:
                            break;
                        }
                        moveToTarget();
                        hardCont.ejectIntake();
                        autoChoices.autonomousStage = AutonomousState.PARK_AT_BACKSTAGE;
                        break;
                    case PARK_AT_BACKSTAGE:
                        /* pTask.runTaskTick(stageState, hardCont);
                        if (pTask.isFinished()) {

                            autoChoices.autonomousStage = AutonomousState.DONE;
                        }*/

                        autoChoices.yTarget = RobotSettings.FULL_TILE_INCHES / 2;
                        switch (autoChoices.alliance) {
                            case RED_ALLIANCE:
                                autoChoices.xTarget = RobotSettings.FULL_FIELD_INCHES - RobotSettings.FULL_TILE_INCHES / 2;
                                break;
                            default:
                                autoChoices.xTarget = RobotSettings.FULL_TILE_INCHES / 2;
                        }

                        moveToTarget();
                        break;

            }
        }
    }

}