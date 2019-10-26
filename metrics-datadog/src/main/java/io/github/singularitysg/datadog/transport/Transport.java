package io.github.singularitysg.datadog.transport;

import io.github.singularitysg.datadog.model.DatadogCounter;
import io.github.singularitysg.datadog.model.DatadogGauge;

import java.io.Closeable;
import java.io.IOException;

/**
 * The transport layer for pushing metrics to datadog
 */
public interface Transport extends Closeable {

  /**
   * Build a request context.
   * @throws IOException
   * @return {@link Request}
   */
  public Request prepare() throws IOException;

  /**
   * A request for batching of metrics to be pushed to datadog.
   * The call order is expected to be:
   *    one or more of addGauge, addCounter -&gt; send()
   */
  public interface Request {

    /**
     * Add a gauge
     * @param gauge
     */
    void addGauge(DatadogGauge gauge) throws IOException;

    /**
     * Add a counter to the request
     * @param counter
     */
    void addCounter(DatadogCounter counter) throws IOException;

    /**
     * Send the request to datadog
     * @throws IOException
     */
    void send() throws Exception;
  }
}
