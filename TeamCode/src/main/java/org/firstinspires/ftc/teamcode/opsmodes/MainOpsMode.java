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
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
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

            updateDriveMotorsSam();
            updateServos();

            String motorData = String.format(
                    Locale.US,
                    "Left Front: %f%n" +
                            "Left Rear: %f%n" +
                            "Right Front: %f%n" +
                            "Right Rear: %f%n",
                    DriveConstants.encoderTicksToInches(leftFrontDrive.getVelocity()), DriveConstants.encoderTicksToInches(leftBackDrive.getVelocity()), DriveConstants.encoderTicksToInches(rightFrontDrive.getVelocity()), DriveConstants.encoderTicksToInches(rightBackDrive.getVelocity())

            );


            // telemetry.addData("Arm Open %", "%f", armX / (ARM_UP - ARM_DOWN));
            // telemetry.addData("Intake Locked", trapDoor);
            // telemetry.addData("Bucket %", "%f", bucketX - 0.19f / (1.0f - 0.19f));
            telemetry.addLine("left distance: " + hardwareController.getLeftDistance());
            telemetry.addLine("right distance: " + hardwareController.getRightDistance());
            telemetry.addLine(motorData);
            // telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    // Booleans that store if the buttons are pressed
    boolean rbPressed, bPressed, yPressed, xPressed = false;

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
        double min;

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

        // axial and yaw lock
        boolean aylock = false;

        // whether or not a is pressed
        boolean aPressed = false;

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

//        // Auto alignment
//        if (gamepad1.a && !aPressed && aylock) {
//            aylock = false;
//            aPressed = true;
//        } else if (gamepad1.a && !aPressed && !aylock) {
//            alignBot();
//            aPressed = true;
//        } else if (!gamepad1.a) {
//            aPressed = false;
//        } else if (aPressed && !aylock) {
//            if (alignBot())
//                aylock = true;
//        }

        // quick and dirty distance sensor code
        if (gamepad1.a) {
            if (Math.abs(hardwareController.getLeftDistance() - hardwareController.getRightDistance()) > 0.05) {
                yaw = -(hardwareController.getLeftDistance() - hardwareController.getRightDistance()) / (hardwareController.getLeftDistance() * 7);
                aylock = false;
                if (hardwareController.getLeftDistance() >= 2 || hardwareController.getRightDistance() >= 2) {
                    axial = -1 * ((hardwareController.getLeftDistance() + hardwareController.getRightDistance()) / 2) / 42;
                    if (axial < -1)
                        axial = -1;
                    aylock = false;
                }
            }
        }

        // can turn on a mode to protect from banging into the backdrop
        if(gamepad2.x && !xPressed) {
            xPressed = true;
            aylock = !aylock;
        } else if (!gamepad2.x) {
            xPressed = false;
        }

        // locks the movement of anything but lateral if robot is aligned with backdrop
        if (aylock) {
            axial = -1 * Math.abs(axial);
            yaw = 0;
        }

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower = axial - lateral + yaw;
        double rightBackPower = axial + lateral - yaw;

        leftFrontPower *= -DRIVE_SPEED;
        rightFrontPower *= DRIVE_SPEED;
        leftBackPower *= DRIVE_SPEED;
        rightBackPower *= DRIVE_SPEED;

        double leftFrontEx = DriveConstants.encoderTicksToInches(leftFrontDrive.getVelocity());
        double rightFrontEx = DriveConstants.encoderTicksToInches(rightFrontDrive.getVelocity());
        double leftBackEx = DriveConstants.encoderTicksToInches(leftBackDrive.getVelocity());
        double rightBackEx = DriveConstants.encoderTicksToInches(rightBackDrive.getVelocity());

        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        min = Math.min(leftFrontEx * leftFrontPower, rightFrontEx * rightFrontPower);
        min = Math.min(min, rightBackEx * rightBackPower);
        min = Math.min(min, leftBackEx * leftBackPower);

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        double lro = leftBackEx / min;
        double lfo = leftFrontEx / min;
        double rro = rightBackEx / min;
        double rfo = rightFrontEx / min;

        if (lro == 0)
            lro = 1;
        if (rro == 0)
            rro = 1;
        if (lfo == 0)
            lfo = 1;
        if (rfo == 0)
            rfo = 1;

        // Send calculated power to wheels
        // leftFrontDrive.setPower(leftFrontPower * .98);
        leftFrontDrive.setPower(leftFrontPower /*/ lfo*/  * .96);
        rightFrontDrive.setPower(rightFrontPower /*/ rfo*/);
        leftBackDrive.setPower(leftBackPower /*/ lro*/);
        rightBackDrive.setPower(rightBackPower /*/ rro*/ * .85);

        // rightBackDrive.setPower(rightBackPower * .82);

        // Intake
        intakePower = 0;

        if(gamepad1.right_trigger != 0)
            intakePower = -1;

        if(gamepad1.left_trigger != 0)
            intakePower = 1;

        if(!trapDoor)
            intakeDrive.setPower(intakePower);

    }

    private void updateDriveMotorsSam() {
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

        leftFrontPower *= -DRIVE_SPEED;
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

        // Send calculated power to wheels
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        // rightBackDrive.setPower(rightBackPower * .82);

        // Intake
        intakePower = 0;

        if(gamepad1.right_trigger != 0)
            intakePower = -1;

        if(gamepad1.left_trigger != 0)
            intakePower = 1;

        if(!trapDoor)
            intakeDrive.setPower(intakePower);

    }
}