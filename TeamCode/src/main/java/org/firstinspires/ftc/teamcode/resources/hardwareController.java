package org.firstinspires.ftc.teamcode.resources;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
/*
    Class for making the robot like
    do stuff
 */
public class hardwareController{
    // Servos
    private Servo armServo = null;
    private Servo bucketServoOne = null;
    private Servo bucketServoTwo = null;


    public hardwareController(HardwareMap hardwareMap){
        // Since the bot isnt finished yet, these may not exist
        // but we may still want to test other functionality so this
        // makes sure that it doesnt crash
        try{
            armServo = hardwareMap.get(Servo.class, "ArmServo");
            bucketServoOne = hardwareMap.get(Servo.class, "BucketServoOne");
            bucketServoTwo = hardwareMap.get(Servo.class, "BucketServoTwo");

        }catch(Exception e){

        }
    }

}
