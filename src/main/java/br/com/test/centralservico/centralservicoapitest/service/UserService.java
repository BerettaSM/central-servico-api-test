package br.com.test.centralservico.centralservicoapitest.service;

import br.com.test.centralservico.centralservicoapitest.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<List<User>> findAll();

    Optional<User> findById(Long userId);

    Optional<User> save(User user);

    Optional<User> update(User user);

    Optional<User> deleteById(Long userId);

}
