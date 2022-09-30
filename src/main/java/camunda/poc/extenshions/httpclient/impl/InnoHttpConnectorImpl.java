package camunda.poc.extenshions.httpclient.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;

import static camunda.poc.extenshions.httpclient.impl.ConstParams.CONNECTOR_ID;

public class InnoHttpConnectorImpl extends AbstractHttpConnector<HttpRequest, HttpResponse> implements HttpConnector {

  public InnoHttpConnectorImpl() {
    super(CONNECTOR_ID);
  }

  public InnoHttpConnectorImpl(String connectorId) {
    super(connectorId);
  }

  public HttpRequest createRequest() {
    return new HttpRequestImpl(this);
  }

  protected HttpResponse createResponse(CloseableHttpResponse response) {
    return new HttpResponseImpl(response);
  }

}
