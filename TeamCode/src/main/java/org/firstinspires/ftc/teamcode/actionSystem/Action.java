package org.firstinspires.ftc.teamcode.actionSystem;

public abstract class Action {

    protected Action() {

    }

    public enum ActionUrgency{
        LOW, // example: Read april tags for position (low becuase we already have great encoders)
        NORMAL, // ex. Move to a position
        HIGH, // ex. Use vision to detect obstacles
        URGENT  // ex. We are about to hit something, MOVE
    }

    protected String name;
    protected ActionUrgency level;

    public Action(String name){
        this.name = name;
    }


    // The action at hand
    public abstract void executeAction();

    public String getName(){
        return name;
    }
    public ActionUrgency getLevel(){
        return level;
    }
}
