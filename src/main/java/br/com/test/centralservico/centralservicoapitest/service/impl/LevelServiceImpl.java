package br.com.test.centralservico.centralservicoapitest.service.impl;

import br.com.test.centralservico.centralservicoapitest.domain.model.Level;
import br.com.test.centralservico.centralservicoapitest.persistence.LevelRepository;
import br.com.test.centralservico.centralservicoapitest.service.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LevelServiceImpl implements LevelService {

    private final LevelRepository userLevelRepository;

    @Override
    public Optional<List<Level>> findAll() {

        return Optional.of(userLevelRepository.findAll());

    }

    @Override
    public Optional<Level> findById(Long levelId) {

        return userLevelRepository.findById(levelId);

    }

    @Override
    public Optional<Level> save(Level level) {

        return Optional.of(userLevelRepository.save(level));

    }

    @Override
    public Optional<Level> update(Level level) {

        Optional<Level> levelToUpdate = userLevelRepository.findById(level.getId());

        if(levelToUpdate.isEmpty())
            return Optional.empty();

        return Optional.of(userLevelRepository.saveAndFlush(level));

    }

    @Override
    public Optional<Level> deleteById(Long levelId) {

        Optional<Level> levelToDelete = userLevelRepository.findById(levelId);

        levelToDelete.ifPresent(userLevelRepository::delete);

        return levelToDelete;
    }

}
