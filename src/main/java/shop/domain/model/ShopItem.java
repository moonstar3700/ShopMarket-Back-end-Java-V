package shop.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/** Lombok use */

@Entity @Getter @Setter
@Table(name = "shopitems")
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
