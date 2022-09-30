package camunda.poc.extenshions.httpclient.impl;

import gtm.caller.SpringUtil;
import gtm.caller.service.GtmAuthService;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.camunda.commons.utils.IoUtil;
import org.camunda.connect.httpclient.HttpResponse;
import org.camunda.connect.impl.AbstractCloseableConnectorResponse;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class HttpResponseImpl extends AbstractCloseableConnectorResponse implements HttpResponse {

  private final HttpConnectorLogger LOG = HttpLogger.HTTP_LOGGER;

  protected CloseableHttpResponse httpResponse;

  public HttpResponseImpl(CloseableHttpResponse httpResponse) {
    this.httpResponse = httpResponse;
  }

  public Integer getStatusCode() {
    return getResponseParameter(PARAM_NAME_STATUS_CODE);
  }

  public String getResponse() {
    return getResponseParameter(PARAM_NAME_RESPONSE);
  }

  public Map<String, String> getHeaders() {
    GtmAuthService gtmAuthService = SpringUtil.getApplicationContext().getBean(GtmAuthService.class);
    return getResponseParameter(PARAM_NAME_RESPONSE_HEADERS);
  }

  public String getHeader(String field) {
    Map<String, String> headers = getHeaders();
    if (headers != null) {
      return headers.get(field);
    }
    else {
      return null;
    }
  }

  protected void collectResponseParameters(Map<String, Object> responseParameters) {
    if (httpResponse.getStatusLine() != null) {
      responseParameters.put(PARAM_NAME_STATUS_CODE, httpResponse.getStatusLine().getStatusCode());
    }
    collectResponseHeaders();

    if (httpResponse.getEntity() != null) {
      try {
        String response = IoUtil.inputStreamAsString(httpResponse.getEntity().getContent());
        responseParameters.put(PARAM_NAME_RESPONSE, response);
      } catch (IOException e) {
        throw LOG.unableToReadResponse(e);
      } finally {
        IoUtil.closeSilently(httpResponse);
      }
    }
  }

  protected void collectResponseHeaders() {
    Map<String, String> headers = new HashMap<String, String>();
    for (Header header : httpResponse.getAllHeaders()) {
      headers.put(header.getName(), header.getValue());
    }
    responseParameters.put(PARAM_NAME_RESPONSE_HEADERS, headers);
  }

  protected Closeable getClosable() {
    return httpResponse;
  }

}
