package dev.appkr.immutableentity.config.security;

import static dev.appkr.immutableentity.config.Constants.UNKNOWN_USER_ID;

import dev.appkr.immutableentity.support.SecurityUtils;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of(SecurityUtils.getCurrentUserLogin().orElse(UNKNOWN_USER_ID));
  }
}
