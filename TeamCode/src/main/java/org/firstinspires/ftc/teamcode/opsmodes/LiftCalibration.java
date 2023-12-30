package org.firstinspires.ftc.teamcode.opsmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp(name="LiftCalib")
public class LiftCalibration extends LinearOpMode {

    private Lift lift;

    int position = 0;

    boolean goTo = false;

    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            int adjustment = (int) (gamepad1.left_stick_y * -10.0f);
            position += adjustment;
            lift.adjustLift(adjustment);


            sleep(100);

            telemetry.addData("Running to",  " %7f", (float) position);
            telemetry.addData("Currently at",  " at %7f", (float) lift.getLiftPos());
            telemetry.addData("Currently Moving?", goTo);
            telemetry.update();
        }
    }
}
