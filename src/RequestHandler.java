package src;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse rep)
        throws IOException {
            handleRequest(req,rep);
    }

    @Override
    protected void doPost(HttpServletRequest req,HttpServletResponse rep)
        throws IOException {
            handleRequest(req,rep);
    }

     private void handleRequest(HttpServletRequest req, HttpServletResponse rep) // ✅ void
            throws IOException {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        rep.setContentType("text/html;charset=UTF-8");
        PrintWriter out = rep.getWriter();
        out.println("URL reçue : " + path);
    }
}