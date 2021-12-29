package com.atos.userapi.services;

import com.atos.userapi.entities.UserEntity;
import com.atos.userapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.Period;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository ur;

    public Iterable<UserEntity> findAll() {
        return ur.findAll();
    }

    public UserEntity findById(int id) {
        return ur.findById(id).get();
    }

    public UserEntity saveUser(UserEntity user) throws InvalidObjectException {
        checkUser(user);
        ur.save(user);

        return user;
    }

    public UserEntity updateUser(int id, UserEntity user) throws InvalidObjectException, NoSuchElementException {
        checkUser(user);
        try {
            UserEntity userToUpdate = this.findById(id);

            userToUpdate.setBirthdate(user.getBirthdate());
            userToUpdate.setCountry(user.getCountry());
            userToUpdate.setGender(user.getGender());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setPhone(user.getPhone());

            ur.save(userToUpdate);

            return userToUpdate;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public void deleteById(int id) {
        ur.deleteById(id);
    }

    private void checkObligatoryValue(UserEntity user) throws InvalidObjectException {
        if (user.getBirthdate() == null)
            throw new InvalidObjectException("The user must have a birthdate (yyyy-mm-dd)");
        else if (user.getUsername() == null)
            throw new InvalidObjectException("The user must have a username");
        else if (user.getCountry() == null)
            throw new InvalidObjectException("The user must have a country");
    }

    private void checkUser(UserEntity user) throws InvalidObjectException {
        String regexPattern = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$";

        // check the non null value of the user
        checkObligatoryValue(user);

        // check the validity of the non null value
        if (user.getPhone() != null && user.getPhone() != "" && !Pattern.compile(regexPattern).matcher(user.getPhone()).matches()) {
            throw new InvalidObjectException("Phone number is invalid");
        } else if (!user.getCountry().equals("France")){
            throw new InvalidObjectException("The user must be from France to be registered");
        } else if (Period.between(user.getBirthdate().toLocalDate(), LocalDate.now()).getYears() < 18){
            throw new InvalidObjectException("The user must be an adult (above 18 years old) to be registered");
        }
    }

}
