package code.flatura.easyexpendit.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "registered")
    private LocalDateTime registered;

    @Column(name = "last_logon")
    private LocalDateTime lastLogon;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "comment")
    private String comment;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(mappedBy = "author")
    private Set<Transaction> transactions;

    public User() {
    }

    public User(String password, String name, String email, LocalDateTime registered, LocalDateTime lastLogon, boolean enabled, String comment) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.registered = registered;
        this.lastLogon = lastLogon;
        this.enabled = enabled;
        this.comment = comment;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public LocalDateTime getLastLogon() {
        return lastLogon;
    }

    public void setLastLogon(LocalDateTime lastLogon) {
        this.lastLogon = lastLogon;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}