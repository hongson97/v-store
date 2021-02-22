package com.example.vstore.bind;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Products implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_product;
    private Long price;
    private Long number;
    private String name_product;

    public Products() {
    }
    public Products (Long id_product, Long price,Long number, String name_product) {
        this.id_product = id_product;
        this.price = price;
        this.number = number;
        this.name_product = name_product;
    }
    //get
    public Long getId_product() {return id_product;}
    public String getName_product() {
        return name_product;
    }
    public Long getPrice() {
        return price;
    }
    public Long getNumber() {
        return number;
    }



    //set
    public void setPrice(Long price) {
        this.price = price;
    }
    public  void setName_product(String name_product) {
        this.name_product = name_product;
    }
    public void setNumber(Long number) {
        this.number = number;
    }


}
