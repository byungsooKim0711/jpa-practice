package org.kimbs.jpademo.user.controller;

import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public List<User> users() {
        return userService.findAll();
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> selectOne(@PathVariable Long id) {
        final User selected = userService.findById(id);

        return new ResponseEntity<>(selected, HttpStatus.OK);
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
        User updated = userService.updateById(id, user);

		return new ResponseEntity<>(updated, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "/user/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteById(id);

        return ResponseEntity.accepted().build();
	}
}