package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.UserService;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Optional<List<User>>> findAll() {

        Optional<List<User>> allUsers = userService.findAll();

        if(allUsers.isEmpty())
            throw new ResourceNotFoundException("Nenhum usuário foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(allUsers);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> findById(@PathVariable Long userId) {

        Optional<User> user = userService.findById(userId);

        if(user.isEmpty())
            throw new ResourceNotFoundException("Nenhum usuário com o id " + userId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @PostMapping
    public ResponseEntity<Optional<User>> save(@RequestBody User user) {

        user.setId(0L);

        Optional<User> userToSave = userService.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(userToSave);

    }

    @PutMapping
    public ResponseEntity<Optional<User>> update(@RequestBody User user) {

        Optional<User> userToUpdate = userService.update(user);

        if(userToUpdate.isEmpty())
            throw new ResourceNotFoundException("Usuário não existe no banco de dados.");

        return ResponseEntity.status(HttpStatus.OK).body(userToUpdate);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Optional<User>> deleteById(@PathVariable Long userId) {

        Optional<User> userToDelete = userService.deleteById(userId);

        if(userToDelete.isEmpty())
            throw new ResourceNotFoundException("Nenhum usuário com o id " + userId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(userToDelete);

    }

}
