package code.flatura.easyexpendit.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @ManyToMany
    private Set<User> users;
}
