package org.firstinspires.ftc.teamcode.resources;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Distance;
import org.firstinspires.ftc.teamcode.subsystems.Drone;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

import java.util.List;

/*
    Class for making the robot like
    do stuff
 */
public class HardwareController{

    // Subsystems
    private Bucket bucket;
    private Arm arm;
    private Intake intake;
    private Distance dsensors;
    private Camera camera;
    private Lift lift;
    private Drone drone;
    private Claw claw;

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


        //camera = new Camera(this.hardwareMap);


        // Servos
        drone = new Drone(hardwareMap.get(Servo.class, Drone.HARDWARE_NAME));
        claw = new Claw(hardwareMap.get(Servo.class, Claw.HARDWARE_NAME));
        bucket = new Bucket(hardwareMap.get(Servo.class, Bucket.HARDWARE_NAME));
        arm = new Arm(hardwareMap.get(Servo.class, Arm.HARDWARE_NAME));
        dsensors = new Distance(hardwareMap.get(DistanceSensor.class, Distance.HARDWARE_NAME_LEFT), hardwareMap.get(DistanceSensor.class, Distance.HARDWARE_NAME_RIGHT));

        // motors
        intake = new Intake(hardwareMap.get(DcMotor.class, Intake.HARDWARE_NAME));
        lift = new Lift(this.hardwareMap.get(DcMotorEx.class, Lift.HARDWARE_NAME));

        // Init drive
        //drive = new SampleMecanumDrive(this.hardwareMap);

    }

    public Drone getDrone() {
        return drone;
    }

    public Claw getClaw() {
        return claw;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public Arm getArm() {
        return arm;
    }

    public Distance getDistanceSensors() {
        return dsensors;
    }

    public Intake getIntake() {
        return intake;
    }

    public Lift getLift() {
        return lift;
    }

    public SampleMecanumDrive getDrive(){
        return drive;
    }

    // Camera stuff
    public List<AprilTagDetection> getAllAprilTags(){
        return camera.getAllVisibleAprilTags();
    }

    public AprilTagPoseFtc getAprilTagWithId(int id){
        AprilTagPoseFtc tmp = new AprilTagPoseFtc(0,0,0,0,0,0,-100,0,0); // default
        for(AprilTagDetection april : this.getAllAprilTags()){
            if(april.id == id){
                tmp = april.ftcPose;
                break;
            }
        }
        return tmp;
    }
}
