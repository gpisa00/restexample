package it.arteprogrammazione.restexample.repositories.common.entities;


import javax.persistence.*;

@Table(name = "card_types")
@Entity
public class CardType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
