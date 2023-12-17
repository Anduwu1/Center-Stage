package org.firstinspires.ftc.teamcode.opsmodes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp(name="LiftCalib")
public class LiftCalibration extends LinearOpMode {

    private Lift lift;

    float position = 0.0f;

    boolean goTo = false;

    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            if(!lift.isLiftBusy()){
                lift.setLiftPower(0);
                goTo = false;
            }
            if(!goTo) {
                position -= gamepad1.left_stick_y * 10.0f;

                // make this better if you want
                // (awesome ap csa question)
                /*if (position > 1550.0f) {
                    position = 1550.0f;
                }
                if (position < -0.01) {
                    position = 0.0f;
                }*/
            }
            if(gamepad1.a && !goTo) goTo = true;

            if(goTo && !lift.isLiftBusy()) {
                lift.setLiftPower(1);
                lift.liftGoToPosition((int) position);
            }

            sleep(250);

            telemetry.addData("Running to",  " %7f", (float) position);
            telemetry.addData("Currently at",  " at %7f", (float) lift.getLiftPos());
            telemetry.addData("Currently Moving?", goTo);
            telemetry.update();
        }
    }
}
