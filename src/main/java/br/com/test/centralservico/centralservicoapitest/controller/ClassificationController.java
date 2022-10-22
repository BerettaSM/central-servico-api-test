package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.domain.model.Classification;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.ClassificationService;
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
@RequestMapping("/api/classification")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassificationController {

    private final ClassificationService classificationService;

    @GetMapping
    public ResponseEntity<Optional<List<Classification>>> findAll() {

        Optional<List<Classification>> allClassifications = classificationService.findAll();

        if(allClassifications.isEmpty())
            throw new ResourceNotFoundException("Nenhuma classificação foi encontrada.");

        return ResponseEntity.status(HttpStatus.OK).body(allClassifications);

    }

    @GetMapping("/{classificationId}")
    public ResponseEntity<Optional<Classification>> findById(@PathVariable Long classificationId) {

        Optional<Classification> classification = classificationService.findById(classificationId);

        if(classification.isEmpty())
            throw new ResourceNotFoundException("Nenhuma classificação com o id " + classificationId + " foi encontrada.");

        return ResponseEntity.status(HttpStatus.OK).body(classification);

    }

    @PostMapping
    public ResponseEntity<Optional<Classification>> save(@RequestBody Classification classification) {

        classification.setId(0L);

        Optional<Classification> classificationToSave = classificationService.save(classification);

        return ResponseEntity.status(HttpStatus.OK).body(classificationToSave);

    }

    @PutMapping
    public ResponseEntity<Optional<Classification>> update(@RequestBody Classification classification) {

        Optional<Classification> classificationToUpdate = classificationService.update(classification);

        if(classificationToUpdate.isEmpty())
            throw new ResourceNotFoundException("Classificação não existe no banco de dados.");

        return ResponseEntity.status(HttpStatus.OK).body(classificationToUpdate);

    }

    @DeleteMapping("/{classificationId}")
    public ResponseEntity<Optional<Classification>> deleteById(@PathVariable Long classificationId) {

        Optional<Classification> classificationToDelete = classificationService.deleteById(classificationId);

        if(classificationToDelete.isEmpty())
            throw new ResourceNotFoundException("Nenhuma classificação com o id " + classificationId + " foi encontrada.");

        return ResponseEntity.status(HttpStatus.OK).body(classificationToDelete);

    }

}
