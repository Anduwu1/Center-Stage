package org.firstinspires.ftc.teamcode.resources;

public abstract class AutoTask {
    private String taskName;

    public abstract void runTaskTick(StageState state);

    public void setTaskName(String newTaskName){
        this.taskName = newTaskName;
    }
    public String getTaskName(){
        return taskName;
    }

}
