package org.firstinspires.ftc.teamcode.resources.taskManagment;

import org.firstinspires.ftc.teamcode.resources.HardwareController;

public abstract class AutoTask {
    private String taskName;
    private boolean taskFinished = false;
    public abstract void runTaskTick(StageState state, HardwareController controller);

    public void setTaskName(String newTaskName){
        this.taskName = newTaskName;
    }
    public String getTaskName(){
        return this.taskName;
    }
    public boolean isFinished(){
        return this.taskFinished;
    }

}
