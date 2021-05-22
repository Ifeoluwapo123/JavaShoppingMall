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

    /**
     * CREATE operation on Person
     * @param person
     * @return boolean
     * */
    public boolean createUser(Person person){
        boolean flag = false;

        try {
            person.setPassword(PasswordHashing.encryptPassword(person.getPassword()));

            Person userData = personRepository.findPersonByEmail(person.getEmail());

            if(userData == null) {
                person.setPosition("user");
                personRepository.save(person);
                flag = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * GET operation on Person
     * @param email
     * @param password
     * @return boolean(true for successful update and false on failure on post)
     * */
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
