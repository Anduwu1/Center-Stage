package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Main OpMode")
public class MainOpsMode extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    // Motors
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;

    private DcMotor intakeDrive = null;
    private enum ArmState {
        UP,
        DOWN
    }

    private boolean trapdoorOpened = false;
    private boolean bay1 = false;
    private boolean bay2 = false;
    private ArmState armState = ArmState.DOWN;


    // Constants
    private static final double DRIVE_SPEED = 0.6;
    private static final double INTAKE_SPEED = 0.3;
    // Servo Constants
    // TODO: Get real values for these
    private static final double TRAPDOOR_OPEN = 0;
    private static final double TRAPDOOR_CLOSED = 0;
    private static final double ARM_UP = 0;
    private static final double ARM_DOWN = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        // Init hardware Vars
        leftFrontDrive = hardwareMap.get(DcMotor.class, "frontL");
        leftBackDrive = hardwareMap.get(DcMotor.class, "backL");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "frontR");
        rightBackDrive = hardwareMap.get(DcMotor.class, "backR");

        intakeDrive = hardwareMap.get(DcMotor.class, "intake");

        // Set directions
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        intakeDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            updateDriveMotors();

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }

    private void updateDriveMotors() {
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;

        //fine control using dpad and bumpers
        if (gamepad1.dpad_up)
            axial += 0.3;
        if (gamepad1.dpad_down)
            axial -= 0.3;
        if (gamepad1.dpad_left)
            lateral -= 0.3;
        if (gamepad1.dpad_right)
            lateral += 0.3;
        if (gamepad1.left_bumper)
            yaw -= 0.3 * -1;
        if (gamepad1.right_bumper)
            yaw += 0.3 * -1;

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

        // Intake
        double intakePower = 0;
        if(gamepad1.right_trigger != 0) {
            intakePower = Math.min(Math.max(gamepad1.right_trigger * INTAKE_SPEED, 1.0), -1.0);
        }

        if(gamepad1.left_trigger != 0) {
            intakePower = Math.min(Math.max(gamepad1.left_trigger * -INTAKE_SPEED, 1.0), -1.0);
        }

        intakeDrive.setPower(intakePower);

    }
}
