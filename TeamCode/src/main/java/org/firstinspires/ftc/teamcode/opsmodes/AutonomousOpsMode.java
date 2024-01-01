package org.firstinspires.ftc.teamcode.opsmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.OpenCVManager;
import org.firstinspires.ftc.teamcode.resources.Pipelines.AutoPipeLine;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Distance;

@Autonomous(group="drive")
public class AutonomousOpsMode extends LinearOpMode {

    private enum Position {
        LEFT(1),
        CENTER(2),
        RIGHT(3);

        private int id;

        Position(int position) {
            this.id = position;
        }

        public static Position fromId(int id) {
            for (Position position : values()) {
                if (position.id == id) {
                    return position;
                }
            }
            throw new IllegalArgumentException("No enum constant with id " + id);
        }
    }

    Claw claw;
    Arm arm;
    Bucket bucket;

    Distance distance;
    SampleMecanumDrive drive;


    // Drive
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightBackDrive = null;

    OpenCVManager camMan;
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
        AutoPipeLine pipe = new AutoPipeLine();
        camMan.setPipeline(pipe);

        // Set speed


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
            movePixelToPos(Position.RIGHT);
        }else if(pixelPos > RobotSettings.PIXEL_LEFT){
            pos = "CENTER";
            movePixelToPos(Position.CENTER);
        }else{
            pos = "LEFT";
            movePixelToPos(Position.LEFT);
        }
        //  time

        // idle
        while (!isStopRequested() && opModeIsActive()){
            telemetry.addData("X:","%d", pipe.getX());
            telemetry.addLine(pos);
            telemetry.update();
        }
    }


    /*
        This needs to be fixed to be better
        AutoAction class maybe
        or just general cleanup
     */
    private void movePixelToPos(Position position) {
        Trajectory moveForward = drive.trajectoryBuilder(new Pose2d()).forward(27).build();
        // Go to position
        switch(position){
            case LEFT:
                drive.followTrajectory(moveForward);
                Trajectory rotateL90Left = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX() + 0.01, moveForward.end().getY() - 0.01) , Math.toRadians(90.0)).build();
                drive.followTrajectory(rotateL90Left);
                moveForward = drive.trajectoryBuilder(rotateL90Left.end()).forward(5).build();
                drive.followTrajectory(moveForward);
                claw.open();
                Trajectory moveBack = drive.trajectoryBuilder(moveForward.end()).back(25).build();
                drive.followTrajectory(moveBack);
                claw.close();
                driveToBackdrop(moveBack, position);
                break;

            case CENTER:
                Trajectory moveForwardCenter = drive.trajectoryBuilder(new Pose2d()).forward(31).build();
                drive.followTrajectory(moveForwardCenter);
                claw.open();
                Trajectory moveBackCenter = drive.trajectoryBuilder(moveForward.end()).back(4.5).build();
                drive.followTrajectory(moveBackCenter);
                claw.close();
                Trajectory rotateL90Center = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX() + 0.01, moveForward.end().getY() - 0.01) , Math.toRadians(90.0)).build();
                drive.followTrajectory(rotateL90Center);
                moveBackCenter = drive.trajectoryBuilder(rotateL90Center.end()).back(20).build();
                drive.followTrajectory(moveBackCenter);
                driveToBackdrop(moveBackCenter, position);
                break;

            case RIGHT:
                drive.followTrajectory(moveForward);
                Trajectory rotateL90 = drive.trajectoryBuilder(moveForward.end()).splineTo(new Vector2d(moveForward.end().getX() + 0.01, moveForward.end().getY() - 0.01) , Math.toRadians(90.0)).build();
                drive.followTrajectory(rotateL90);
                Trajectory moveBackRight = drive.trajectoryBuilder(rotateL90.end()).back(20).build();
                drive.followTrajectory(moveBackRight);
                claw.open();
                moveBackRight = drive.trajectoryBuilder(moveBackRight.end()).back(5).build();
                drive.followTrajectory(moveBackRight);
                claw.close();
                driveToBackdrop(moveBackRight, position);
                break;
            default:
                break;
        }
    }

    private void driveToBackdrop(Trajectory start, Position position){
        Trajectory moveBackBg = drive.trajectoryBuilder(start.end()).back(66).build();
        drive.followTrajectory(moveBackBg);
        arm.moveToUpPosition();
        bucket.moveToDropPositionAndWait();
        while(distance.getLeftDistance() > 2 && distance.getRightDistance() > 2){
            rightBackDrive.setPower(-0.1);
            leftBackDrive.setPower(-0.1);
        }
        rightBackDrive.setPower(0);
        leftBackDrive.setPower(0);

        // move left/right based on position of team prop position
        if (position == Position.LEFT)
            drive.followTrajectory(drive.trajectoryBuilder(moveBackBg.end()).strafeLeft(6).build());
        else if (position == Position.RIGHT)
            drive.followTrajectory(drive.trajectoryBuilder(moveBackBg.end()).strafeRight(6).build());

        // Drop em
        claw.open();
    }
}
