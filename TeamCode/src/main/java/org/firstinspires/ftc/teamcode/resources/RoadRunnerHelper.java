package org.firstinspires.ftc.teamcode.resources;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;

/**
    Makes sure trajectories are linked together
 */
public class RoadRunnerHelper{
    private SampleMecanumDrive drive;
    private Pose2d pose;

    public RoadRunnerHelper(SampleMecanumDrive drive){
        this.drive = drive;
        pose = new Pose2d();
    }

    // [NOTE] All functions return RoadRunnerHelper so its possible
    // to chain functions together
    // (im assuming thats how roadrunner does it internally idk i havent checked)

    // Move functions

    /**
     * I dare you to guess what this does
     * @param dist
     */
    public RoadRunnerHelper forward(double dist){
        Trajectory traj = drive.trajectoryBuilder(pose).forward(dist).build();
        drive.followTrajectory(traj);
        pose = traj.end();
        return this;
    }

    public RoadRunnerHelper reverse(double dist){
        Trajectory traj = drive.trajectoryBuilder(pose).back(dist).build();
        drive.followTrajectory(traj);
        pose = traj.end();
        return this;
    }

    /**
     * Turn to angle (positive is turning left)
     * @param angle Turn angle
     */
    public RoadRunnerHelper turn(double angle)  {
        Trajectory traj = null;
        if(pose == null){
            throw new RotateWithoutPreviousPathException();
        }else{
            traj = drive.trajectoryBuilder(pose).splineTo(new Vector2d(pose.getX() + 0.01, pose.getY() - 0.01) , Math.toRadians(angle)).build();
        }
        drive.followTrajectory(traj);
        pose = traj.end();
        return this;
    }

    /**
     * Turn to angle with speed (positive is turning left)
     * @param angle Turn angle (in degrees)
     * @param speed Rotation speed
     * @param angAcc Max angular acceleration
     */
    public RoadRunnerHelper turn(double angle, double speed, double angAcc)  {
        TrajectorySequence traj = drive.trajectorySequenceBuilder(pose).turn(Math.toRadians(angle), speed, angAcc).build();
        drive.followTrajectorySequence(traj);
        pose = traj.end();
        return this;
    }

    // Strafe
    public RoadRunnerHelper strafeRight(double dist){
        Trajectory traj = drive.trajectoryBuilder(pose).strafeRight(dist + (dist * 0.13)).build();
        drive.followTrajectory(traj);
        pose = traj.end();
        return this;
    }

    public RoadRunnerHelper strafeLeft(double dist){
        Trajectory traj = drive.trajectoryBuilder(pose).strafeLeft(dist + (dist * 0.13)).build();
        drive.followTrajectory(traj);
        pose = traj.end();
        return this;
    }

    /**
     * Spline to location
     * @param x X Location (wow)
     * @param y Y Location (crazy)
     * @param ang Angle (degrees) (wack)
     * @return
     */
    public RoadRunnerHelper splineToLinearHeading(double x, double y, double ang){
        Trajectory traj = drive.trajectoryBuilder(pose).splineToLinearHeading(new Pose2d(x, y, Math.toRadians(ang)), 0).build();
        drive.followTrajectory(traj);
        pose = traj.end();
        return this;
    }

    /**
     * Waits for seconds
     * @param seconds wait for seconds
     * @return Not important, dont worry about it
     * @throws InterruptedException oops
     */
    public RoadRunnerHelper waitSeconds(double seconds) throws InterruptedException {
        drive.wait((long) seconds);
        return this;
    }


    /**
     * Clears the current path to start a new one
     * I don't know when you would use this but
     * why not maybe some edge case
     */
    public void resetPath(){
        pose = null;
    }

}

