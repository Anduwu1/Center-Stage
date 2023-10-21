package org.firstinspires.ftc.teamcode.resources.taskManagment;

import org.firstinspires.ftc.teamcode.objects.Pixel;

import java.util.ArrayList;
/*
    Current state of the game field
    passed into AutoTask's so they know what
    they need to do to complete their task
 */
public class StageState {
    // This is for our board
    private ArrayList<Pixel> pixels;

    // TODO "Make work with Driver assists"


    // Things that can happen in autonomous
    private boolean botParked = false;



    public ArrayList<Pixel> getPixels(){
        return this.pixels;
    }

    public boolean isParked(){
        return botParked;
    }
    public void updateParkedStatus(boolean newParkedState){
        this.botParked = newParkedState;
    }
}
