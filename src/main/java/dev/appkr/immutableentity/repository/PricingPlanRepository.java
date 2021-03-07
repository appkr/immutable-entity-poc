package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.PricingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {

  Optional<PricingPlan> findByContractId(@Param("contractId") Long contractId);
}
