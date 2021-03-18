package com.spiet.crud.data.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.spiet.crud.entities.Product;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@JsonPropertyOrder({"id", "name", "inventory", "price"})
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -3596714608156597250L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("inventory")
    private Integer inventory;

    @JsonProperty("price")
    private Double price;

    public static ProductVO create(Product product) {
        return new ModelMapper().map(product, ProductVO.class);
    }
}
