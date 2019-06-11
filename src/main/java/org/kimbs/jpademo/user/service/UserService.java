package org.kimbs.jpademo.user.service;

import org.kimbs.jpademo.exception.ResourceNotFoundException;
import org.kimbs.jpademo.user.domain.User;
import org.kimbs.jpademo.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User updateById(Long id, User user) {
        User updated = this.findById(id);

        updated.setEmail(user.getEmail());
        updated.setLoginId(user.getLoginId());
        updated.setPassword(user.getPassword());
        updated.setPhone(user.getPhone());

        updated = userRepository.saveAndFlush(updated);
        return updated;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User resource not found with ID: " + id));
    }

    public void deleteById(Long id) {
        User deleted = this.findById(id);
        userRepository.deleteById(deleted.getId());
    }
}