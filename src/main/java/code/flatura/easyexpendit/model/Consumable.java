package code.flatura.easyexpendit.model;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="consumable")
public class Consumable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    private String name;

    @Column(name="contract")
    private String contract;

    @Column(name="price")
    private Integer price;

    @Column(name="part_number")
    private String partNumber;

    @ManyToOne
    @JoinColumn(name="consumable_category_id")
    private Category category;

    @Column(name="status")
    private Status status;

    @OneToMany(mappedBy = "consumable")
    private Set<Transaction> transactions;

    public Consumable() {
    }

    public Consumable(String name, String contract, Integer price, String partNumber, Category category, Status status, Set<Transaction> transactions) {
        this.name = name;
        this.contract = contract;
        this.price = price;
        this.partNumber = partNumber;
        this.category = category;
        this.status = status;
        this.transactions = transactions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
