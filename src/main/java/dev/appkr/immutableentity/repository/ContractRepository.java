package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, Long> {

  Optional<Contract> findTopByExternalIdOrderByIdDesc(@Param("externalId") UUID externalId);
  List<Contract> findByExternalId(@Param("externalId") UUID externalId);
}
