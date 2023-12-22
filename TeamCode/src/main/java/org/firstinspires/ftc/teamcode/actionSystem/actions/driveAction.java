package org.firstinspires.ftc.teamcode.actionSystem.actions;

import org.firstinspires.ftc.teamcode.actionSystem.Action;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

public class driveAction extends Action {

    private SampleMecanumDrive drive;

    /*
        Fix this later to match specific needs
        right now nothings here because its a demo
     */
    public driveAction(/* args*/) {
        this.name = "DRIVE ACTION TO [POS]";
    }

    @Override
    public void executeAction() {
        while(drive.isBusy()){
            // Wait
        }
    }
}
