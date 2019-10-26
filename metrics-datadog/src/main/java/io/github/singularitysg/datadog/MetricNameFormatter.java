package io.github.singularitysg.datadog;

public interface MetricNameFormatter {

  public String format(String name, String... path);
}
