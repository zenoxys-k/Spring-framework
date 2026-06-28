package mg.itu.kiady.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mg.itu.kiady.annotation.Controller;
import mg.itu.kiady.annotation.UrlMapping;
import mg.itu.kiady.dto.ControllerMethodUrlDTO;

public class Util {

    public List<String> splitPackage(String packageName) {
        return List.of(packageName.split(";"));
    }

public void loadClasses(File directory, String packageName, List<Class<?>> listClass) throws Exception {
        if (!directory.exists()) return;

        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                loadClasses(file, packageName + "." + file.getName(), listClass);
            }
            else if (file.getName().endsWith(".class")){
                String className = packageName + "." + file.getName().replace(".class", "");
                listClass.add(Class.forName(className));
            }
        }
    }

    public void loadClassesIn(String packageName, List<Class<?>> listClasses) throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        File directory = new File(loader.getResource(path).getFile());
        loadClasses(directory, packageName, listClasses);
    }

    // Version 1 : toutes les classes sans filtre
    public List<Class<?>> getClasses(String packagesName) {
        List<Class<?>> listClasses = new ArrayList<>();
        try {
            for (String elem : splitPackage(packagesName)) {
                loadClassesIn(elem, listClasses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listClasses;
    }

    // Version 2 : seulement les classes avec une annotation précise
    public List<Class<?>> getClasses(String packagesName, Class<?> annotationClass) {
        List<Class<?>> listClasses = getClasses(packagesName);
        List<Class<?>> listAnnotatedClasses = new ArrayList<>();
        for (Class<?> elem : listClasses) {
            if (elem.isAnnotationPresent((Class<java.lang.annotation.Annotation>) annotationClass)) {
                listAnnotatedClasses.add(elem);
            }
        }
        return listAnnotatedClasses;
    }

    // Version 3 : classes @Controller + remplit la Map des URLs
    public List<Class<?>> getClasses(String packagesName, Map<String, ControllerMethodUrlDTO> controllerMethods) {
        List<Class<?>> listClasses = getClasses(packagesName);
        List<Class<?>> listAnnotatedClasses = new ArrayList<>();

        for (Class<?> elem : listClasses) {
            if (elem.isAnnotationPresent(Controller.class)) {
                listAnnotatedClasses.add(elem);

                // Pour chaque méthode de la classe
                for (java.lang.reflect.Method method : elem.getMethods()) {
                    if (method.isAnnotationPresent(UrlMapping.class)) {
                        String url = method.getAnnotation(UrlMapping.class).url();
                        controllerMethods.put(url, new ControllerMethodUrlDTO(elem, method));
                    }
                }
            }
        }
        return listAnnotatedClasses;
    }
}

