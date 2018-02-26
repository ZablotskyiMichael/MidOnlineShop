package com.websystique.springboot.model;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name= "Comments")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user ;

    @Column(name = "text")
    private String text ;

   /* @Column(name = "password")
    private String password;*/

    @Column(name = "parentId")
    private long parentId;

    @ManyToOne
    @JoinColumn(name = "product",nullable = false)
    private Product product;

    @Column(name = "time")
    private String time;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /*public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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

    public String getTest() {
        return text;
    }

    public void setTest(String text) {
        this.text = text;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
