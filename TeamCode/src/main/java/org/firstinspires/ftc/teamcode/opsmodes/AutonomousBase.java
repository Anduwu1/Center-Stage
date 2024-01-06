package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.objects.Marker;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.OpenCVManager;
import org.firstinspires.ftc.teamcode.resources.Pipelines.AutoPipeLine;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Distance;

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

    // Drive
    protected DcMotorEx leftBackDrive = null;
    protected DcMotorEx rightBackDrive = null;

    protected OpenCVManager camMan;

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

        int pixelPos = 0;
        while(!isStarted()){
            // Get pos
            pixelPos = pipe.getX();
            telemetry.addData("X:","%d", pixelPos);
            telemetry.update();

        }
        String pos = "None";
        // Move to position based on pixelPos
        if(pixelPos > RobotSettings.PIXEL_CENTER){
            pos = "RIGHT";
            markerOnRight();
        }
        else if(pixelPos > RobotSettings.PIXEL_LEFT){
            pos = "CENTER";
            markerOnCenter();
        }
        else{
            pos = "LEFT";
            markerOnLeft();
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

    public abstract Marker getMarker();
}
