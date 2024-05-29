package com.sii.promocodes.product.infrastructure;

import com.sii.promocodes.product.domain.ProductFacade;
import com.sii.promocodes.product.dto.ProductApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductFacade productFacade;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductApi.Product createProduct(@RequestBody @Valid ProductApi.CreateProductRequest request) {
        return productFacade.createProduct(request);
    }

    @GetMapping("/all")
    public List<ProductApi.Product> getProducts() {
        return productFacade.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductApi.Product getProduct(@PathVariable UUID id) {
        return productFacade.getProduct(id);
    }

    @PutMapping("/{id}")
    public ProductApi.Product updateProduct(@PathVariable UUID id, @RequestBody @Valid ProductApi.UpdateProductRequest request) {
        return productFacade.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productFacade.deleteProduct(id);
    }

}
