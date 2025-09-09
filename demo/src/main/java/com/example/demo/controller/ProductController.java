/*package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000") // allow React dev server
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public Page<Product> getProducts(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String category,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {

    return productService.getProducts(name, category, page, size, sortBy, sortDir);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Product createProduct(@RequestBody Product product) {
    return productService.createProduct(product);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
    return productService.updateProduct(id, product);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }
}
*/

package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000") // allow React dev server

public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  // ✅ Anyone with USER/ADMIN role can view products
  @GetMapping
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public Page<Product> getProducts(
      @RequestParam(defaultValue = "") String search,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String direction) {

    Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    return productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(search, search, pageable);
  }

  // ✅ Only Admins can add
  @PostMapping("/add")
  @PreAuthorize("hasRole('ADMIN')")
  public Product createProduct(@RequestBody Product product) {
    return productRepository.save(product);
  }

  // ✅ Only Admins can update
  @PutMapping("/update/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
    return productRepository.findById(id).map(product -> {
      product.setName(updatedProduct.getName());
      product.setCategory(updatedProduct.getCategory());
      product.setPrice(updatedProduct.getPrice());
      return productRepository.save(product);
    }).orElseThrow(() -> new RuntimeException("Product not found"));
  }

  // ✅ Only Admins can delete
  @DeleteMapping("/delete/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public String deleteProduct(@PathVariable Long id) {
    productRepository.deleteById(id);
    return "Product deleted successfully";
  }
}
