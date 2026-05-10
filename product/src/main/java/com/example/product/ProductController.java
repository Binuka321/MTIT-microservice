package com.example.product;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Management", description = "APIs for Product Service")
@OpenAPIDefinition(
        info = @Info(title = "Product API", version = "1.0"),
        servers = {

                @Server(url = "http://localhost:8080/api", description = "Gateway Access (8080)"),
             
                @Server(url = "http://localhost:8085", description = "Direct Access (8085)")
        }
)
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    @Operation(summary = "Get all products")
    public List<Product> getProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public Product getProductById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public Product addProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updated = service.updateProduct(id, product);
        if (updated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return updated;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public String deleteProduct(@PathVariable Long id) {
        boolean deleted = service.deleteProduct(id);
        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return "Product deleted successfully";
    }
}