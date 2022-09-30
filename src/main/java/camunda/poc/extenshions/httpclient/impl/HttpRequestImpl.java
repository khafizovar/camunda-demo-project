package camunda.poc.extenshions.httpclient.impl;

import gtm.caller.SpringUtil;
import gtm.caller.service.GtmAuthService;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.connect.spi.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class HttpRequestImpl extends AbstractHttpRequest<HttpRequest, HttpResponse> implements HttpRequest {

  private static final Logger LOG = LoggerFactory.getLogger(HttpRequestImpl.class);

  public HttpRequestImpl(Connector connector) {
    super(connector);
  }

  @Override
  public Map<String, String> getHeaders() {
    Map<String, String> headers = super.getHeaders();
    try {
      GtmAuthService gtmAuthService = SpringUtil.getApplicationContext().getBean(GtmAuthService.class);
      if (gtmAuthService != null) {
        headers.put("Authorization", gtmAuthService.getJwtToken());
      }
    } catch (Exception e) {
      LOG.error("Error while getting JWT token", e);
    }
    return headers;
  }
}
