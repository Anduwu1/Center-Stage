package org.firstinspires.ftc.teamcode.resources;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;

/*
    Class for making the robot like
    do stuff
 */
public class hardwareController{
    // Servos


    private Bucket bucket;
    private Arm arm;

    public hardwareController(HardwareMap hardwareMap){
        // Since the bot isn't finished yet, these may not exist
        // but we may still want to test other functionality so this
        // makes sure that it doesn't crash
        try{
            arm.armServo = hardwareMap.get(Servo.class, "ArmServo");
            bucket.bucketServoOne = hardwareMap.get(Servo.class, "BucketServoOne");
            bucket.bucketServoTwo = hardwareMap.get(Servo.class, "BucketServoTwo");
        }catch(Exception e){

        }
    }

}
