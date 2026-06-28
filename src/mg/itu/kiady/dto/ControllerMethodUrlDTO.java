package src.mg.itu.kiady.dto;

import java.lang.reflect.Method;

public class ControllerMethodUrlDTO {
    private Class<?> controllerClass;
    private String method;

    public ControllerMethodUrlDTO(Class<?> controllerClass, String method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public String getMethod() {
        return method;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "controller: " + controllerClass.getName() + " -> " + method.getName() + "()";
    }
}
