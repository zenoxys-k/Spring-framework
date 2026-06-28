package mg.itu.kiady.dto;

import java.lang.reflect.Method;

public class ControllerMethodUrlDTO {
    private Class<?> controllerClass;
    private java.lang.reflect.Method method;

    public ControllerMethodUrlDTO(Class<?> controllerClass, java.lang.reflect.Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "controller: " + controllerClass.getName() + " -> " + method.getName() + "()";
    }
}
