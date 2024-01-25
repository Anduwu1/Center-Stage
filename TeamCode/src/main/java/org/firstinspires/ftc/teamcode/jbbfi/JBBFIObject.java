package org.firstinspires.ftc.teamcode.jbbfi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
public class JBBFIObject {
    private Object object;

    private String name;

    public JBBFIObject(String objectName, String frontEndName) throws ClassNotFoundException {
        object = Class.forName(objectName);
        this.name = frontEndName;
    }

    public void executeFunction(String name, JBBFIArg... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Object> argTypes = new ArrayList<>();
        for (JBBFIArg arg:
             args) {
            argTypes.add(
                    arg.getArg()
            );
        }
        Method function = object.getClass().getMethod(name, argTypes.toArray().getClass());
        function.invoke(argTypes.toArray());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
