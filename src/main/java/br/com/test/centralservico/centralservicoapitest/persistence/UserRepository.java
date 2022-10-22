package br.com.test.centralservico.centralservicoapitest.persistence;

import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
