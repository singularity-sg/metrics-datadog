package io.github.singularitysg.datadog.transport;

import org.junit.jupiter.api.Test;

import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


/**
 * Some of the tests in this class cannot be implemented without overriding the DNS Cache
 * It was previously performed using a library from AliBaba https://github.com/alibaba/java-dns-cache-manipulator
 * but with the upgrade to JDK 11l, this doesn't work any more
 */
public class UdpTransportTest {
  private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
  private static final String LOCAL_IP = "127.0.0.1";
  private static final String TEST_HOST = "fastandunresolvable";
  private static final String TEST_LOCALHOST = "localhost";
  private static final int TEST_PORT = 1111;

  @Test
  public void constructsWithReachableHost() {
    final UdpTransport transport = new UdpTransport.Builder().withStatsdHost(TEST_LOCALHOST).build();
    assertThat(transport).isNotNull();
  }

  @Test
  public void constructsWhenUnreachableHostWithRetry() {
    assertThatExceptionOfType(RuntimeException.class).isThrownBy(
      new UdpTransport.Builder().withStatsdHost(TEST_HOST).withRetryingLookup(true)::build
    );
  }

  @Test
  public void throwsWhenUnreachableHost() {
    assertThatExceptionOfType(RuntimeException.class).isThrownBy(
      new UdpTransport.Builder().withStatsdHost(TEST_HOST)::build
    );
  }

  @Test
  public void volatileResolverResolvesByTheTimeTheHostIsResolvable() throws Exception {
    // ^ Doesn't crash when host is unresolvable.
    final Callable<SocketAddress> retryingCallable = UdpTransport.volatileAddressResolver(TEST_HOST, TEST_PORT);

    // ^ This should throw because the host is unresolvable.
    assertThatExceptionOfType(UnknownHostException.class).isThrownBy(retryingCallable::call);
  }
}
