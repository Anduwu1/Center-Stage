package org.firstinspires.ftc.teamcode.resources;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

/**
    Makes sure trajectories are linked together
 */
public class RoadRunnerHelper {
    private SampleMecanumDrive drive;
    private Trajectory prev;

    public RoadRunnerHelper(SampleMecanumDrive drive){
        this.drive = drive;
        prev = null;
    }

    // Move functions
    public void Forward(double dist){
        Trajectory traj;
        if(prev == null){
            traj = drive.trajectoryBuilder(new Pose2d()).forward(dist).build();
        }else{
            traj = drive.trajectoryBuilder(prev.end()).forward(dist).build();
        }
        drive.followTrajectory(traj);
        prev = traj;
    }

    public void Reverse(double dist){
        Trajectory traj;
        if(prev == null){
            traj = drive.trajectoryBuilder(new Pose2d()).back(dist).build();
        }else{
            traj = drive.trajectoryBuilder(prev.end()).back(dist).build();
        }
        drive.followTrajectory(traj);
        prev = traj;
    }

    /**
     * Turn to angle (World relative)
     * @param angle Turn angle
     */
    public void Turn(double angle)  {
        Trajectory traj;
        if(prev == null){
            throw new RotateWithoutPreviousPathException();
        }else{
            traj = drive.trajectoryBuilder(prev.end()).splineTo(new Vector2d(prev.end().getX() + 0.01, prev.end().getY() - 0.01) , Math.toRadians(angle)).build();
        }
        drive.followTrajectory(traj);
        prev = traj;
    }

    // Strafe
    public void StrafeRight(double dist){
        Trajectory traj;
        if(prev == null){
            traj = drive.trajectoryBuilder(new Pose2d()).strafeRight(dist + (dist * 0.13)).build();
        }else{
            traj = drive.trajectoryBuilder(prev.end()).strafeRight(dist + (dist * 0.13)).build();
        }
        drive.followTrajectory(traj);
        prev = traj;
    }

    public void StrafeLeft(double dist){
        Trajectory traj;
        if(prev == null){
            traj = drive.trajectoryBuilder(new Pose2d()).strafeLeft(dist + (dist * 0.1)).build();
        }else{
            traj = drive.trajectoryBuilder(prev.end()).strafeLeft(dist + (dist * 0.1)).build();
        }
        drive.followTrajectory(traj);
        prev = traj;
    }

}

