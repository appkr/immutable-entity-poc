package dev.appkr.immutableentity.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "contracts")
public class Contract extends AbstractAggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID externalId;

  @Enumerated(EnumType.STRING)
  private ContractStatus status;

  @Embedded
  private DateRange validThrough;
}
