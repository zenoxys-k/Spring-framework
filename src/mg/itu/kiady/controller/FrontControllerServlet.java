package mg.itu.kiady.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.itu.kiady.util.Util;
import mg.itu.kiady.dto.ControllerMethodUrlDTO;
import mg.itu.kiady.exception.NoMethodUrlException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontControllerServlet extends HttpServlet {

    List<Class<?>> listClasses;
    Map<String, ControllerMethodUrlDTO> controllerMethods;

    @Override
    public void init() {
        Util util = new Util();
        controllerMethods = new HashMap<>();
        listClasses = util.getClasses(getInitParameter("packages"), controllerMethods);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processHandler(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        processHandler(req, res);
    }

    // Appelé à CHAQUE requête
    public void processHandler(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/plain");
        PrintWriter out = res.getWriter();

        out.println("URL reçue : " + req.getRequestURI());
        out.println();

        out.println("Classes @Controller trouvées :");
        for (Class<?> elem : listClasses) {
            out.println("  - " + elem.getName());
        }
        out.println();

        try {
            String path = getPathAfterBaseURL(req);
            ControllerMethodUrlDTO dto = getControllerMethodUrlDTOByUrl(path);
            out.println("Méthode trouvée → " + dto);
        } catch (NoMethodUrlException e) {
            out.println(e.getMessage());
            out.println();
            out.println("Routes disponibles :");
            for (String url : controllerMethods.keySet()) {
                out.println("  " + url + " → " + controllerMethods.get(url));
            }
        }
    }

    public String getPathAfterBaseURL(HttpServletRequest req) {
        return req.getRequestURI().substring(req.getContextPath().length());
    }

    public ControllerMethodUrlDTO getControllerMethodUrlDTOByUrl(String url) {
        ControllerMethodUrlDTO ret = controllerMethods.get(url);
        if (ret != null) {
            return ret;
        }
        throw new NoMethodUrlException("Aucune route pour : " + url);
    }

    public List<Class<?>> getListClasses() {
        return listClasses;
    }

    public void setListClasses(List<Class<?>> listClasses) {
        this.listClasses = listClasses;
    }
}