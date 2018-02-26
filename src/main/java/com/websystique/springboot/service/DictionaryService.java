package com.websystique.springboot.service;

import com.websystique.springboot.model.Dictionary;

import java.util.List;

public interface DictionaryService {
    Dictionary findById(Long id,String discriminator);

    Dictionary findByName(String name);

    void saveDictionary(Dictionary dictionary);

    void updateDictionary(Dictionary dictionary);

    void deleteDictionaryById(Long id);

    void deleteAllDictionaries();

    List<Dictionary> findAllDictionaries();

    List<Dictionary> findAllDictionariesByDiscriminator(String discriminator);

    boolean isDictionaryExist(Dictionary role);
}