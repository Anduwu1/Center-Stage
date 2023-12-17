package org.firstinspires.ftc.teamcode.resources.taskManagment;


public abstract class AutoTask {
    protected String taskName;
    protected boolean taskFinished = false;

    public AutoTask(String name){
        this.taskName = name;
    }

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
