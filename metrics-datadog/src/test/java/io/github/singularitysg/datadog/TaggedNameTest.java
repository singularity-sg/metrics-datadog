package io.github.singularitysg.datadog;


import org.junit.jupiter.api.Test;

import static io.github.singularitysg.datadog.TaggedName.TaggedNameBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class TaggedNameTest {

  @Test
  public void testBuildNoTags() throws Exception {
    TaggedName taggedName = new TaggedNameBuilder()
        .metricName("metric")
        .build();

    assertThat(taggedName.getMetricName()).isEqualTo("metric");
    assertThat(taggedName.getEncodedTags().size()).isEqualTo(0);
    assertThat(taggedName.encode()).isEqualTo("metric");
  }

  @Test
  public void testBuildOneTag() throws Exception {
    TaggedName taggedName = new TaggedNameBuilder()
        .metricName("metric")
        .addTag("key1:val1")
        .build();

    assertThat(taggedName.getMetricName()).isEqualTo("metric");
    assertThat(taggedName.getEncodedTags().size()).isEqualTo(1);
    assertThat(taggedName.getEncodedTags().get(0)).isEqualTo("key1:val1");

    assertThat(taggedName.encode()).isEqualTo("metric[key1:val1]");
  }

  @Test
  public void testBuildManyTags() throws Exception {
    TaggedName taggedName = new TaggedNameBuilder()
        .metricName("metric")
        .addTag("key1", "val1")
        .addTag("key2", "val2")
        .build();

    assertThat(taggedName.getMetricName()).isEqualTo("metric");
    assertThat(taggedName.getEncodedTags().size()).isEqualTo(2);
    assertThat(taggedName.getEncodedTags().get(0)).isEqualTo("key1:val1");
    assertThat(taggedName.getEncodedTags().get(1)).isEqualTo("key2:val2");

    assertThat(taggedName.encode()).isEqualTo("metric[key1:val1,key2:val2]");
  }

  @Test
  public void testDecodeNoTags() throws Exception {
    TaggedName tn = TaggedName.decode("metric");
    assertThat(tn.getMetricName()).isEqualTo("metric");
    assertThat(tn.getEncodedTags().size()).isEqualTo(0);
  }

  @Test
  public void testDecodeOneTag() throws Exception {
    TaggedName tn = TaggedName.decode("metric[key]");
    assertThat(tn.getMetricName()).isEqualTo("metric");
    assertThat(tn.getEncodedTags().size()).isEqualTo(1);
    assertThat(tn.getEncodedTags().get(0)).isEqualTo("key");

  }

  @Test
  public void testDecodeTwoTags() throws Exception {
    TaggedName tn = TaggedName.decode("metric[key1:val1,key2]");
    assertThat(tn.getMetricName()).isEqualTo("metric");
    assertThat(tn.getEncodedTags().size()).isEqualTo(2);
    assertThat(tn.getEncodedTags().get(0)).isEqualTo("key1:val1");
    assertThat(tn.getEncodedTags().get(1)).isEqualTo("key2");
  }

  @Test
  public void testDecodeTwoTagsDotted() throws Exception {
    TaggedName tn = TaggedName.decode("my.metric.name[key.1:val.1,key.2]");
    assertThat(tn.getMetricName()).isEqualTo("my.metric.name");
    assertThat(tn.getEncodedTags().size()).isEqualTo(2);
    assertThat(tn.getEncodedTags().get(0)).isEqualTo("key.1:val.1");
    assertThat(tn.getEncodedTags().get(1)).isEqualTo("key.2");
  }

  @Test
  public void testDecodeTwoTagsDashed() throws Exception {
    TaggedName tn = TaggedName.decode("my-metric-name[key-1:val-1,key-2]");
    assertThat(tn.getMetricName()).isEqualTo("my-metric-name");
    assertThat(tn.getEncodedTags().size()).isEqualTo(2);
    assertThat(tn.getEncodedTags().get(0)).isEqualTo("key-1:val-1");
    assertThat(tn.getEncodedTags().get(1)).isEqualTo("key-2");
  }

  @Test
  public void testDecodeTwoTagsUnderscored() throws Exception {
    TaggedName tn = TaggedName.decode("my_metric_name[key_1:val_1,key_2]");
    assertThat(tn.getMetricName()).isEqualTo("my_metric_name");
    assertThat(tn.getEncodedTags().size()).isEqualTo(2);
    assertThat(tn.getEncodedTags().get(0)).isEqualTo("key_1:val_1");
    assertThat(tn.getEncodedTags().get(1)).isEqualTo("key_2");
  }

  @Test
  public void testDecodeInvalidEncodings() throws Exception {
    // note that parsing could be stricter, but we're relaxing things to
    // maintain backward compatibility with prior parsing logic
    assertInvalidEncoding(null);
    assertInvalidEncoding("");
    assertInvalidEncoding("metric[ ,]");
    assertInvalidEncoding("metric[tag, ]");
    assertInvalidEncoding("metric[,tag]");
    assertInvalidEncoding("metric[tag,,tag]");
  }

  private void assertInvalidEncoding(String encodedTagName) {
    try {
      TaggedName n = TaggedName.decode(encodedTagName);
      System.out.println(n.getMetricName() + ": " + n.getEncodedTags().size());
      fail("Expected decoding exception");
    } catch (Exception e) {
      // ok
    }
  }
}
