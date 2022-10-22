package br.com.test.centralservico.centralservicoapitest.service.impl;

import br.com.test.centralservico.centralservicoapitest.domain.model.Classification;
import br.com.test.centralservico.centralservicoapitest.persistence.ClassificationRepository;
import br.com.test.centralservico.centralservicoapitest.service.ClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassificationServiceImpl implements ClassificationService {

    private final ClassificationRepository classificationRepository;

    @Override
    public Optional<List<Classification>> findAll() {

        return Optional.of(classificationRepository.findAll());

    }

    @Override
    public Optional<Classification> findById(Long classificationId) {

        return classificationRepository.findById(classificationId);

    }

    @Override
    public Optional<Classification> save(Classification classification) {

        return Optional.of(classificationRepository.save(classification));

    }

    @Override
    public Optional<Classification> update(Classification classification) {

        Optional<Classification> classificationToUpdate = classificationRepository.findById(classification.getId());

        if(classificationToUpdate.isEmpty())
            return Optional.empty();

        return Optional.of(classificationRepository.saveAndFlush(classification));

    }

    @Override
    public Optional<Classification> deleteById(Long classificationId) {

        Optional<Classification> classificationToDelete = classificationRepository.findById(classificationId);

        classificationToDelete.ifPresent(classificationRepository::delete);

        return classificationToDelete;

    }

}
