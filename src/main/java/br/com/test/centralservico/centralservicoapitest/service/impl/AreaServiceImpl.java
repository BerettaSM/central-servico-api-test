package br.com.test.centralservico.centralservicoapitest.service.impl;


import br.com.test.centralservico.centralservicoapitest.domain.model.Area;
import br.com.test.centralservico.centralservicoapitest.persistence.AreaRepository;
import br.com.test.centralservico.centralservicoapitest.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    public Optional<List<Area>> findAll() {

       return  Optional.of(areaRepository.findAll());

    }

    @Override
    public Optional<Area> findById(Long areaId) {

        return areaRepository.findById(areaId);

    }

    @Override
    public Optional<Area> save(Area area) {

        return Optional.of(areaRepository.save(area));

    }

    @Override
    public Optional<Area> update(Area area) {

        Optional<Area> areaToUpdate = areaRepository.findById(area.getId());

        if(areaToUpdate.isEmpty())
            return Optional.empty();

        return Optional.of(areaRepository.saveAndFlush(area));

    }

    @Override
    public Optional<Area> deleteById(Long areaId) {

        Optional<Area> areaToDelete = areaRepository.findById(areaId);

        areaToDelete.ifPresent(areaRepository::delete);

        return areaToDelete;

    }

}
