package io.github.singularitysg.datadog;

import io.github.singularitysg.datadog.model.DatadogCounter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DatadogCounterTest {

  @Test
  public void testSplitNameAndTags() {
    List<String> tags = new ArrayList<String>();
    tags.add("env:prod");
    tags.add("version:1.0.0");
    DatadogCounter counter = new DatadogCounter(
        "test[tag1:value1,tag2:value2,tag3:value3]", 1L, 1234L, "Test Host", tags);
    List<String> allTags = counter.getTags();

    assertThat(allTags.size()).isEqualTo(5);
    assertThat(allTags.get(0)).isEqualTo("tag1:value1");
    assertThat(allTags.get(1)).isEqualTo("tag2:value2");
    assertThat(allTags.get(2)).isEqualTo("tag3:value3");
    assertThat(allTags.get(3)).isEqualTo("env:prod");
    assertThat(allTags.get(4)).isEqualTo("version:1.0.0");
  }

}
