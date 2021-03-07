package dev.appkr.immutableentity.domain;

import dev.appkr.immutableentity.api.model.ContractDto;
import dev.appkr.immutableentity.domain.factory.ContractFactory;
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

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "from", column = @Column(name = "valid_from")),
      @AttributeOverride(name = "to", column = @Column(name = "valid_to"))
  })
  private DateRange validThrough;

  public static Contract from(ContractDto dto) {
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
  }
}
