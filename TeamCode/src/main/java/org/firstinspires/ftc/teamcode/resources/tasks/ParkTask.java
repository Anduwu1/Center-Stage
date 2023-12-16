package org.firstinspires.ftc.teamcode.resources.tasks;

import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.teamcode.resources.taskManagment.AutoTask;
import org.firstinspires.ftc.teamcode.resources.taskManagment.StageState;

public class ParkTask extends AutoTask {

    private boolean movingToParkPos = false;

    public ParkTask() {
        super("ParkTask");
    }

    @Override
    public void runTaskTick(StageState state, HardwareController controller) {
        if(this.taskFinished) return; // Just in case someone calls this function even after its finished
                                      // in theory this shouldn't happen but you know just in case
        // General steps:

        // Read state of stage
        if(state.isParked()){ // idk man
            this.taskFinished = true;
            return;
        }else if(!movingToParkPos){
            // We need to start moving
            // Somehow(?) reset our position to a favorable area
            movingToParkPos = true;
            // controller.driveTo((float) (RobotSettings.FULL_TILE_INCHES * 1.5), 0); //TODO move this code to autonomous section, because teleop doesn't care about it
            // Get our current alliance
            /*switch(controller.robot.alliance){
                case RED_ALLIANCE:
                    controller.driveTo(controller.robot.position.get_x() - RobotSettings.BACKSTAGE_RED, controller.robot.position.get_y() - (float)(RobotSettings.FULL_FIELD_INCHES - (RobotSettings.FULL_TILE_INCHES / 2.0f)));
                    break;
                case BLUE_ALLIANCE: // not "default" for readability
                    controller.driveTo(controller.robot.position.get_x() - RobotSettings.BACKSTAGE_BLUE, controller.robot.position.get_y() - (float)(RobotSettings.FULL_FIELD_INCHES - (RobotSettings.FULL_TILE_INCHES / 2.0f)));
                    break;
            }*/

        }else{
            // It's moving, and its not done yet so
            // wait ig?
        }


        // Awesome
    }
}
