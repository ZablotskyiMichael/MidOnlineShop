package com.websystique.springboot.service;

import com.websystique.springboot.model.Dictionary;
import com.websystique.springboot.repositories.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("dictionaryService")
@Transactional
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    public Dictionary findById(Long id,String discriminator) {
        List<Dictionary> filterByDiscrimination  = dictionaryRepository.findAllByDiscriminator(discriminator);
        for (Dictionary dictionary:filterByDiscrimination ) {
            if (dictionary.getDictionary_id().equals(id)){
                return dictionary;
            }
        }
        return null;
    }

    public Dictionary findByName(String name) {
        return dictionaryRepository.findByName(name);
    }

    public void saveDictionary(Dictionary dictionary) {
        dictionaryRepository.save(dictionary);
    }

    public void updateDictionary(Dictionary dictionary){

        saveDictionary(dictionary);
    }

    public void deleteDictionaryById(Long id){
        dictionaryRepository.delete(id);
    }

    public void deleteAllDictionaries(){
        dictionaryRepository.deleteAll();
    }

    public List<Dictionary> findAllDictionaries(){
        return dictionaryRepository.findAll();
    }

    @Override
    public List<Dictionary> findAllDictionariesByDiscriminator(String discriminator) {
            List <Dictionary> dictionaries = findAllDictionaries();
            List <Dictionary> dictionariesByDiscriminator = new ArrayList<>();
        for (int i = 0; i < dictionaries.size(); i++) {
            if (dictionaries.get(i).getDiscriminator().equals(discriminator)){
                dictionariesByDiscriminator.add(dictionaries.get(i));
            }
        }
        return dictionariesByDiscriminator;
    }


    public boolean isDictionaryExist(Dictionary Dictionary) {
        return findByName(Dictionary.getName()) != null;
    }

}