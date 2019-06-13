package menu_aurora_class;

import universal_dao.Cost;
import universal_dao.Discount;
import universal_dao.Weight;

import javax.persistence.*;

@Entity
@Table(name="aurora_cafe")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Weight
    @Column(name="weight", nullable = false)
    private int weight;

    @Cost
    @Column(name="cost", nullable = false)
    private int cost;

    @Discount
    @Column(name="discount", nullable = false)
    private int discount;

    public Menu() {}

    public Menu(String name, int weight, int cost, int discount) {
        this.name = name;
        this.weight = weight;
        this.cost = cost;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "id = " + id +
                ", name = " + name +
                ", weight = " + weight +
                ", cost = " + cost +
                ", discount = " + discount;
    }
}
