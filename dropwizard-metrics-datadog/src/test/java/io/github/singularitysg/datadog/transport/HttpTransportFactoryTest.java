package io.github.singularitysg.datadog.transport;

import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HttpTransportFactoryTest {
  @Test
  public void isDiscoverable() throws Exception {
    Assertions
            .assertThat(new DiscoverableSubtypeResolver().getDiscoveredSubtypes())
            .contains(HttpTransportFactory.class);
  }
}