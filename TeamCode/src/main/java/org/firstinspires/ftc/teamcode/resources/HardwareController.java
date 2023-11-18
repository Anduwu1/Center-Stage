package org.firstinspires.ftc.teamcode.resources;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
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


    // Robot we're controlling
    public Robot robot;

    // Roadrunner drive
    SampleMecanumDrive drive;
    HardwareMap hardwareMap;

    // Pass in a hardware map please
    @SuppressLint("SuspiciousIndentation")
    public HardwareController(HardwareMap _hardwareMap){
        this.hardwareMap = _hardwareMap;

        bucket = new Bucket();
        arm = new Arm();
        intake = new Intake();

        bucket.bucketRotation = this.hardwareMap.get(Servo.class, bucket.ROTATION_SERVO);
        bucket.bucketTrapdoor = this.hardwareMap.get(Servo.class, bucket.TRAPDOOR_SERVO);
        arm.armServo = this.hardwareMap.get(Servo.class, arm.arm);

        try {
            drive = new SampleMecanumDrive(this.hardwareMap);

        }catch (Exception e){

        }
        // Since the bot isn't finished yet, these may not exist
        // but we may still want to test other functionality so this
        // makes sure that it doesn't crash
        //try{


            // arm.armServo = hardwareMap.get(Servo.class, "servoA");
            // bucket.bucketRotation = hardwareMap.get(Servo.class, RobotSettings.bucket.ROTATION_SERVO);
            // bucket.bucketTrapdoor = hardwareMap.get(Servo.class, RobotSettings.bucket.TRAPDOOR_SERVO);
            // intake.intakeMotor = hardwareMap.get(DcMotor.class, RobotSettings.intake.MOTOR);


        /*}catch(Exception e){
            // uhhh
            //telemetry.addLine("[HardwareMap] Error. errm.. Embarrassing");
            //telemetry.speak("errrm we have an error (cringe)"); // test (remove in the future)
        }*/

        //telemetry.update();
    }

    /*
        DRIVE STUFF
     */
    public void driveTo(float xPos, float yPos){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .splineTo(new Vector2d(xPos, yPos), Math.toRadians(0))
                .build());
        //telemetry.addLine("If this shows up sooner than expected \" follow trajectory \" is asyncrohjnousl> i thinik");
        //telemetry.update();
    }


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

}
