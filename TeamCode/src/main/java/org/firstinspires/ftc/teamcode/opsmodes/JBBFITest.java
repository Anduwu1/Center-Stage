package org.firstinspires.ftc.teamcode.opsmodes;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.jbbfi.JBBFI;
import org.firstinspires.ftc.teamcode.jbbfi.ScriptWebPortal;
import org.firstinspires.ftc.teamcode.jbbfi.ScriptingWebPortal;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIClassNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIInvalidFunctionException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIScriptNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIUnknownKeywordException;
import org.firstinspires.ftc.teamcode.resources.RoadRunnerHelper;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

@Autonomous(group="drive")
public class JBBFITest extends LinearOpMode {
    JBBFI jbbfi;
    String error;
    @Override
    public void runOpMode() throws InterruptedException {
        Thread.UncaughtExceptionHandler caught = new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                error = e.toString();
            }

        };
        error = "NONE";

        ScriptingWebPortal scriptingWebPortal = new ScriptingWebPortal(hardwareMap.appContext);
        scriptingWebPortal.setUncaughtExceptionHandler(caught);
        scriptingWebPortal.start();


        //ScriptWebPortal scriptWebPortal = new ScriptWebPortal();


        // Create drivehelper
        SampleMecanumDrive sampleMecanumDrive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerHelper roadRunnerHelper = new RoadRunnerHelper(sampleMecanumDrive);

        try {
            jbbfi = new JBBFI("/sdcard/test/test.jbbfi", hardwareMap);
            jbbfi.addGlobal(roadRunnerHelper, "driveHelper");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        while(!isStarted() && !isStopRequested()) {
            if(scriptingWebPortal.isAlive()){
                telemetry.addLine("ScriptPortal is alive\n");
            }else{
                telemetry.addLine("ScriptPortal is dead\n");
            }
            telemetry.addData("State", scriptingWebPortal.getState().toString());
            telemetry.addData("Error", error);
            telemetry.update();
        }

        /*try{
            jbbfi.runFunction("driveTest");
        } catch (Exception e){
            throw new RuntimeException(e);
        }*/

        while(opModeIsActive() && !isStopRequested()){

        }

        scriptingWebPortal.stopRunning();

    }
}
