package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    protected Claw claw;
    protected Arm arm;
    protected Bucket bucket;
    protected Distance distance;
    protected SampleMecanumDrive drive;
    protected AutoPipeLine pipe;
    protected Intake intake;

    // Drive
    protected DcMotorEx leftBackDrive = null;
    protected DcMotorEx rightBackDrive = null;

    protected OpenCVManager camMan;

    protected RoadRunnerHelper driveHelper;

    protected int flip = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);
        claw = new Claw(hardwareMap);
        arm = new Arm(hardwareMap);
        bucket = new Bucket(hardwareMap);
        camMan = new OpenCVManager(hardwareMap);
        distance = new Distance(hardwareMap);
        intake = new Intake(hardwareMap);
        // Motors
        leftBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LBDRIVE_MOTOR);
        rightBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RBDRIVE_MOTOR);
        // Opencv
        pipe = new AutoPipeLine(getMarker());
        camMan.setPipeline(pipe);
        // Road runner helper
        driveHelper = new RoadRunnerHelper(drive);

        int pixelPos = 0;
        String pos = "None";
        arm.moveToHoverPosition();
        claw.close();

        // Should we flip the rotation
        if(getMarker() != Marker.RED){
            flip = -1;
        }

        while(!isStarted()){
            // Get pos
            pixelPos = pipe.getX();
            telemetry.addData("X:","%d", pixelPos);
            telemetry.addData("Side", "%s", getMarker().toString());
            if(pixelPos > RobotSettings.PIXEL_CENTER){
                pos = "RIGHT";
            }
            else if(pixelPos > RobotSettings.PIXEL_LEFT){
                pos = "CENTER";
            }
            else{
                pos = "LEFT";
            }
            telemetry.addData("Projected position","%s" ,pos);
            telemetry.update();

        }

        // Move to position based on pixelPos
        if(pixelPos > RobotSettings.PIXEL_CENTER){
            pos = "RIGHT";
            if(getMarker() != Marker.RED) markerOnLeft();
            else markerOnRight();
        }
        else if(pixelPos > RobotSettings.PIXEL_LEFT){
            pos = "CENTER";
            markerOnCenter();
        }
        else{
            pos = "LEFT";
            if(getMarker() != Marker.RED) markerOnRight();
            else markerOnLeft();
        }

        // idle
        while (!isStopRequested() && opModeIsActive()){
            telemetry.addData("X:","%d", pipe.getX());
            telemetry.addLine(pos);
            telemetry.update();
        }
    }

    public abstract void markerOnLeft();

    public abstract void markerOnCenter();

    public abstract void markerOnRight();

    public void redDropPixels(Position position){
        claw.close();
        arm.moveToAutoDropPosition();
        bucket.moveToAutoDropPos();
        sleep(2500);
        // Insurance
        ElapsedTime insTime = new ElapsedTime();
        double time = insTime.seconds();
        while(distance.getLeftDistance() > 8.3 && distance.getRightDistance() > 8.3){
            rightBackDrive.setPower(-0.1);
            leftBackDrive.setPower(-0.1);
            // If we just dont find it, drop the pixel anyway
            if(Math.abs(time - insTime.seconds()) > 5) break;
        }
        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);
        driveHelper.resetPath();
        // move left/right based on position of team prop position
        if (position == Position.LEFT)
            driveHelper.strafeRight(7.5);
        else if (position == Position.RIGHT)
            driveHelper.strafeLeft(7.5);

        // Drop em
        claw.open();
    }

    public abstract Marker getMarker();
}
