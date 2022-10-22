package br.com.test.centralservico.centralservicoapitest.service;

import br.com.test.centralservico.centralservicoapitest.domain.model.Area;

import java.util.List;
import java.util.Optional;

public interface AreaService {

    Optional<List<Area>> findAll();

    Optional<Area> findById(Long areaId);

    Optional<Area> save(Area area);

    Optional<Area> update(Area area);

    Optional<Area> deleteById(Long areaId);

}
