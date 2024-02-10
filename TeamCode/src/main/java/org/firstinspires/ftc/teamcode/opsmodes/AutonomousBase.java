package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.jbbfi.JBBFI;
import org.firstinspires.ftc.teamcode.jbbfi.ScriptingWebPortal;
import org.firstinspires.ftc.teamcode.objects.Marker;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.OpenCVManager;
import org.firstinspires.ftc.teamcode.resources.Pipelines.AutoPipeLine;
import org.firstinspires.ftc.teamcode.resources.RoadRunnerHelper;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Distance;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Disabled
public abstract class AutonomousBase extends LinearOpMode {

    public enum Position {
        LEFT(1),
        CENTER(2),
        RIGHT(3);

        private int id;

        Position(int position) {
            this.id = position;
        }
    }

    protected static final double DISTANCE_TO_DROP = 8.28;

    protected Claw claw;
    protected Arm arm;
    protected Bucket bucket;
    protected Distance distance;
    protected SampleMecanumDrive drive;
    protected AutoPipeLine pipe;

    // Drive
    protected DcMotorEx leftBackDrive = null;
    protected DcMotorEx rightBackDrive = null;

    protected OpenCVManager camMan;

    protected RoadRunnerHelper driveHelper;

    protected JBBFI jbbfi;
    protected ScriptingWebPortal scriptingWebPortal;

    protected int flip = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        claw = new Claw(hardwareMap);
        arm = new Arm(hardwareMap);
        bucket = new Bucket(hardwareMap);
        camMan = new OpenCVManager(hardwareMap);
        distance = new Distance(hardwareMap);
        // Motors
        leftBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LBDRIVE_MOTOR);
        rightBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RBDRIVE_MOTOR);
        // Opencv
        pipe = new AutoPipeLine(getMarker());
        camMan.setPipeline(pipe);
        // Road runner helper
        driveHelper = new RoadRunnerHelper(drive);

        int pixelPos = 0;
        String pos = "None", goingTo = "None";
        arm.moveToDownPosition();
        claw.close();

        // Should we flip the rotation
        if(getMarker() != Marker.RED){
            flip = -1;
        }

        try {
            jbbfi = new JBBFI("/sdcard/auto/autoCode.jbbfi");
            jbbfi.addGlobal(driveHelper, "driveHelper");
            jbbfi.addGlobal(flip, "flip");
        }catch (Exception e){
            e.printStackTrace();
        }

        scriptingWebPortal = new ScriptingWebPortal(hardwareMap.appContext);
        scriptingWebPortal.start();

        bucket.moveToIntakePosition();
        while(!isStarted() && !isStopRequested()){
            // Get pos
            pixelPos = pipe.getX();
            telemetry.addData("X:","%d", pixelPos);
            telemetry.addData("Side", "%s", getMarker().toString());
            if(pixelPos > RobotSettings.PIXEL_CENTER){
                pos = "RIGHT";
                if(getMarker() != Marker.RED) goingTo = "LEFT";
                else goingTo = "RIGHT";
            }
            else if(pixelPos > RobotSettings.PIXEL_LEFT){
                pos = "CENTER";
                if(getMarker() != Marker.RED) goingTo = "CENTER";
                else goingTo = "CENTER";
            }
            else{
                pos = "LEFT";
                if(getMarker() != Marker.RED) goingTo = "RIGHT";
                else goingTo = "LEFT";
            }
            telemetry.addData("TeamProp position","%s" ,pos);
            telemetry.addData("GoTo position","%s" ,goingTo);
            telemetry.update();

        }

        if(!isStopRequested() && opModeIsActive()) {
            // Move to position based on pixelPos
            if (pixelPos > RobotSettings.PIXEL_CENTER) {
                pos = "RIGHT";
                if (getMarker() != Marker.RED) markerOnLeft();
                else markerOnRight();
            } else if (pixelPos > RobotSettings.PIXEL_LEFT) {
                pos = "CENTER";
                markerOnCenter();
            } else {
                pos = "LEFT";
                if (getMarker() != Marker.RED) markerOnRight();
                else markerOnLeft();
            }
        }

        // idle
        while (!isStopRequested() && opModeIsActive()){
            telemetry.addLine(pos);
            telemetry.update();
        }
    }

    public abstract void markerOnLeft();

    public abstract void markerOnCenter();

    public abstract void markerOnRight();

    public void dropPixels(Position position){
        claw.close();
        arm.moveToAutoDropPosition();
        bucket.moveToAutoDropPos();
        // Wait until the arm and bucket are done moving
        sleep(2500);
        // Insurance
        ElapsedTime insTime = new ElapsedTime();
        double time = insTime.seconds();
        while(distance.getLeftDistance() > DISTANCE_TO_DROP && distance.getRightDistance() > DISTANCE_TO_DROP){
            // Reverse
            rightBackDrive.setPower(-0.15);
            leftBackDrive.setPower(-0.15);
            // If we just dont find it, drop the pixel anyway
            if(Math.abs(time - insTime.seconds()) > 5) break;
        }
        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);
        driveHelper.resetPath();
        // move left/right based on position of team prop position
        if (position == Position.LEFT)
            driveHelper.strafeRight(7 * flip);
        else if (position == Position.RIGHT)
            driveHelper.strafeLeft(7.5 * flip);

        // Drop em
        claw.open();
        // Wait
        sleep(800);
        // Reset
        arm.moveToDownPosition();
        bucket.moveToIntakePosition();

        //move out of the way for team member
        if (isNear()) {
            driveHelper.resetPath();
            if (flip < 0)
                driveHelper.strafeRight(28);
            else
                driveHelper.strafeLeft(28);
        }
    }

    public void delayForOther(){
        sleep(5000);
    }

    public abstract Marker getMarker();

    public abstract boolean isNear();
}
