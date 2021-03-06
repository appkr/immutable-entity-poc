package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.PricingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
}
