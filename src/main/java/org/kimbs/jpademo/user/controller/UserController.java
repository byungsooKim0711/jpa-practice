package org.kimbs.jpademo.user.controller;

import java.util.List;
import java.util.Optional;

import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> list() {
        final List<User> allUsers = userService.findAll();

        if(allUsers.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> selectOne(@PathVariable Long id) {
        final Optional<User> selected = userService.findById(id);

        if (!selected.isPresent()) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        
        return new ResponseEntity<User>(selected.get(), HttpStatus.OK);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<User> create(@RequestBody final User user, final UriComponentsBuilder uriBuilder) {
        User created = userService.create(user);
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/api/user/{id}").buildAndExpand(created.getId()).toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    } 

    @PutMapping(value = "/user/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody final User user) {
		Optional<User> updated = userService.findById(id);
		if (!updated.isPresent()) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
		user.setId(updated.get().getId());
        updated = Optional.of(userService.create(user));
        
		return new ResponseEntity<>(updated.get(), HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Optional<User> deleted = userService.findById(id);
		if (!deleted.isPresent()) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        
        return new ResponseEntity<Void>(HttpStatus.OK);
	}
}