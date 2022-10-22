package br.com.test.centralservico.centralservicoapitest.service;

import br.com.test.centralservico.centralservicoapitest.domain.model.Level;

import java.util.List;
import java.util.Optional;

public interface LevelService {

    Optional<List<Level>> findAll();

    Optional<Level> findById(Long levelId);

    Optional<Level> save(Level level);

    Optional<Level> update(Level level);

    Optional<Level> deleteById(Long levelId);

}
