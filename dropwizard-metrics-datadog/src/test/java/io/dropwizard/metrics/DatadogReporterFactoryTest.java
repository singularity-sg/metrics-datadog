package io.dropwizard.metrics;

import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatadogReporterFactoryTest {
  @Test
  public void isDiscoverable() throws Exception {
    Assertions
        .assertThat(new DiscoverableSubtypeResolver().getDiscoveredSubtypes())
        .contains(DatadogReporterFactory.class);
  }
}