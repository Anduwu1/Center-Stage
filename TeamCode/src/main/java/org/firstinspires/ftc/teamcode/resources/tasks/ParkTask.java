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
        // Figure out where we at
        // Move (parkLocation - currentPos)

        // Awesome
    }
}
