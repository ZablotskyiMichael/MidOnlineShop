package com.websystique.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "Purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;

    @Column(name = "total_price")
    private double total_price ;

    @Column(name = "count")
    private int count ;

    @ManyToOne
    @JoinColumn (name = "status_id")
    private Dictionary status;

    public Dictionary getDictionary() {
        return status;
    }

    public void setDictionary(Dictionary dictionary) {
        this.status = dictionary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
