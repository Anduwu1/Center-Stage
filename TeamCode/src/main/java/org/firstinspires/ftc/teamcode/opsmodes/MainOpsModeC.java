package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Distance;
import org.firstinspires.ftc.teamcode.subsystems.Drone;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@TeleOp(name="Main OpMode")
public class MainOpsModeC extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // Motors
    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx rightBackDrive = null;

    // Constants
    private static final double DRIVE_SPEED = 0.8;
    private static final int MAX_VELOCITY = 1700; //true physical max = 2200, we want it slower
    // Servo Constants

    // Subsystems
    private Bucket bucket;
    private Arm arm;
    private Intake intake;
    private Distance dsensors;
    private Camera camera;
    private Lift lift;
    private Drone drone;
    private Claw claw;

    private void initHardware() {

        //init subsystems
        drone = new Drone(hardwareMap);
        claw = new Claw(hardwareMap);
        bucket = new Bucket(hardwareMap);
        arm = new Arm(hardwareMap);
        dsensors = new Distance(hardwareMap);
        intake = new Intake(hardwareMap);
        lift = new Lift(hardwareMap);

        // Init drive motors
        leftFrontDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LFDRIVE_MOTOR);
        leftBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LBDRIVE_MOTOR);
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RFDRIVE_MOTOR);
        rightBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RBDRIVE_MOTOR);
        List<DcMotorEx> motors = Arrays.asList(leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive);

        //set mode
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        // Set directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void runOpMode() throws InterruptedException {

       initHardware();

        // Wait for the game to start (driver presses PLAY)
        // telemetry.addData("Status", "Initialized");
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            /*if(!hardwareController.isLiftBusy()){
                hardwareController.setLiftPower(0);
            }*/

            updateDriveMotors();
            updateServos();

            String motorData = String.format(
                    Locale.US,
                    "Left Front: %f%n" +
                            "Left Rear: %f%n" +
                            "Right Front: %f%n" +
                            "Right Rear: %f%n",
                    leftFrontDrive.getVelocity(), leftBackDrive.getVelocity(), rightFrontDrive.getVelocity(), rightBackDrive.getVelocity()
                    //DriveConstants.encoderTicksToInches(leftFrontDrive.getVelocity()), DriveConstants.encoderTicksToInches(leftBackDrive.getVelocity()), DriveConstants.encoderTicksToInches(rightFrontDrive.getVelocity()), DriveConstants.encoderTicksToInches(rightBackDrive.getVelocity())

            );




            // telemetry.addData("Arm Open %", "%f", armX / (ARM_UP - ARM_DOWN));
            // telemetry.addData("Intake Locked", trapDoor);
            //telemetry.addData("Bucket ", bucketX);
            telemetry.addData("Drone ", drone.getPosition());
            // telemetry.addLine("left distance: " + hardwareController.getLeftDistance());
            telemetry.addLine("right distance: " + dsensors.getRightDistance());
            telemetry.addLine(motorData);
            telemetry.addData("Lift current draw:", " %7f", lift.getLiftCurrentDraw());
            telemetry.addData("Lift position: ", "%4d", lift.getLiftPos());
            telemetry.addData("Lift target position: ", "%4d", lift.getTargetPos());
            // telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    private void toggleArm() {
        if(arm.isUp()) {
            bucket.moveToIntakePosition();
            arm.moveToDownPosition();
            arm.moveToHoverPosition();
        } else {
            bucket.moveToDropPosition();
            arm.moveToUpPosition();
        }
    }

    // Booleans that store if the buttons are pressed
    boolean rbPressed, bPressed, yPressed, xPressed = false, leftTrig;

    private void updateServos(){
        // Trapdoor toggle
        if(gamepad2.right_bumper && !yPressed) {
            yPressed = true;
            claw.toggleClaw();
            // telemetry.addLine("Toggling trapdoor");
        } else if(!gamepad2.right_bumper) {
            yPressed = false;
            //telemetry.addLine("Button Pressed but not toggling");
        }

        // Lift toggle
        if(gamepad2.left_bumper){
            // adjust the lift based on the right stick y
            double adjustment = gamepad2.right_stick_y * -50;
            if (Math.abs(adjustment) > 1)
                lift.adjustLift((int)adjustment);
        }


        // Bucket Toggle
        if(gamepad2.b && !bPressed) {
            bPressed = true;
            bucket.toggleBucket();
        } else if(!gamepad2.b) {
            bPressed = false;
        }

        if(gamepad2.a) {
            arm.moveToLowDropPosition();
            bucket.moveToLowDropPosition();
        }

        if(gamepad2.x) {
            arm.moveToAutoDropPosition();
            bucket.moveToAutoDropPos();
        }

        // Arm Toggle
        if (gamepad2.y && !rbPressed) {
            rbPressed = true; toggleArm();
        }
        else if (!gamepad2.y){
            rbPressed = false;
        }

        // Manual arm control
        arm.adjustPosition(-gamepad2.left_stick_y / 800.0f);

        // Arm location percenetage for telemetry
        // float percent = (float) ((armX - Arm.ARM_DOWN) / (Arm.ARM_UP - Arm.ARM_DOWN));

        // Manual control of the bucket
        if(gamepad2.dpad_up)
            bucket.adjustPosition(0.005);
        if(gamepad2.dpad_down)
            bucket.adjustPosition(-0.005);

        // only launch drone when a and b are pressed simultaneously on gamepad 1
        if (gamepad1.a && gamepad1.b)
            drone.launch();
    }

    private void updateDriveMotors() {
        double max, axial = 0, lateral = 0, yaw = 0;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        if (Math.abs(gamepad1.left_stick_y) > 0.1)
            axial = -gamepad1.left_stick_y;
        if (Math.abs(gamepad1.left_stick_x) > 0.1)
            lateral = gamepad1.left_stick_x;
        if (Math.abs(gamepad1.right_stick_x) > 0.1)
            yaw = gamepad1.right_stick_x;

        // Fine control using dpad and bumpers
        if (gamepad1.dpad_up)
            axial += 0.3;
        if (gamepad1.dpad_down)
            axial -= 0.3;
        if (gamepad1.dpad_left)
            lateral -= 0.3;
        if (gamepad1.dpad_right)
            lateral += 0.3;

        // Bumpers Turn Bot Left and Right
        if (gamepad1.left_bumper)
            yaw += 0.3 * -1;
        if (gamepad1.right_bumper)
            yaw -= 0.3 * -1;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower = axial - lateral + yaw;
        double rightBackPower = axial + lateral - yaw;

        leftFrontPower *= DRIVE_SPEED;
        rightFrontPower *= DRIVE_SPEED;
        leftBackPower *= DRIVE_SPEED;
        rightBackPower *= DRIVE_SPEED;

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        WheelValues wheelPowers = new WheelValues(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);

        // prevent forward movement of the motors if it gets too close to the wall
        if (gamepad1.x) {
            wheelPowers = adjustVelocityForBackdrop(wheelPowers);
        }

        // Send calculated power to wheels
        leftFrontDrive.setVelocity(wheelPowers.leftFrontValue * MAX_VELOCITY);
        rightFrontDrive.setVelocity(wheelPowers.rightFrontValue * MAX_VELOCITY);
        leftBackDrive.setVelocity(wheelPowers.leftBackValue * MAX_VELOCITY);
        rightBackDrive.setVelocity(wheelPowers.rightBackValue * MAX_VELOCITY);


        // Intake
        double intakePower = 0;

        if(gamepad2.right_trigger != 0) {
            intakePower = -1;
            arm.moveToDownPosition();
            bucket.moveToIntakePosition();
        }

        if(gamepad2.left_trigger != 0) {
            intakePower = 1;
            arm.moveToDownPosition();
            bucket.moveToIntakePosition();
        }

        intake.setPower(intakePower);

    }

    private WheelValues adjustVelocityForBackdrop(WheelValues wheelValues) {
        double leftFrontPower = wheelValues.leftFrontValue;
        double leftBackPower = wheelValues.leftBackValue;
        double rightFrontPower = wheelValues.rightFrontValue;
        double rightBackPower = wheelValues.rightBackValue;

        float far_distance = 20.0f;
        float close_distance = 8.5f;

        // Linearly scale the power for the left wheels based on the left sensor distance
        double leftDistance = dsensors.getLeftDistance();
        if (leftDistance < far_distance && leftDistance > close_distance) {
            double scaledPower = (leftDistance - close_distance) / (far_distance - close_distance); // Scale from 2 inches (0 power) to 10 inches (1 power)
            if (rightFrontPower < 0)
                rightFrontPower *= scaledPower * 0.5;
            if (rightBackPower < 0)
                rightBackPower *= scaledPower * 0.5;
        } else if (leftDistance <= close_distance) {
            if (rightFrontPower < 0)
                rightFrontPower = 0;
            if (rightBackPower < 0)
                rightBackPower = 0;
        }

        // Linearly scale the power for the right wheels based on the right sensor distance
        double rightDistance = dsensors.getRightDistance();
        if (rightDistance < far_distance && rightDistance > close_distance) {
            double scaledPower = (rightDistance - close_distance) / (far_distance - close_distance); // Scale from 2 inches (0 power) to 10 inches (1 power)
            if (leftFrontPower < 0)
                leftFrontPower *= scaledPower * 0.5;
            if (leftBackPower < 0)
                leftBackPower *= scaledPower * 0.5;
        } else if (rightDistance <= close_distance) {
            if (leftFrontPower < 0)
                leftFrontPower = 0;
            if (leftBackPower < 0)
                leftBackPower = 0;
        }

        return new WheelValues(leftFrontPower, leftBackPower, rightFrontPower, rightBackPower);
    }

    private class WheelValues {
        public double leftFrontValue;
        public double rightFrontValue;
        public double leftBackValue;
        public double rightBackValue;

        public WheelValues(double leftFrontValue, double leftBackValue, double rightFrontValue, double rightBackValue) {
            this.leftFrontValue = leftFrontValue;
            this.leftBackValue = leftBackValue;
            this.rightFrontValue = rightFrontValue;
            this.rightBackValue = rightBackValue;
        }
    }
}