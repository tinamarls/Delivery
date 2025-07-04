package servlets.listeners;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import models.Product;
import repository.ProductRepository;
import repository.impl.ProductRepositoryImpl;

import java.util.HashMap;
import java.util.Map;

@WebListener
public class AttributeListener implements HttpSessionAttributeListener {

    ProductRepository productRepository = new ProductRepositoryImpl();

    private static final Map<Product, Integer> allProductsInBag = new HashMap<>();

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        if (event.getName().equals("idForProduct")){

            String productID = (String) event.getSession().getAttribute("idForProduct");
            if(productRepository.findProductById(Long.valueOf(productID)).isPresent()){

                Product product = productRepository.findProductById(Long.valueOf(productID)).get();

                allProductsInBag.put(product, 1);
            }
        }

        if (event.getName().equals("idForMinus")){
            String value = (String) event.getSession().getAttribute("idForMinus");

            if(productRepository.findProductById(Long.valueOf(value)).isPresent()){
                Product product = productRepository.findProductById(Long.valueOf(value)).get();
                int countThisProduct = allProductsInBag.get(product);

                if(countThisProduct > 1){
                    allProductsInBag.put(product, countThisProduct-1);
                }
            }
        }

        if(event.getName().equals("deleteSomeProduct")){
            String value = (String) event.getSession().getAttribute("deleteSomeProduct");

            if(productRepository.findProductById(Long.valueOf(value)).isPresent()){
                Product product = productRepository.findProductById(Long.valueOf(value)).get();
                allProductsInBag.remove(product);
            }
        }

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

        if(event.getName().equals("idForProduct")){

            String value = (String) event.getSession().getAttribute("idForProduct");

            if(productRepository.findProductById(Long.valueOf(value)).isPresent()){
                Product product = productRepository.findProductById(Long.valueOf(value)).get();

                if(allProductsInBag.get(product) != null){

                    int count = allProductsInBag.get(product) + 1;
                    allProductsInBag.put(product, count);

                } else {
                    allProductsInBag.put(product, 1);
                }
            }

        }

        if (event.getName().equals("idForMinus")){
            String value = (String) event.getSession().getAttribute("idForMinus");

            if(productRepository.findProductById(Long.valueOf(value)).isPresent()){
                Product product = productRepository.findProductById(Long.valueOf(value)).get();
                int countThisProduct = allProductsInBag.get(product);

                if(countThisProduct > 1){
                    allProductsInBag.put(product, countThisProduct-1);
                } else {
                    allProductsInBag.remove(product);
                }
            }

        }

        if(event.getName().equals("deleteSomeProduct")){
            String value = (String) event.getSession().getAttribute("deleteSomeProduct");

            if(productRepository.findProductById(Long.valueOf(value)).isPresent()){
                Product product = productRepository.findProductById(Long.valueOf(value)).get();
                allProductsInBag.remove(product);
            }
        }

    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

        HttpSession session = event.getSession();

        if(event.getName().equals("allProducts")){
            allProductsInBag.clear();
            session.removeAttribute("idForProduct");
            session.removeAttribute("idForMinus");
            session.removeAttribute("fullPrice");
            session.removeAttribute("deleteSomeProduct");
        }

    }

    public static Map<Product, Integer> getBag() {return Map.copyOf(allProductsInBag);}

}
