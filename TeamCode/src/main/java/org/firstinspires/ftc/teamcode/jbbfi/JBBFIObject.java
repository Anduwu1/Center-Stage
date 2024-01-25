package org.firstinspires.ftc.teamcode.jbbfi;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
public class JBBFIObject<T> {
    private Object object;

    private String name;

    public JBBFIObject(String objectName, String frontEndName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        java.lang.Class<T> claz = (Class<T>) Class.forName(objectName);

        object = claz.newInstance();

        this.name = frontEndName;
    }

    public void executeFunction(String name, JBBFIArg... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Class<?>> argTypes = new ArrayList<>();
        ArrayList<Object> argsObj = new ArrayList<>();
        for (JBBFIArg arg:
                args) {
            argTypes.add(
                    arg.getArg().getClass()
            );
            argsObj.add(arg.getArg());
        }
        Class<?>[] parameterTypes = argTypes.toArray(new Class<?>[0]);
        Method function = object.getClass().getMethod(name, parameterTypes);
        function.invoke(object, argsObj.toArray());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
