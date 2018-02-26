package com.websystique.springboot.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
@Entity
@Table(name = "dictionary")
@EntityListeners(AuditingEntityListener.class)
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "dictionary_id")
    private Long dictionary_id;


    @Column(name = "discriminator")
    private String discriminator;

    @Column(name = "name")
    private String name;

    public Long getDictionary_id() {
        return dictionary_id;
    }

    public void setDictionary_id(Long dictionary_id) {
        this.dictionary_id = dictionary_id;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
