package com.websystique.springboot.controller;

import com.websystique.springboot.model.*;
import com.websystique.springboot.repositories.CategoryRepository;
import com.websystique.springboot.repositories.ProductRepository;
import com.websystique.springboot.repositories.PurchaseRepository;
import com.websystique.springboot.service.*;
import com.websystique.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class ProductController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    private static int STATUS_NOT_ENOUGH_MONEY = 1;
    private static int STATUS_NOT_ITEMS_IN_STOCK = 2;

    @Autowired
    UserService userService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    ProductService productService;
    @Autowired
    StorageService storageService;
    @Autowired
    PropertiesService propertiesService;

    @RequestMapping(value = "/product/", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProducts() {
        List<Product> products = productService.findAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }
    @RequestMapping(value = "/purchase/", method = RequestMethod.GET)
    public ResponseEntity<List<Purchase>> listBoughtPurchases() {
        List<Purchase> purchases = purchaseService.findAllPurchaseByStatus("BOUGHT");
        if (purchases.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
    }
   /* @RequestMapping(value = "/basked/", method = RequestMethod.GET)
    public ResponseEntity<List<Purchase>> listBaskedPurchases() {
        List<Purchase> purchases = purchaseService.findAllPurchaseByStatus("BASKED");
        if (purchases.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
    }*/
    @RequestMapping(value = "/pfilter/", method = RequestMethod.GET)
    public ResponseEntity<List<Purchase>> listPurchasesByUser() {
        List<Purchase> purchases = purchaseService.findPurchaseByUser("BOUGHT");
        if (purchases.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
    }
    @RequestMapping(value = "/baskedfilter/", method = RequestMethod.GET)
    public ResponseEntity<List<Purchase>> listPurchasesByUserInBusked() {
        List<Purchase> purchases = purchaseService.findPurchaseByUser("BASKED");
        if (purchases.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Purchase>>(purchases, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        logger.info("Fetching product with id {}", id);
        Product product = productService.findById(id);
        if (product == null) {
            logger.error("product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Product with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
    @RequestMapping(value = "/purchase/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPurchase(@PathVariable("id") long id) {
        logger.info("Fetching product with id {}", id);
        Purchase purchase = purchaseService.findById(id);
        if (purchase == null) {
            logger.error("purchase with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Purchase with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Purchase>(purchase, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/", method = RequestMethod.POST)
    public ResponseEntity<?> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
        logger.info("Creating product : {}", product);
        long productId = product.getId();
        if (productService.isProductExist(product)) {
            logger.error("Unable to create. A product with name {} already exist", product.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A product with name " +
                    product.getName() + " already exist."),HttpStatus.CONFLICT);
        }
        productService.saveProduct(product);
        Storage storage = new Storage();
        storage.setId(productId);
        storage.setProduct(product);
        storage.setCount(0);
        storageService.saveStorage(storage);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/product/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/purchase/", method = RequestMethod.POST)
    public ResponseEntity<?> createPurchase(@RequestBody Purchase purchase, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Purchase : {}", purchase);
        int countOfProduct = storageService.findByProduct(purchase.getProduct()).getCount();
        if (purchaseService.isPurchaseExist(purchase)) {
            logger.error("Unable to create. A Purchase with name {} already exist", purchase.getId());
            return new ResponseEntity(new CustomErrorType("Unable to create. A purchase with id " +
                    purchase.getId() + " already exist."),HttpStatus.CONFLICT);
        }
        if (countOfProduct==0){
            return new ResponseEntity(new CustomErrorType("Sorry, the item is out of stock"),HttpStatus.CONFLICT);
        }
        if (purchase.getCount()<=0){
            return new ResponseEntity(new CustomErrorType("please enter the quantity of the item"),HttpStatus.CONFLICT);
        }
        if (countOfProduct>=purchase.getCount()){
            int discount = propertiesService.getCurrentDiscount(purchase.getUser());
            double totalPrise = purchase.getTotal_price()-(purchase.getTotal_price()*discount)/100;
            String formattedDouble =  new DecimalFormat("#0.00").format(totalPrise);
            purchase.setTotal_price(Double.parseDouble(formattedDouble.replace(',','.')));
        purchaseService.savePurchase(purchase);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/purchase/{id}").buildAndExpand(purchase.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }else {
            return new ResponseEntity(new CustomErrorType("Unable to create. No such quantity in the warehouse. Max:"+storageService.findByProduct(purchase.getProduct()).getCount()),HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/filter/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> filterProducts(@PathVariable("id") long id) {
        Category category = categoryRepository.findOne(id);
        List<Product> filterProducts =new ArrayList<>();
        List<Product> products= productService.findAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getCategory().equals(category)){
                filterProducts.add(products.get(i));
            }
        }

        return new ResponseEntity<List<Product>>(filterProducts, HttpStatus.OK);
    }
    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProducts(@PathVariable("id") long id, @RequestBody Product product) {
        logger.info("Updating Product with id {}", id);

        Product currentProduct = productService.findById(id);

        if (currentProduct == null) {
            logger.error("Unable to update. Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentProduct.setName(product.getName());
        currentProduct.setCategory(product.getCategory());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setRelated_product_id(product.getRelated_product_id());
        //currentProduct.setImage(product.getImage());

        productService.saveProduct(currentProduct);
        return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
    }
    @RequestMapping(value = "/purchase/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePurchase(@PathVariable("id") long id, @RequestBody Purchase purchase) {
        logger.info("Updating Dictionary with id {}", id);

        Purchase currentPurchese = purchaseService.findById(id);

        if (currentPurchese == null) {
            logger.error("Unable to update. Purchase with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. Purchase with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        currentPurchese.setProduct(currentPurchese.getProduct());
        currentPurchese.setCount(currentPurchese.getCount());
        currentPurchese.setTotal_price(currentPurchese.getTotal_price());
        currentPurchese.setUser(currentPurchese.getUser());

        purchaseService.updatePurchase(currentPurchese);
        return new ResponseEntity<Purchase>(currentPurchese, HttpStatus.OK);
    }


    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProducts(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);

        Product product = productService.findById(id);
        if (product == null) {
            logger.error("Unable to delete. Product with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Product with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        productService.deleteProductById(id);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/purchase/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePurchase(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting Purchase with id {}", id);

        Purchase purchase =purchaseService.findById(id);
        if (purchase == null) {
            logger.error("Unable to delete. Purchase with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. Purchase with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        purchaseService.deletePurchaseById(id);
        return new ResponseEntity<Purchase>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/basked/", method = RequestMethod.DELETE)
    public ResponseEntity<?> buyBasked() {
			int status = purchaseService.buyBasked();
			if (status==STATUS_NOT_ENOUGH_MONEY){
			    return (new ResponseEntity(new CustomErrorType("You do not have enough money on the account"),
                        HttpStatus.NOT_FOUND));
            }
            if (status==STATUS_NOT_ITEMS_IN_STOCK){
                return (new ResponseEntity(new CustomErrorType("No such quantity of items in the warehouse"),
                        HttpStatus.NOT_FOUND));
            }

        return new ResponseEntity<Dictionary>(HttpStatus.NO_CONTENT);
    }



    @RequestMapping(value = "/product/", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteAllProducts() {
        logger.info("Deleting All Products");

        productService.deleteAllProducts();
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
    @RequestMapping(value = "/purchase/", method = RequestMethod.DELETE)
    public ResponseEntity<Purchase> deleteAllPurchases() {
        logger.info("Deleting All Purchases");

        purchaseService.deleteAllPurchase();
        return new ResponseEntity<Purchase>(HttpStatus.NO_CONTENT);
    }

}