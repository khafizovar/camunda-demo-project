package camunda.poc.extenshions.httpclient.impl.util;

import java.util.Map;

import camunda.poc.extenshions.httpclient.impl.RequestConfigOption;
import org.apache.http.client.config.RequestConfig.Builder;

import org.camunda.connect.httpclient.impl.HttpConnectorLogger;
import org.camunda.connect.httpclient.impl.HttpLogger;

public class ParseUtil {

  protected static HttpConnectorLogger LOG = HttpLogger.HTTP_LOGGER;

  public static void parseConfigOptions(Map<String, Object> configOptions, Builder configBuilder) {
    for (RequestConfigOption option : RequestConfigOption.values()) {
      try {
        if (configOptions.containsKey(option.getName())) {
          option.apply(configBuilder, configOptions.get(option.getName()));
        }
      } catch (ClassCastException e) {
        throw LOG.invalidConfigurationOption(option.getName(), e);
      }
    }
  }
}