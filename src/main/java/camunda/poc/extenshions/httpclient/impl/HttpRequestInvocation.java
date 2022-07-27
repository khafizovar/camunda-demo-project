package camunda.poc.extenshions.httpclient.impl;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;
import org.camunda.connect.spi.ConnectorRequest;
import org.camunda.connect.impl.AbstractRequestInvocation;
import org.camunda.connect.spi.ConnectorRequestInterceptor;

public class HttpRequestInvocation  extends AbstractRequestInvocation<HttpRequestBase> {

  protected HttpClient client;

  public HttpRequestInvocation(HttpRequestBase target, ConnectorRequest<?> request, List<ConnectorRequestInterceptor> interceptorChain, HttpClient client) {
    super(target, request, interceptorChain);
    this.client = client;
  }

  public Object invokeTarget() throws Exception {
    // execute the request
    return client.execute(target);
  }

}
