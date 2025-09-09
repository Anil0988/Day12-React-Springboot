package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepository;

  public Page<Product> getProducts(String name, String category, int page, int size, String sortBy, String dir) {
    Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    return productRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(
        name == null ? "" : name,
        category == null ? "" : category,
        pageable);
  }

  public Product createProduct(Product p) {
    return productRepository.save(p);
  }

  public Product updateProduct(Long id, Product p) {
    return productRepository.findById(id).map(db -> {
      db.setName(p.getName());
      db.setCategory(p.getCategory());
      db.setPrice(p.getPrice());
      return productRepository.save(db);
    }).orElseThrow(() -> new RuntimeException("Product not found: " + id));
  }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}
