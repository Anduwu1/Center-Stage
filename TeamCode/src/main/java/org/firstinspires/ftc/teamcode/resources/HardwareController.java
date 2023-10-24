package org.firstinspires.ftc.teamcode.resources;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    // Subsystems
    private Bucket bucket;
    private Arm arm;
    private Intake intake;

    // Roadrunner drive
    SampleMecanumDrive drive;

    // Pass in a hardware map please
    public HardwareController(HardwareMap hardwareMap){
        telemetry.addLine("[HardwareMap] Init HardwareMap");
        drive = new SampleMecanumDrive(hardwareMap);
        // Since the bot isn't finished yet, these may not exist
        // but we may still want to test other functionality so this
        // makes sure that it doesn't crash
        try{
            arm.armServo = hardwareMap.get(Servo.class, "ArmServo");
            bucket.bucketServoOne = hardwareMap.get(Servo.class, "BucketServoOne");
            bucket.bucketServoTwo = hardwareMap.get(Servo.class, "BucketServoTwo");
            intake.intakeMotor = hardwareMap.get(DcMotor.class, "IntakeMotor");
            telemetry.addLine("Subsystems initialized (awesome)");
        } catch(Exception e){
            // uhhh
            telemetry.addLine("[HardwareMap] Error. errm.. Embarrassing");
            telemetry.speak("errrm we have an error (cringe)"); // test (remove in the future)
        }

        telemetry.update();
    }

    /*
        DRIVE STUFF
     */
    public void driveTo(float xPos, float yPos){
        drive.followTrajectory(drive.trajectoryBuilder(new Pose2d())
                .lineTo(new Vector2d(xPos, yPos))
                .build());
    }

}
