package org.firstinspires.ftc.teamcode.opsmodes;

import static org.firstinspires.ftc.teamcode.subsystems.Arm.ARM_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.Arm.ARM_UP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.objects.Robot;
import org.firstinspires.ftc.teamcode.objects.RobotSettings;
import org.firstinspires.ftc.teamcode.resources.HardwareController;
import org.firstinspires.ftc.teamcode.roadrunner.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Bucket;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@TeleOp(name="Main OpMode")
public class MainOpsMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // Motors
    private DcMotorEx leftFrontDrive = null;
    private DcMotorEx leftBackDrive = null;
    private DcMotorEx rightFrontDrive = null;
    private DcMotorEx rightBackDrive = null;
    private DcMotor intakeDrive = null;
    private enum ArmState {
        UP,
        DOWN
    }

    public enum IntakeState {
        IN,
        OUT
    }

    public enum BucketState {
        IN,
        DROP
    }

    private boolean trapdoorOpened = false;
    private boolean bay1 = false;
    private boolean bay2 = false;
    private ArmState armState = ArmState.DOWN;


    // Constants
    private static final double DRIVE_SPEED = 1;
    private static final int MAX_VELOCITY = 2200;
    // private static final double INTAKE_SPEED = 0.1;
    // Servo Constants

    private HardwareController hardwareController;
    private Robot robot;

    // Intake
    private double intakePower = 0;
    private int inState = 1;
    private IntakeState intakeState = IntakeState.IN;

    // Bucket
    float bucketX = Bucket.INTAKE_POS;
    private BucketState bState = BucketState.IN;

    // TDoor
    float tX = 1f;

    public void toggleTrapdoor(BucketState bs) {
        if(bs == BucketState.IN) {
            tX = 1f;
        } else {
            tX = 0.7f;
        }
    }

    public void setTrapdoorOpened() {
        tX = 1f;
    }

    public void toggleTrapdoor() {
        if(tX == .7f) {
            bState = BucketState.DROP;
            tX = 1f;
        } else {
            bState = BucketState.IN;
            tX = .7f;
        }
    }
    public void toggleBucket(BucketState bs) {
        if (bs == BucketState.IN) {
            bucketX = Bucket.INTAKE_POS;
            // toggleTrapdoor(bs);
        }
        else {
            bucketX = Bucket.DROP_POS;
            // toggleTrapdoor(bs);
        }
    }

    public void toggleBucket() {
        if (bucketX == Bucket.DROP_POS) {
            bucketX = Bucket.INTAKE_POS;
        } else {
            bucketX = Bucket.DROP_POS;
        }
    }

    public void armToTop() {
        toggleBucket(BucketState.DROP);
        armX = ARM_UP;
    }
    public void toggleArm() {
        if(armX == ARM_UP) {
            toggleBucket(BucketState.IN);
            armX = ARM_DOWN;
        } else {
            toggleBucket(BucketState.DROP);
            armX = ARM_UP;
        }
    }

    public boolean alignBot() {
        // TODO: make this use the Hardware Controller to rotate the bot using the distance sensors then move the arm into place
        boolean aligned = hardwareController.align();
        armToTop();

        // If you still want trapdoor release to be manual remove this next line
        setTrapdoorOpened();

        return aligned;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        // robot = new Robot(AutonomousOpsMode.StartPos.BACKSTAGE, hardwareMap);

        hardwareController = new HardwareController(hardwareMap);

        // Init hardware Vars
        leftFrontDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LFDRIVE_MOTOR);
        leftBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_LBDRIVE_MOTOR);
        rightFrontDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RFDRIVE_MOTOR);
        rightBackDrive = hardwareMap.get(DcMotorEx.class, RobotSettings.BANA_RBDRIVE_MOTOR);
        List<DcMotorEx> motors = Arrays.asList(leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive);

        //set mode
        for (DcMotorEx motor : motors) {
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        intakeDrive = hardwareMap.get(DcMotor.class, "intake");

        // Set directions
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        intakeDrive.setDirection(DcMotorSimple.Direction.FORWARD);

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
            telemetry.addData("Bucket ", bucketX);
            // telemetry.addLine("left distance: " + hardwareController.getLeftDistance());
            telemetry.addLine("right distance: " + hardwareController.getRightDistance());
            telemetry.addLine(motorData);
            // telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    // Booleans that store if the buttons are pressed
    boolean rbPressed, bPressed, yPressed, xPressed = false, leftTrig;

    boolean open = true, trapDoor = false;
    private float armX = ARM_DOWN;
    private void updateServos(){
        // Trapdoor toggle
        if(gamepad2.y && !yPressed) {
            yPressed = true;
            toggleTrapdoor();
            // telemetry.addLine("Toggling trapdoor");
        } else if(!gamepad2.y) {
            yPressed = false;
            //telemetry.addLine("Button Pressed but not toggling");
        }

        // Lift toggle
        if(gamepad2.left_bumper){
            if(!leftTrig) {
                leftTrig = true;
                toggleLift();
            }
        }else{
            leftTrig = false;
        }


        // Bucket Toggle
        if(gamepad2.b && !bPressed) {
            bPressed = true;
            toggleBucket();
        } else if(!gamepad2.b) {
            bPressed = false;
        }

        // Arm Toggle
        if (gamepad2.right_bumper && !rbPressed) {
            rbPressed = true; toggleArm();
        }
        else if (!gamepad2.right_bumper){
            rbPressed = false;
        }

        // Manual arm control
        armX -= gamepad2.left_stick_y / 800.0f;

        // Arm clamp
        if(armX > ARM_UP)
            armX = ARM_UP;
        if(armX < ARM_DOWN)
            armX = ARM_DOWN;

        // Arm location percenetage for telemetry
        // float percent = (float) ((armX - Arm.ARM_DOWN) / (Arm.ARM_UP - Arm.ARM_DOWN));

        // Manual control of the bucket
        if(gamepad2.dpad_up) bucketX+=0.005f;
        if(gamepad2.dpad_down) bucketX-=0.005f;

        // Clamps the bucket servo position
        if(bucketX > Bucket.DROP_POS)
            bucketX = Bucket.DROP_POS;
        if(bucketX < Bucket.INTAKE_POS)
            bucketX = Bucket.INTAKE_POS;

        // Updates servos
        hardwareController.servoMove(bucketX, HardwareController.Servo_Type.BUCKET_SERVO);
        hardwareController.servoMove(tX, HardwareController.Servo_Type.DOOR_SERVO);
        hardwareController.servoMove(armX, HardwareController.Servo_Type.ARM_SERVO);

    }

    private void updateDriveMotors() {
        // Max motor speed
        double max;

        double axial = 0;
        double lateral = 0;
        double yaw = 0;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        if (Math.abs(gamepad1.left_stick_y) > 0.05)
            axial = -gamepad1.left_stick_y;
        if (Math.abs(gamepad1.left_stick_x) > 0.05)
            lateral = gamepad1.left_stick_x;
        if (Math.abs(gamepad1.right_stick_x) > 0.05)
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
        intakePower = 0;

        if(gamepad2.right_trigger != 0)
            intakePower = -1;

        if(gamepad2.left_trigger != 0)
            intakePower = 1;

        if(!trapDoor)
            intakeDrive.setPower(intakePower);

    }

    boolean up = false;
    private void toggleLift(){
        if(up){
            hardwareController.setLiftPower(-1f);
            hardwareController.liftGoToPosition(0);
            up = false;
        }else{
            hardwareController.setLiftPower(1);
            hardwareController.liftGoToPosition(1550);
            up = true;
        }
        sleep(250);
    }

    private WheelValues adjustVelocityForBackdrop(WheelValues wheelValues) {
        double leftFrontPower = wheelValues.leftFrontValue;
        double leftBackPower = wheelValues.leftBackValue;
        double rightFrontPower = wheelValues.rightFrontValue;
        double rightBackPower = wheelValues.rightBackValue;

        float far_distance = 14.0f;
        float close_distance = 2.0f;

        // Linearly scale the power for the left wheels based on the left sensor distance
        double leftDistance = hardwareController.getLeftDistance();
        if (leftDistance < far_distance && leftDistance > close_distance) {
            double scaledPower = (leftDistance - close_distance) / (far_distance - close_distance); // Scale from 2 inches (0 power) to 10 inches (1 power)
            if (rightFrontPower < 0)
                rightFrontPower *= scaledPower;
            if (rightBackPower < 0)
                rightBackPower *= scaledPower;
        } else if (leftDistance <= close_distance) {
            if (rightFrontPower < 0)
                rightFrontPower = 0;
            if (rightBackPower < 0)
                rightBackPower = 0;
        }

        // Linearly scale the power for the right wheels based on the right sensor distance
        double rightDistance = hardwareController.getRightDistance();
        if (rightDistance < far_distance && rightDistance > 2.0) {
            double scaledPower = (rightDistance - close_distance) / (far_distance - close_distance); // Scale from 2 inches (0 power) to 10 inches (1 power)
            if (leftFrontPower < 0)
                leftFrontPower *= scaledPower;
            if (leftBackPower < 0)
                leftBackPower *= scaledPower;
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