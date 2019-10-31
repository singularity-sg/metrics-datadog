package io.github.singularitysg.datadog.transport;

import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UdpTransportFactoryTest {
  @Test
  public void isDiscoverable() throws Exception {
    Assertions
            .assertThat(new DiscoverableSubtypeResolver().getDiscoveredSubtypes())
            .contains(UdpTransportFactory.class);
  }
}