package org.firstinspires.ftc.teamcode.jbbfi;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIClassNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIInvalidFunctionException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIScriptNotFoundException;
import org.firstinspires.ftc.teamcode.jbbfi.exceptions.JBBFIUnknownKeywordException;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 Manages scripting
 */
public class JBBFI {
    ArrayList<JBBFIObject> objects = new ArrayList<>();
    ArrayList<JBBFIFunction> functions = new ArrayList<>();

    ArrayList<String> fileData = new ArrayList<>();

    public static HardwareMap hardwareMap;

    int currentLine = 0;

    public JBBFI(String fileName) throws JBBFIScriptNotFoundException, JBBFIClassNotFoundException, FileNotFoundException, JBBFIInvalidFunctionException, JBBFIUnknownKeywordException {
        try {
            readFile(fileName);
        } catch (FileNotFoundException e){
            throw new JBBFIScriptNotFoundException(fileName);
        }

        for(currentLine = 0; currentLine < fileData.size(); currentLine++){
            parseLine(fileData.get(currentLine));
        }
    }

    /**
     * Create a JBBFI Interpreter that supports classes that need HardwareMap
     * @param fileName
     * @param hardwareMap
     * @throws JBBFIScriptNotFoundException
     * @throws JBBFIClassNotFoundException
     * @throws FileNotFoundException
     * @throws JBBFIInvalidFunctionException
     * @throws JBBFIUnknownKeywordException
     */
    public JBBFI(String fileName, HardwareMap hardwareMap) throws JBBFIScriptNotFoundException, JBBFIClassNotFoundException, FileNotFoundException, JBBFIInvalidFunctionException, JBBFIUnknownKeywordException {
        this.hardwareMap = hardwareMap;
        try {
            readFile(fileName);
        } catch (FileNotFoundException e){
            throw new JBBFIScriptNotFoundException(fileName);
        }

        for(currentLine = 0; currentLine < fileData.size(); currentLine++){
            parseLine(fileData.get(currentLine));
        }
    }

    public void addGlobal(Object global, String name){
        objects.add(
                new JBBFIObject(
                        global,
                        name
                )
        );
    }



    private void readFile(String fileName) throws FileNotFoundException, JBBFIClassNotFoundException, JBBFIUnknownKeywordException, JBBFIInvalidFunctionException {
        File scriptFile = new File(fileName);
        Scanner fileScan = new Scanner(scriptFile);

        while(fileScan.hasNextLine()){

            fileData.add(fileScan.nextLine());

        }
    }

    private void parseLine(String line) throws JBBFIClassNotFoundException, JBBFIUnknownKeywordException, JBBFIInvalidFunctionException {
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
                }catch (Exception e){
                    throw new JBBFIClassNotFoundException(tokens[1]);
                }
                break;
            // Oh. Its on.
            case "function":
                // TODO: IF THE NEXT LINE FAILS, CRASH WITH FUNCTIONWITHOUTNAMEEXCPETION
                JBBFIFunction function = new JBBFIFunction(tokens[1]);
                int offSet = currentLine + 1;
                while(offSet < fileData.size() - 1){
                    String l = fileData.get(offSet);
                    if(l.contains("function_end")) break;
                    function.addLine(l);
                    offSet++;
                }
                functions.add(function);
                currentLine += (offSet - currentLine);
                break;
            default:
                // is it empty?
                if(tokens[0].isEmpty()) return;

                // Is it a comment
                if(tokens[0].startsWith("//")) return;

                // Maybe a function? lets see
                if(runFunction(tokens[0]) == 1) return;

                // Maybe the name of an object? lets try
                // Split at "." since that is how functions are called
                String[] possible = line.split("\\.");
                boolean found = false;
                for (JBBFIObject obj:
                        objects) {
                    if(obj.getName().equals(possible[0])){
                        found = true;
                        // Start doing stuff
                        for (int i = 1; i < possible.length; i++) {
                            String func = possible[i];
                            // Name of the function
                            String functionName = func.split("\\(")[0];
                            // Get the arguments in the "( )"
                            String argsSTR = func.substring(func.indexOf("(")+1, func.indexOf(")"));
                            // Isolate args
                            String[] argList = argsSTR.split("\\,");
                            // Args
                            ArrayList<JBBFIArg> args = new ArrayList<>();
                            for(String argStr : argList){
                                // Convert to a JBBFIArg

                                // Is it a primitive
                                // Int
                                try {
                                    Integer intTmp = Integer.parseInt(argStr);
                                    args.add(
                                            new JBBFIArg(intTmp)
                                    );
                                    continue;
                                }catch (NumberFormatException n){
                                    // Continue
                                }
                                // Float
                                try {
                                    Float floatTmp = Float.parseFloat(argStr);
                                    args.add(
                                            new JBBFIArg(floatTmp)
                                    );
                                    continue;
                                }catch (NumberFormatException n){
                                    // Continue
                                }
                                // Double
                                try {
                                    Double doubleTmp = Double.parseDouble(argStr);
                                    args.add(
                                            new JBBFIArg(doubleTmp)
                                    );
                                    continue;
                                }catch (NumberFormatException n){
                                    // Continue
                                }


                                // Is it a type of class we know?
                                boolean fClass = false;
                                for (JBBFIObject objKnown:
                                        objects) {
                                    if(objKnown.getName().equals(argStr)){
                                        args.add(
                                                new JBBFIArg(objKnown.getObject())
                                        );
                                        fClass = true;
                                        break;
                                    }
                                }
                                if(fClass) continue;

                                // Ok just assume its a string omg
                                args.add(new JBBFIArg(argStr));
                            }

                            try {
                                obj.executeFunction(functionName, args.toArray(new JBBFIArg[0]));
                            } catch (Exception e){
                                throw new JBBFIInvalidFunctionException(obj.getName() + "::" + functionName);
                            }
                        }


                    }
                }
                if(!found){
                    throw new JBBFIUnknownKeywordException(tokens[0]);
                }
                break;
        }
    }

    public int runFunction(String funcName) throws JBBFIClassNotFoundException, JBBFIInvalidFunctionException, JBBFIUnknownKeywordException {
        for (JBBFIFunction func:
                functions) {
            if(funcName.split("\\(")[0].contains(func.getFunctionName())) {
                for(String s : func.getCommands()){
                    parseLine(s);
                }
                return 1;
            }
        }
        return 0;
    }

}