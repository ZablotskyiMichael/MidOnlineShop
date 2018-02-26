package com.websystique.springboot.repositories;

import com.websystique.springboot.model.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    Dictionary findByName(String name);
    List<Dictionary> findAllByDiscriminator(String discriminator);
}
