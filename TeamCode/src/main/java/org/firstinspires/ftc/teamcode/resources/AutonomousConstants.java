package org.firstinspires.ftc.teamcode.resources;

/*
    Holds constants for different Auto opsmodes
 */

public class AutonomousConstants {
    /**
     * Holds constants for when the Robot is at
     * the Far side on the red pos
     */
    public class RedFarSide{
        public static final int BaseMoveForward = 27;

        // Left stuff
        public static final int LeftForwardForPixelPlace = 5;
        public static final int LeftBackwardToReachBackDropFunctionHandOff = 25;

        // Center stuff
        public static final int CenterMoveForward = 31;
        public static final double CenterMoveBack = 4.5;
        public static final int CenterReachBackDropFunctionHandOff = 20;

        // Right
        public static final int RightMoveBackPlacePixel = 20;
        public static final int RightMoveBackBackDropFunctionHandOff = 5;
    }
}
