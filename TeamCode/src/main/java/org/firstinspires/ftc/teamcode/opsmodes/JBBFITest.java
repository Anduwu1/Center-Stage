package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.jbbfi.JBBFI;
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
    @Override
    public void runOpMode() throws InterruptedException {
        ScriptingWebPortal scriptingWebPortal = new ScriptingWebPortal(hardwareMap.appContext);
        scriptingWebPortal.start();

        // Create drivehelper
        SampleMecanumDrive sampleMecanumDrive = new SampleMecanumDrive(hardwareMap);
        RoadRunnerHelper roadRunnerHelper = new RoadRunnerHelper(sampleMecanumDrive);

        try {
            jbbfi = new JBBFI("/sdcard/test.jbbfi", hardwareMap);
            jbbfi.addGlobal(roadRunnerHelper, "driveHelper");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        waitForStart();

        try{
            jbbfi.runFunction("driveTest");
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        while(opModeIsActive() && !isStopRequested()){

        }

    }
}
