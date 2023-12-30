package org.firstinspires.ftc.teamcode.actionSystem;

import java.util.ArrayList;

/*
       Manages actions and when they're executed
*/
public class ActionManager {


    // List of all current actions in queue
    private ArrayList<Action> actionQueue;



    public ActionManager(/* something */){

    }

    /**
     * Update
     */
    public void updateTick(){
        // Update this in the future to handle URGENT tasks
        if(actionQueue.size() > 0){
            // Execute top-most action in queue
            actionQueue.get(actionQueue.size() - 1).executeAction();
            // Action is finished, remove it from the queue
            actionQueue.remove(actionQueue.get(actionQueue.size() - 1));
        }
    }

    /**
     * Returns a summary of action in queue, presumably for use in telemetry
     * @return summary
     */
    public String getSummary(){
       String sum = new String("IN PROG");

       return sum;
    }

    private int lastHigh = 0;
    /**
     * Pushes an Action to the queue based on its priority
     */
    public void pushAction(Action action){
        switch(action.getLevel()){
            case LOW:
                // Push to the end, not very important
                actionQueue.add(0, action);
                break;
            case NORMAL:
                // Push action to nearest it can be at the top without it superseding a high priority task
                actionQueue.add((actionQueue.size() - 1) - lastHigh, action);
                break;
            case HIGH:
                // Push to front
                actionQueue.add(action);
                // Make sure that "NORMAL" priority tasks know their place
                lastHigh = actionQueue.size() - 1;
                break;
            case URGENT:
                // DO IT NOW
                break;
        }
    }


}

