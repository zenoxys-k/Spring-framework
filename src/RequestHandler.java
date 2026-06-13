package src;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RequestHandler extends HttpServlet {
    @Override
    protected doGet(HttpServletRequest req,HttpServletResponse rep)
        throws IOExeption {
            handleRequest(req,rep);
    }

    @Override
    protected doPost(HttpServletRequest req,HttpServletResponse rep)
        throws IOExeption {
            handleRequest(req,rep);
    }

    private String handleRequest(HttpServletRequest req, HttpServletResponse rep)
            throws IOExeption {
                String path = req.getRequestURI().substring(req.getContextPath().length());
                rep.setContentType("text/html;charset=UTF-8");
                PrintWriter out = rep.getWriter();
                out.println("URL recue :" + path);
    }
}