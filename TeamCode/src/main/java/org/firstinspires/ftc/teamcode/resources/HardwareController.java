package org.firstinspires.ftc.teamcode.resources;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.DistanceSensors;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

/*
    Class for making the robot like
    do stuff
 */
public class HardwareController{

    public enum Servo_Type{
        ARM_SERVO,
        BUCKET_SERVO,
        DOOR_SERVO
    }

    // Subsystems
    private Bucket bucket;
    private Arm arm;
    private Intake intake;
    private DistanceSensors dsensors;

    // Robot we're controlling
    public Robot robot;

    // Roadrunner drive
    SampleMecanumDrive drive;
    HardwareMap hardwareMap;

    // Pass in a hardware map please
    @SuppressLint("SuspiciousIndentation")
    public HardwareController(HardwareMap _hardwareMap) {
        this.hardwareMap = _hardwareMap;

        // Create subsystems
        bucket = new Bucket();
        arm = new Arm();
        intake = new Intake();
        dsensors = new DistanceSensors();

        // Init servos
        bucket.bucketRotation = this.hardwareMap.get(Servo.class, bucket.ROTATION_SERVO);
        bucket.bucketTrapdoor = this.hardwareMap.get(Servo.class, bucket.TRAPDOOR_SERVO);

        arm.armServo = this.hardwareMap.get(Servo.class, arm.arm);

        // Init distance sensors
        dsensors.left = this.hardwareMap.get(DistanceSensor.class, dsensors.leftSense);
        dsensors.right = this.hardwareMap.get(DistanceSensor.class, dsensors.rightSense);

        // Init drive
        drive = new SampleMecanumDrive(this.hardwareMap);

    }

    /*
        DRIVE STUFF
     */
    public void driveTo(float xPos, float yPos){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .lineTo(new Vector2d(xPos, yPos))
                .build());
    }

    public void driveForward(float distance){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .forward(distance).build());
    }

    public void driveBackwards(float distance){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .back(distance).build());
    }

    public void driveLeft(float distance){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(distance).build());
    }

    public void driveRight(float distance){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .strafeRight(distance).build());
    }

    /*
        Servo stuff
        (maybe rewrite later? the Servo_Type arg is kinda
        icky imo)
     */
    public void servoMove(float to, @NonNull Servo_Type sT){
        switch (sT){
            case ARM_SERVO:
                arm.armServo.setPosition(to);
                break;
            case DOOR_SERVO:
                bucket.bucketTrapdoor.setPosition(to);
                break;
            case BUCKET_SERVO:
                bucket.bucketRotation.setPosition(to);
                break;
        }
    }

    public boolean align() {
        // TODO: figure out how to calculate the start pose

        // get the distances
        double leftD = dsensors.left.getDistance(DistanceUnit.INCH);
        double rightD = dsensors.right.getDistance(DistanceUnit.INCH);

        // set the robot's current position relative to backdrop
        Pose2d startPose = new Pose2d(0,0,0);
        drive.setPoseEstimate(startPose);

        // tell it to go up against the backdrop
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(0.0),0)
                .build());

        if (leftD == 0 && rightD == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void ejectIntake() throws InterruptedException {
        intake.intakeMotor.setPower(.2);
    }

    public double getRightDistance() {
        return dsensors.right.getDistance(DistanceUnit.INCH);
    }

    public double getLeftDistance() {
        return dsensors.left.getDistance(DistanceUnit.INCH);
    }
}
