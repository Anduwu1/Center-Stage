package org.firstinspires.ftc.teamcode.resources.taskManagment;

public abstract class AutoTask {
    private String taskName;
    private boolean taskFinished = false;
    public abstract void runTaskTick(StageState state);

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
