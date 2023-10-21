package org.firstinspires.ftc.teamcode.resources.tasks;

import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.teamcode.resources.taskManagment.AutoTask;
import org.firstinspires.ftc.teamcode.resources.taskManagment.StageState;

public class ParkTask extends AutoTask {


    public ParkTask() {
        super("ParkTask");
    }

    @Override
    public void runTaskTick(StageState state, HardwareController controller) {
        // General steps:

        // Read state of stage
        if(state.isParked()){
            this.taskFinished = true;
            return;
        }
        // Figure out where we at
        int c;
        // Move (parkLocation - currentPos)

        // Awesome
    }
}
