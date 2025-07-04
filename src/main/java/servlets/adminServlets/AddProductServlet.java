package servlets.adminServlets;

import dto.FileDto;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Product;
import repository.ProductRepository;
import service.FileService;
import service.ProductService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@MultipartConfig
@WebServlet(urlPatterns = "/admin/changes/add")
public class AddProductServlet extends HttpServlet {

    String storagePath;
    ProductService productService;
    FileService fileService;
    ProductRepository productRepository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storagePath = (String) getServletContext().getAttribute("storagePath");
        productService = (ProductService) getServletContext().getAttribute("productService");
        fileService = (FileService) getServletContext().getAttribute("fileService");
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/adminPages/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Part> parts = (List<Part>) req.getParts();

        String name = getStringFromPart(parts.get(0));
        String category = getStringFromPart(parts.get(1));
        String price = getStringFromPart(parts.get(2));
        String desc = getStringFromPart(parts.get(3));
        Part photo = parts.get(4);

        if(!name.isEmpty() && !category.isEmpty() && !price.isEmpty() && !desc.isEmpty() && !photo.getSubmittedFileName().isEmpty()){

            FileDto fileDto = FileDto.builder()
                    .name(photo.getSubmittedFileName())
                    .size(photo.getSize())
                    .contentType(photo.getContentType())
                    .inputStream(photo.getInputStream())
                    .storagePath(storagePath)
                    .build();

            String fileName = fileService.save(fileDto);

            Product product = Product.builder()
                    .nameOfProduct(name).category(category)
                    .price(Integer.valueOf(price)).description(desc)
                    .photoNameFile(fileName).build();

            productRepository.save(product);

            req.getSession().removeAttribute("messageForAddProduct");
            resp.sendRedirect("/admin/changes");
        } else {
            req.getSession().setAttribute("messageForAddProduct", "Не удалось сохранить: все поля должны быть заполнены, чтобы добавить товар");
            resp.sendRedirect("/admin/changes/add");
        }

    }

    private String getStringFromPart(Part part){
        try {
            InputStream inputStream = part.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            return new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}

