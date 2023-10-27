package org.firstinspires.ftc.teamcode.opsmodes;
// Basic stuff

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.Locale;
// New vision system
import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.teamcode.resources.taskManagment.StageState;
import org.firstinspires.ftc.teamcode.resources.tasks.ParkTask;

// Default java stuff


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

        @Override
        public String toString()
        {
            return String.format(
                    Locale.US,
                            "state=\"%s\" \n" +
                            "delay=%.0f \n" +
                            "alliance=\"%s\" \n" +
                            "startPos=\"%s\" \n" +
                            // "parkPos=\"%s\" \n" +
                            "autostage=\"%s\" \n" +
                            "xTarget=%.1f \n" +
                            "yTarget=%.1f \n" +
                            "turnTarget=%.0f \n" +
                            "driveTime=%.0f \n" +
                            "drivePower=%.1f",
                    state, delay, alliance,startPos, /*parkPos,*/ autonomousStage, xTarget, yTarget, turnTarget, driveTime, drivePower);
        }
    }

    public static final AutoChoices autoChoices = new AutoChoices();
    private Robot robot;

    //public ArrayList<AutoTask> tasks;

    ParkTask pTask = new ParkTask();

    StageState stageState = new StageState();

    HardwareController hardCont = new HardwareController(hardwareMap, robot);



    public void runOpMode() throws InterruptedException {
        while (autoChoices.state == RobotState.RUNNING) {
            // Update Telemetry
            telemetry.addLine(autoChoices.toString());
            telemetry.update();

            // switch for what we do
            switch (autoChoices.autonomousStage) {
                case START:

                    int teamPropPos = 0; // means nothing rn
                    String msg;

                    robot = new Robot(autoChoices.startPos, hardwareMap);

                    hardCont.robot = robot;

                    if (robot.vision != null) {
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
                    }
                    // Currently the only other option is PARK
                    autoChoices.autonomousStage = AutonomousState.PLACE_PURPLE_PIXEL;
                    break;
                case PLACE_PURPLE_PIXEL:

                    break;
                case PARK_AT_BACKSTAGE:
                    pTask.runTaskTick(stageState, hardCont);
                    if(pTask.isFinished()){
                        autoChoices.autonomousStage = AutonomousState.DONE;
                    }
                    break;

            }
        }
    }

}