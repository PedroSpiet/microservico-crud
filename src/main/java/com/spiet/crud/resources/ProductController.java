package com.spiet.crud.resources;

import com.spiet.crud.data.vo.ProductVO;
import com.spiet.crud.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService service;
    private final PagedResourcesAssembler<ProductVO> assembler;

    @Autowired
    public ProductController(ProductService service, PagedResourcesAssembler<ProductVO> assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml",})
    public ProductVO findById(@PathVariable("id")  Long id) {
        ProductVO productVO = service.findById(id);
        productVO.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return productVO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml",})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction)
    {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection));

        Page<ProductVO> products = service.findAll(pageable);
        products.stream().forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getId())).withSelfRel()));
        PagedModel<EntityModel<ProductVO>> pagedModel = assembler.toModel(products);

        return ResponseEntity.ok().body(pagedModel);
    }

    public ProductVO create(@RequestBody ProductVO produtoVO) {
        ProductVO proVo = service.create(produtoVO);
        proVo.add(linkTo(methodOn(ProductController.class).findById(proVo.getId())).withSelfRel());
        return proVo;
    }

    @PutMapping(produces = {"application/json","application/xml","application/x-yaml"},
            consumes = {"application/json","application/xml","application/x-yaml"})
    public ProductVO update(@RequestBody ProductVO produtoVO) {
        ProductVO proVo = service.update(produtoVO);
        proVo.add(linkTo(methodOn(ProductController.class).findById(produtoVO.getId())).withSelfRel());
        return proVo;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
