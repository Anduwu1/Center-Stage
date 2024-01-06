package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.objects.Marker;

@Autonomous(group="drive")
public class Auto_BLUE_NEAR_SIDE extends AutonomousBaseNearSide {

    @Override
    public Marker getMarker() {
        return Marker.BLUE;
    }
}
