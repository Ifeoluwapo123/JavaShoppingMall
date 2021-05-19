package com.shoppingMall.service;

import com.shoppingMall.model.Person;
import com.shoppingMall.repository.PersonRepository;
import com.shoppingMall.utilities.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public boolean createUser(Person person){
        boolean flag = false;

        try {
            person.setPassword(PasswordHashing.encryptPassword(person.getPassword()));

            Person userData = personRepository.findPersonByEmail(person.getEmail());

            if(userData == null) {
                personRepository.save(person);
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public Person loginUser(String email, String password){

        Person userData = null;

        try {

            userData = personRepository.findPersonByEmail(email);

            if(userData != null){
                if(!password.equals(PasswordHashing.decryptPassword(userData.getPassword())))
                    userData = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userData;
    }
}
