package com.dannyhromau.employee.service.impl;

import com.dannyhromau.employee.core.config.ErrorMessages;
import com.dannyhromau.employee.exception.DeletingEntityException;
import com.dannyhromau.employee.exception.EntityNotfoundException;
import com.dannyhromau.employee.exception.InvalidInputDataException;
import com.dannyhromau.employee.model.User;
import com.dannyhromau.employee.repository.UserRepository;
import com.dannyhromau.employee.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getEntities(Pageable page) {
        return userRepository.findAll(page).toList();
    }

    @Override
    public User getEntityById(UUID id) {
        return checkValidData(id);
    }

    @Override
    public User addEntity(User user) {
        user.setCreatedOn(ZonedDateTime.now());
        userRepository.save(user);
        user = userRepository.findByUsername(user.getUsername());
        if (user == null) {
            throw new InvalidInputDataException(ErrorMessages.CREATING_DATA_ERROR_MESSAGE.label);
        }
        return user;
    }

    @Override
    public UUID deleteEntity(UUID id) {
        User user = checkValidData(id);
        userRepository.deleteById(user.getId());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new DeletingEntityException(ErrorMessages.DELETING_DATA_ERROR_MESSAGE.label);
        }
        return id;
    }

    @Override
    public User updateEntity(User user) {
        ZonedDateTime updateTime = ZonedDateTime.now();
        user.setUpdatedOn(updateTime);
        userRepository.save(user);
        if (userRepository.findByUpdatedOnAndId(updateTime, user.getId()) == null) {
            throw new InvalidInputDataException(ErrorMessages.UPDATING_DATA_ERROR_MESSAGE.label);
        }
        return user;
    }

    private User checkValidData(@NonNull UUID id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new EntityNotfoundException(String.format(ErrorMessages.ENTITY_NOT_FOUND_MESSAGE.label, id));
        } else {
            return userOpt.get();
        }
    }
}
