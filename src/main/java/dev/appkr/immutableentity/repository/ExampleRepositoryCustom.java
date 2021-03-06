package dev.appkr.immutableentity.repository;

import dev.appkr.immutableentity.domain.Example;
import java.util.List;

public interface ExampleRepositoryCustom {

  List<Example> findCreatedToday();
}
