package org.firstinspires.ftc.teamcode.resources;


import com.qualcomm.robotcore.hardware.Servo;

/*
    Class for making the robot like
    do stuff
 */
public class hardwareController {
    // Servos
    private Servo armServo = null;
    private Servo bucketServo = null;

    public hardwareController(){
        // Since the bot isnt finished yet, these may not exist
        // but we may still want to test other functionality so this
        // makes sure that it doesnt crash
        try{

        }catch(Exception e){

        }
    }

}
