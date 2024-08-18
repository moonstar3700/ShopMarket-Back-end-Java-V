package shop.domain.model;

import jakarta.persistence.*;

import javax.validation.constraints.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    private String username;

    @Email
    private String email;

    @NotBlank
    private String password;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<OwnedItem> ownedItems;

}
