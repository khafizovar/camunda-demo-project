package camunda.poc.extenshions.httpclient.impl;

import java.net.InetAddress;
import java.util.Collection;
import java.util.function.BiConsumer;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig.Builder;

public enum RequestConfigOption {

  AUTHENTICATION_ENABLED("authentication-enabled",
      (builder, value) -> builder.setAuthenticationEnabled((boolean) value)),
  CIRCULAR_REDIRECTS_ALLOWED("circular-redirects-allowed",
      (builder, value) -> builder.setCircularRedirectsAllowed((boolean) value)),
  CONNECTION_TIMEOUT("connection-timeout",
      (builder, value) -> builder.setConnectTimeout((int) value)),
  CONNECTION_REQUEST_TIMEOUT("connection-request-timeout",
      (builder, value) -> builder.setConnectionRequestTimeout((int) value)),
  CONTENT_COMPRESSION_ENABLED("content-compression-enabled",
      (builder, value) -> builder.setContentCompressionEnabled((boolean) value)),
  COOKIE_SPEC("cookie-spec",
      (builder, value) -> builder.setCookieSpec((String) value)),
  DECOMPRESSION_ENABLED("decompression-enabled",
      (builder, value) -> builder.setDecompressionEnabled((boolean) value)),
  EXPECT_CONTINUE_ENABLED("expect-continue-enabled",
      (builder, value) -> builder.setExpectContinueEnabled((boolean) value)),
  LOCAL_ADDRESS("local-address",
      (builder, value) -> builder.setLocalAddress((InetAddress) value)),
  MAX_REDIRECTS("max-redirects",
      (builder, value) -> builder.setMaxRedirects((int) value)),
  NORMALIZE_URI("normalize-uri",
      (builder, value) -> builder.setNormalizeUri((boolean) value)),
  PROXY("proxy",
      (builder, value) -> builder.setProxy((HttpHost) value)),
  PROXY_PREFERRED_AUTH_SCHEMES("proxy-preferred-auth-scheme",
      (builder, value) -> builder.setProxyPreferredAuthSchemes((Collection<String>) value)),
  REDIRECTS_ENABLED("relative-redirects-allowed",
      (builder, value) -> builder.setRedirectsEnabled((boolean) value)),
  RELATIVE_REDIRECTS_ALLOWED("relative-redirects-allowed",
      (builder, value) -> builder.setRelativeRedirectsAllowed((boolean) value)),
  SOCKET_TIMEOUT("socket-timeout",
      (builder, value) -> builder.setSocketTimeout((int) value)),
  STALE_CONNECTION_CHECK_ENABLED("stale-connection-check-enabled",
      (builder, value) -> builder.setStaleConnectionCheckEnabled((boolean) value)),
  TARGET_PREFERRED_AUTH_SCHEMES("target-preferred-auth-schemes",
      (builder, value) -> builder.setTargetPreferredAuthSchemes((Collection<String>) value));

  private String name;
  private BiConsumer<Builder, Object> consumer;

  private RequestConfigOption(String name, BiConsumer<Builder, Object> consumer) {
    this.name = name;
    this.consumer = consumer;
  }

  public String getName() {
    return name;
  }

  public void apply(Builder configBuilder, Object value) {
    this.consumer.accept(configBuilder, value);
  }

}
