package servlets.storage;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@WebServlet("/storage/*")
public class StorageServlet extends HttpServlet {

    private String storagePath;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        storagePath = (String) getServletContext().getAttribute("storagePath");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String fileName = req.getPathInfo().substring(1);

        InputStream inputStream = new FileInputStream(storagePath + "\\" + fileName);

        byte[] imageBytes = inputStream.readAllBytes();
        resp.setContentType(getServletContext().getMimeType("image/jpeg"));
        resp.getOutputStream().write(imageBytes);
    }


}
