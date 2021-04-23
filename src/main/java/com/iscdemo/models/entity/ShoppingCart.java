package com.iscdemo.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "cart")
public class ShoppingCart implements Serializable {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_sequence_generator")
    @SequenceGenerator(name = "cart_sequence_generator", sequenceName = "cart_id_sequence")
    private int id;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "total_quantity")
    private int totalQuantity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    @OrderColumn(name="type")
    private List<Order> orders;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
