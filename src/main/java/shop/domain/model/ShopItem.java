package shop.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotBlank;

@Entity
public class ShopItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String imageurl;

    @NotBlank(message = "name.missing")
    private String name;

    private String description;

    @NotBlank(message = "price.missing")
    private double Price;
}
