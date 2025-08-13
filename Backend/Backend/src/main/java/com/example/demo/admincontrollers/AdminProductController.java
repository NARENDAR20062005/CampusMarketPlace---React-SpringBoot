package com.example.demo.admincontrollers;

import java.util.Map;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.adminservices.AdminProductService;
import com.example.demo.enities.Product;

@RestController
@RequestMapping("/admin/products")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AdminProductController {
	  private final AdminProductService adminProductService;

	    public AdminProductController(AdminProductService adminProductService) {
	        this.adminProductService = adminProductService;
	    }

	    @PostMapping("/add")
	    public ResponseEntity<?> addProduct(@RequestBody Map<String, Object> productRequest) {
	        try {
	            String name = (String) productRequest.get("name");
	            String description = (String) productRequest.get("description");
	            Double price = Double.valueOf(String.valueOf(productRequest.get("price")));
	            Integer stock = (Integer) productRequest.get("stock");
	            Integer categoryId = (Integer) productRequest.get("categoryId");
	            String imageUrl = (String) productRequest.get("imageUrl");

	            Product addedProduct = adminProductService.addProductWithImage(name, description, price, stock, categoryId, imageUrl);
	            return ResponseEntity.status(HttpStatus.CREATED).body(addedProduct);
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	        }
	    }
	    
	    
	    @DeleteMapping("/delete")
	    public ResponseEntity<?> deleteProduct(@RequestBody Map<String, Integer> requestBody) {
	        try {
	            Integer productId = requestBody.get("productId");
	            adminProductService.deleteProduct(productId);
	            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
	        }
	    }

}
