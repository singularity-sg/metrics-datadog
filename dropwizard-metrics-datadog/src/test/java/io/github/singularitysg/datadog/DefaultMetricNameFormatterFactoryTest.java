package io.github.singularitysg.datadog;

import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultMetricNameFormatterFactoryTest {
  @Test
  public void isDiscoverable() throws Exception {
    Assertions
            .assertThat(new DiscoverableSubtypeResolver().getDiscoveredSubtypes())
            .contains(DefaultMetricNameFormatterFactory.class);
  }

  @Test
  public void testBuild() throws Exception {
    Assertions
            .assertThat(new DefaultMetricNameFormatterFactory().build())
            .isInstanceOf(DefaultMetricNameFormatter.class);
  }
}
