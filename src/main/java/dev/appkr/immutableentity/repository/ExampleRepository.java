package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExampleRepository extends JpaRepository<Example, Long>,
    JpaSpecificationExecutor<Example>, ExampleRepositoryCustom {
}
