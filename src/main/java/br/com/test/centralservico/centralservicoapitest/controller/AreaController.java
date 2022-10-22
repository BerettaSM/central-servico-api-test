package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.domain.model.Area;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/area")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<Optional<List<Area>>> findAll() {

        Optional<List<Area>> allAreas = areaService.findAll();

        if(allAreas.isEmpty())
            throw new ResourceNotFoundException("Nenhuma área foi encontrada.");

        return ResponseEntity.status(HttpStatus.OK).body(allAreas);

    }

    @GetMapping("/{areaId}")
    public ResponseEntity<Optional<Area>> findById(@PathVariable Long areaId) {

        Optional<Area> area = areaService.findById(areaId);

        if(area.isEmpty())
            throw new ResourceNotFoundException("Nenhuma área com o id " + areaId + " foi encontrada.");

        return ResponseEntity.status(HttpStatus.OK).body(area);

    }

    @PostMapping
    public ResponseEntity<Optional<Area>> save(@RequestBody Area area) {

        area.setId(0L);

        Optional<Area> areaToSave = areaService.save(area);

        return ResponseEntity.status(HttpStatus.OK).body(areaToSave);

    }

    @PutMapping
    public ResponseEntity<Optional<Area>> update(@RequestBody Area area) {

        Optional<Area> areaToUpdate = areaService.update(area);

        if(areaToUpdate.isEmpty())
            throw new ResourceNotFoundException("Área não existe no banco de dados.");

        return ResponseEntity.status(HttpStatus.OK).body(areaToUpdate);

    }

    @DeleteMapping("/{areaId}")
    public ResponseEntity<Optional<Area>> deleteById(@PathVariable Long areaId) {

        Optional<Area> areaToDelete = areaService.deleteById(areaId);

        if(areaToDelete.isEmpty())
            throw new ResourceNotFoundException("Nenhuma área com o id " + areaId + " foi encontrada.");

        return ResponseEntity.status(HttpStatus.OK).body(areaToDelete);

    }

}
