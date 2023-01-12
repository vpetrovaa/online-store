package com.solvd.onlinestore.service.impl;

import com.solvd.onlinestore.domain.User;
import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.repository.UserRepository;
import com.solvd.onlinestore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceDoesNotExistException("There are no user with such id"));

    }

    @Override
    @Transactional
    public User save(User user) {
        if(userRepository.isExistsByEmail(user.getEmail())){
            throw new ResourceAlreadyExistsException("Such user is already exists");
        }
        userRepository.save(user);
        return user;
    }

}
