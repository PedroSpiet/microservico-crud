package com.spiet.crud.entities;

import com.spiet.crud.data.vo.ProductVO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_products")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "inventory", nullable = false, length = 255)
    private Integer inventory;

    @Column(name = "price", nullable = false, length = 10)
    private Double price;

    public static Product create(ProductVO product) {
        return new ModelMapper().map(product, Product.class);
    }
}
