package dev.appkr.immutableentity.domain;

import static dev.appkr.immutableentity.domain.ContractStatus.DRAFT;
import static dev.appkr.immutableentity.domain.ContractStatus.EFFECTIVE;
import static dev.appkr.immutableentity.domain.ContractStatus.TERMINATED;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.domain.factory.ContractFactory;
import dev.appkr.immutableentity.domain.state.ContractState;
import dev.appkr.immutableentity.domain.state.DraftState;
import dev.appkr.immutableentity.domain.state.EffectiveState;
import dev.appkr.immutableentity.domain.state.TerminatedState;
import javax.annotation.PostConstruct;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "contracts")
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Contract extends AbstractAggregateRoot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private UUID externalId;

  @Enumerated(EnumType.STRING)
  private ContractStatus status;

  @Transient
  private ContractState state;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "from", column = @Column(name = "valid_from")),
      @AttributeOverride(name = "to", column = @Column(name = "valid_to"))
  })
  private DateRange validThrough;

  public static Contract make(ContractDto dto) {
    return new ContractFactory().from(dto);
  }

  protected Contract() {
  }

  Contract(UUID externalId, ContractStatus status, DateRange validThrough) {
    this.externalId = externalId;
    this.status = status;
    this.validThrough = validThrough;
  }

  @Builder
  Contract(Long id, UUID externalId, ContractStatus status, DateRange validThrough) {
    this.id = id;
    this.externalId = externalId;
    this.status = status;
    this.validThrough = validThrough;
    loadState();
  }

  @PostConstruct
  void loadState() {
    // @Transient field will not be initialized when hydrating object from database query result.
    // @see https://stackoverflow.com/questions/10313535/initializing-a-transient-attribute-of-a-jpa-entity-during-criteriaquery
    if (status == null) {
      this.status = DRAFT;
    }

    switch (status) {
      case DRAFT:
        this.state = new DraftState();
        break;
      case EFFECTIVE:
        this.state = new EffectiveState();
        break;
      case TERMINATED:
        this.state = new TerminatedState();
        break;
    }
  }

  public void beginEffective() {
    this.state.beginEffective(this);
    this.status = EFFECTIVE;
  }

  public void terminate() {
    this.state.terminate(this);
    this.status = TERMINATED;
  }

  public void changeState(ContractState newState) {
    this.state = newState;
  }
}
