package br.com.test.centralservico.centralservicoapitest.persistence;

import br.com.test.centralservico.centralservicoapitest.domain.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
}
