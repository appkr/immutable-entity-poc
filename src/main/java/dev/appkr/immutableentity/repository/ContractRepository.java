package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}
