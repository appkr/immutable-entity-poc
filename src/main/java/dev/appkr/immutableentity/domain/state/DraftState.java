package dev.appkr.immutableentity.domain.state;

import dev.appkr.immutableentity.domain.Contract;

public class DraftState implements ContractState {

  @Override
  public void beginEffective(Contract context) throws IllegalStateException {
    context.changeState(new EffectiveState());
  }
}
