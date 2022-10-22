package br.com.test.centralservico.centralservicoapitest.service;

import br.com.test.centralservico.centralservicoapitest.domain.model.Classification;

import java.util.List;
import java.util.Optional;

public interface ClassificationService {

    Optional<List<Classification>> findAll();

    Optional<Classification> findById(Long classificationId);

    Optional<Classification> save(Classification classification);

    Optional<Classification> update(Classification classification);

    Optional<Classification> deleteById(Long classificationId);

}
