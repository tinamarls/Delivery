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
import service.CourierService;
import service.FileService;
import service.UserService;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(urlPatterns = "/admin/changes/product")
public class ChangeProductServlet extends HttpServlet {

    CourierService courierService;
    UserService userService;
    ProductRepository productRepository;
    String storagePath;
    FileService fileService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        courierService = (CourierService) getServletContext().getAttribute("courierService");
        userService = (UserService) getServletContext().getAttribute("userService");
        storagePath = (String) getServletContext().getAttribute("storagePath");
        fileService = (FileService) getServletContext().getAttribute("fileService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(req.getParameter("idChange") != null){
            Long idChange = Long.valueOf(req.getParameter("idChange"));
            req.getServletContext().setAttribute("idChange", idChange);
            if(productRepository.findProductById(idChange).isPresent()){
                Product product = productRepository.findProductById(idChange).get();
                req.setAttribute("product", product);
            }
        } else{
            Long idChange = (Long) req.getServletContext().getAttribute("idChange");
            if(productRepository.findProductById(idChange).isPresent()){
                Product product = productRepository.findProductById(idChange).get();
                req.setAttribute("product", product);
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/adminPages/product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Part> parts = (List<Part>) req.getParts();

        String name = getStringFromPart(parts.get(0));
        String category = getStringFromPart(parts.get(1));
        String price = getStringFromPart(parts.get(2));
        String desc = getStringFromPart(parts.get(3));
        Part photo = parts.get(4);

        if (!name.isEmpty() || !category.isEmpty() || !price.isEmpty() || !desc.isEmpty() || !photo.getSubmittedFileName().isEmpty()) {

            String storageName = "";

            if(!photo.getSubmittedFileName().isEmpty()){
                FileDto fileDto = FileDto.builder()
                        .name(photo.getSubmittedFileName())
                        .size(photo.getSize())
                        .contentType(photo.getContentType())
                        .inputStream(photo.getInputStream())
                        .storagePath(storagePath)
                        .build();

                storageName = fileService.save(fileDto);
            }

            int finishPrice;
            if(price.isEmpty()){
                finishPrice = -100;
            } else{
                finishPrice = Integer.parseInt(price);
            }

            Product productNew = Product.builder()
                    .nameOfProduct(name).category(category)
                    .price(finishPrice).description(desc).photoNameFile(storageName)
                    .build();

            if( req.getServletContext().getAttribute("idChange") != null){
                Long idChange = (Long) req.getServletContext().getAttribute("idChange");

                if(productRepository.findProductById(idChange).isPresent()){
                    Product productOld = productRepository.findProductById(idChange).get();
                    productRepository.update(productOld, productNew);
                }
            }

            req.getSession().setAttribute("messageForChange", "");
            resp.sendRedirect("/admin/changes");

        } else {

            req.getSession().setAttribute("messageForChange", "Не удалось изменить: хотя бы одно поле должно быть не пустым");
            resp.sendRedirect("/admin/changes/product");
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
