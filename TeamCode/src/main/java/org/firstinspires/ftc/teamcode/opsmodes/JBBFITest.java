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
        } catch (JBBFIScriptNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JBBFIClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JBBFIInvalidFunctionException e) {
            throw new RuntimeException(e);
        } catch (JBBFIUnknownKeywordException e) {
            throw new RuntimeException(e);
        }

        waitForStart();

        try{
            jbbfi.runFunction("driveTest");
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
