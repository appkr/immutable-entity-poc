package dev.appkr.immutableentity.domain.state;

import dev.appkr.immutableentity.domain.Contract;

public interface ContractState {

  default void beginEffective(Contract context) throws IllegalStateException {
    throw new IllegalStateException();
  }

  default void terminate(Contract context) throws IllegalStateException {
    throw new IllegalStateException();
  }
}
