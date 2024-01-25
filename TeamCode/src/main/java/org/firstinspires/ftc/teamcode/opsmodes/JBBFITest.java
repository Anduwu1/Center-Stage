package org.firstinspires.ftc.teamcode.opsmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.jbbfi.JBBFI;
import org.firstinspires.ftc.teamcode.jbbfi.ScriptingWebPortal;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIClassNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIInvalidFunctionException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIScriptNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIUnknownKeywordException;

import java.io.FileNotFoundException;


public class JBBFITest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        ScriptingWebPortal scriptingWebPortal = new ScriptingWebPortal(hardwareMap.appContext);
        scriptingWebPortal.start();

        try {
            JBBFI jbbfi = new JBBFI("/sdcard/test.jbbfi");



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
    }
}
