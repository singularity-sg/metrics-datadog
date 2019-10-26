package io.dropwizard.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.singularitysg.datadog.DatadogReporter;
import io.github.singularitysg.datadog.DefaultMetricNameFormatterFactory;
import io.github.singularitysg.datadog.DynamicTagsCallbackFactory;
import io.github.singularitysg.datadog.MetricNameFormatterFactory;
import io.github.singularitysg.datadog.transport.AbstractTransportFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.EnumSet;
import java.util.List;

import static io.github.singularitysg.datadog.DatadogReporter.Expansion;

@JsonTypeName("datadog")
public class DatadogReporterFactory extends BaseReporterFactory {

  @JsonProperty
  private String host = null;

  @JsonProperty
  private List<String> tags = null;

  @Valid
  @JsonProperty
  private DynamicTagsCallbackFactory dynamicTagsCallback = null;

  @JsonProperty
  private String prefix = null;

  @Valid
  @NotNull
  @JsonProperty
  private EnumSet<Expansion> expansions = EnumSet.allOf(Expansion.class);

  @Valid
  @NotNull
  @JsonProperty
  private MetricNameFormatterFactory metricNameFormatter = new DefaultMetricNameFormatterFactory();

  @Valid
  @NotNull
  @JsonProperty
  private AbstractTransportFactory transport = null;

  public ScheduledReporter build(MetricRegistry registry) {
    return DatadogReporter.forRegistry(registry)
        .withTransport(transport.build())
        .withHost(host)
        .withTags(tags)
        .withPrefix(prefix)
        .withExpansions(expansions)
        .withMetricNameFormatter(metricNameFormatter.build())
        .withDynamicTagCallback(dynamicTagsCallback != null ? dynamicTagsCallback.build() : null)
        .filter(getFilter())
        .convertDurationsTo(getDurationUnit())
        .convertRatesTo(getRateUnit())
        .build();
    }
}