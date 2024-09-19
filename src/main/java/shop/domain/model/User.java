package shop.domain.model;

import jakarta.persistence.*;

import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "username.missing")
    private String username;

    @Email
    @NotBlank(message = "email.missing")
    private String email;

    @NotBlank(message = "password.missing")
    private String password;

//    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
//    private List<OwnedItem> ownedItems;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*    public List<OwnedItem> getOwnedItems() {
        return ownedItems;
    }

    public void setOwnedItems(List<OwnedItem> ownedItems) {
        this.ownedItems = ownedItems;
    }*/
}
