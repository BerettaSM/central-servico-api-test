package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.domain.model.Level;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.LevelService;
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
@RequestMapping("/api/user-level")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LevelController {

    private final LevelService levelService;

    @GetMapping
    public ResponseEntity<Optional<List<Level>>> findAll() {

        Optional<List<Level>> allUserLevels = levelService.findAll();

        if(allUserLevels.isEmpty())
            throw new ResourceNotFoundException("Nenhum nível de usuário foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(allUserLevels);

    }

    @GetMapping("/{userLevelId}")
    public ResponseEntity<Optional<Level>> findById(@PathVariable Long levelId) {

        Optional<Level> userLevel = levelService.findById(levelId);

        if(userLevel.isEmpty())
            throw new ResourceNotFoundException("Nenhum nível com o id " + levelId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(userLevel);

    }

    @PostMapping
    public ResponseEntity<Optional<Level>> save(@RequestBody Level level) {

        level.setId(0L);

        Optional<Level> levelToSave = levelService.save(level);

        return ResponseEntity.status(HttpStatus.OK).body(levelToSave);

    }

    @PutMapping
    public ResponseEntity<Optional<Level>> update(@RequestBody Level level) {

        Optional<Level> levelToUpdate = levelService.update(level);

        if(levelToUpdate.isEmpty())
            throw new ResourceNotFoundException("Ticket não existe no banco de dados.");

        return ResponseEntity.status(HttpStatus.OK).body(levelToUpdate);

    }

    @DeleteMapping("/{levelId}")
    public ResponseEntity<Optional<Level>> deleteById(@PathVariable Long levelId) {

        Optional<Level> levelToDelete = levelService.deleteById(levelId);

        if(levelToDelete.isEmpty())
            throw new ResourceNotFoundException("Nenhum nível com o id " + levelId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(levelToDelete);

    }

}
