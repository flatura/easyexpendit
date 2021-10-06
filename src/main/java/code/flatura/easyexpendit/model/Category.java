package code.flatura.easyexpendit.model;

import javax.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;
}
