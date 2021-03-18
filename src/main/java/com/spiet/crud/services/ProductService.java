package com.spiet.crud.services;

import com.spiet.crud.data.vo.ProductVO;
import com.spiet.crud.entities.Product;
import com.spiet.crud.exceptions.ResourceNotFoundException;
import com.spiet.crud.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository repo;

    @Autowired
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public ProductVO create(ProductVO productVO) {
       ProductVO product = ProductVO.create(repo.save(Product.create(productVO)));
       return product;
    }

    public Page<ProductVO> findAll(Pageable pageable) {
        var page = repo.findAll(pageable);
        return page.map(this::convertToProductVO);
    }

    private ProductVO convertToProductVO(Product product) {
        return ProductVO.create(product);
    }

    public ProductVO findById(Long id) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não encontrado!"));
        return ProductVO.create(entity);
    }

    public ProductVO update(ProductVO productVO) {
        final Optional<Product> optionalProduct = repo.findById(productVO.getId());

        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Não encontrado!");
        }

        return ProductVO.create(repo.save(Product.create(productVO)));
    }

    public void delete(Long id) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não encontrado!"));

        repo.delete(entity);
    }
}
