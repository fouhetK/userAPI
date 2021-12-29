package com.atos.userapi.api;

import com.atos.userapi.services.UserService;
import com.atos.userapi.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InvalidObjectException;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    @Autowired
    UserService us;

    @GetMapping(value="")
    public ResponseEntity<Iterable<UserEntity>> getAll(){
        return ResponseEntity.ok(us.findAll());
    }

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity user) {
        try{
            us.saveUser(user);

            return ResponseEntity.ok(user);
        }catch ( InvalidObjectException e ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , e.getMessage() );
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") int id){
        try{
            UserEntity user = us.findById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , "User not found" );
        }

    }
}
