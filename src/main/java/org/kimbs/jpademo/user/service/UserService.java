package org.kimbs.jpademo.user.service;

import java.util.List;
import java.util.Optional;

import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        User created = userRepository.saveAndFlush(user);
        return created;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}