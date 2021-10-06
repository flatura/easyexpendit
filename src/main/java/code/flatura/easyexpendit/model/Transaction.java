package code.flatura.easyexpendit.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private UUID id;

    @Column(name = "type")
    private Status type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consumable_id")
    private Consumable consumable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "comment")
    private String comment;

    public Transaction() {
    }

    public Transaction(Status type, Consumable consumable, User author, LocalDateTime dateTime, String comment) {
        this.type = type;
        this.consumable = consumable;
        this.author = author;
        this.dateTime = dateTime;
        this.comment = comment;
    }

    public Status getType() {
        return type;
    }

    public void setType(Status type) {
        this.type = type;
    }

    public Consumable getConsumable() {
        return consumable;
    }

    public void setConsumable(Consumable consumable) {
        this.consumable = consumable;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
