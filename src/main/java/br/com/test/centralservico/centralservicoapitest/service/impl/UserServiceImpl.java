package br.com.test.centralservico.centralservicoapitest.service.impl;


import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.persistence.UserRepository;
import br.com.test.centralservico.centralservicoapitest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<List<User>> findAll() {

        return Optional.of(userRepository.findAll());

    }

    @Override
    public Optional<User> findById(Long userId) {

        return userRepository.findById(userId);

    }

    @Override
    public Optional<User> save(User user) {

        return Optional.of(userRepository.save(user));

    }

    @Override
    public Optional<User> update(User user) {

        Optional<User> userToUpdate = userRepository.findById(user.getId());

        if(userToUpdate.isEmpty())
            return Optional.empty();

        return Optional.of(userRepository.saveAndFlush(user));

    }

    @Override
    public Optional<User> deleteById(Long userId) {

        Optional<User> userToDelete = userRepository.findById(userId);

        userToDelete.ifPresent(userRepository::delete);

        return userToDelete;

    }

}
