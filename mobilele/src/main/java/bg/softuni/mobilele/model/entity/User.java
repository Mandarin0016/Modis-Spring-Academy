package bg.softuni.mobilele.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Base {
    @Column(nullable = false)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<UserRole> userRoles = new ArrayList<>();

    public User addRole(UserRole userRole){
        this.userRoles.add(userRole);
        return this;
    }
}
