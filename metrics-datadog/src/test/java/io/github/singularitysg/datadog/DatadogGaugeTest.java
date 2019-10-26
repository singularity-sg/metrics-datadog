package io.github.singularitysg.datadog;

import io.github.singularitysg.datadog.model.DatadogGauge;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class DatadogGaugeTest {

  @Test
  public void testSplitNameAndTags() {
    List<String> tags = new ArrayList<String>();
    tags.add("env:prod");
    tags.add("version:1.0.0");
    DatadogGauge gauge = new DatadogGauge(
      "test[tag1:value1,tag2:value2,tag3:value3]", 1L, 1234L, "Test Host", tags);
    List<String> allTags = gauge.getTags();

    assertThat(allTags).hasSize(5);
    assertThat(allTags).containsExactly(
      "tag1:value1",
      "tag2:value2",
      "tag3:value3",
      "env:prod",
      "version:1.0.0"
    );

  }
}
