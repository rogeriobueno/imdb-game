package com.bueno.imdbgame.services;

import com.bueno.imdbgame.exceptions.NotFoundException;
import com.bueno.imdbgame.model.entities.User;
import com.bueno.imdbgame.model.repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User findById(Long id) {
        return userRepository.findById(id).orElseThrow((() -> {
            log.info("User not found with id {}", id);
            return new NotFoundException("user not found" + id);
        }));
    }
}
