package org.firstinspires.ftc.teamcode.jbbfi;

import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIClassNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIScriptNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 Manages scripting
 */
public class JBBFI {
    ArrayList<JBBFIObject> objects;

    public JBBFI(String fileName){
        objects = new ArrayList<>();
        try{
            readFile(fileName);
        } catch (Exception e){
            throw new JBBFIScriptNotFoundException(fileName);
        }

    }

    private void readFile(String fileName) throws FileNotFoundException {
        File scriptFile = new File(fileName);
        Scanner fileScan = new Scanner(scriptFile);

        while(fileScan.hasNextLine()){
            parseLine(fileScan.nextLine());
        }
    }

    private void parseLine(String line){
        String[] tokens = line.split("\\s+"); // we're looking for spaces

        switch(tokens[0]){
            /*
                Example:
                    init Arm arm
             */
            case "init":
                // Jesse, we need to initialize an object
                try{
                    objects.add(
                      new JBBFIObject(tokens[1], tokens[2])
                    );
                }catch (ClassNotFoundException c){
                    throw new JBBFIClassNotFoundException(tokens[1]);
                }
                break;

            default:
                // Maybe the name of an object? lets try
                break;
        }
    }

}
