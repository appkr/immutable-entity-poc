package dev.appkr.immutableentity.domain.state;

import dev.appkr.immutableentity.domain.Contract;

public class EffectiveState implements ContractState {

  @Override
  public void terminate(Contract context) throws IllegalStateException {
    context.changeState(new TerminatedState());
  }
}
