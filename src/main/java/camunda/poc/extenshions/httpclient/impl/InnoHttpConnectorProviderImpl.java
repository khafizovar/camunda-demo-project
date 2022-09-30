package camunda.poc.extenshions.httpclient.impl;

import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpConnectorProvider;

import static camunda.poc.extenshions.httpclient.impl.ConstParams.CONNECTOR_ID;

public class InnoHttpConnectorProviderImpl implements HttpConnectorProvider {

  public String getConnectorId() {
    return CONNECTOR_ID;
  }

  public HttpConnector createConnectorInstance() {
    return new InnoHttpConnectorImpl(CONNECTOR_ID);
  }

}
