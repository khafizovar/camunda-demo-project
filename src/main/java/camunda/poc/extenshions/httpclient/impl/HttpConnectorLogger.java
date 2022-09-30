package camunda.poc.extenshions.httpclient.impl;

import org.camunda.connect.ConnectorRequestException;
import org.camunda.connect.ConnectorResponseException;
import org.camunda.connect.impl.ConnectLogger;

public class HttpConnectorLogger extends ConnectLogger {

  public void setHeader(String field, String value) {
    logDebug("001", "Set header field '{}' to '{}'", field, value);
  }

  public void ignoreHeader(String field, String value) {
    logInfo("002", "Ignoring header with name '{}' and value '{}'", field, value);

  }

  public void payloadIgnoredForHttpMethod(String method) {
    logInfo("003", "Ignoring payload for HTTP '{}' method", method);
  }

  public ConnectorResponseException unableToReadResponse(Exception cause) {
    return new ConnectorResponseException(exceptionMessage("004", "Unable to read connectorResponse: {}", cause.getMessage()), cause);
  }

  public ConnectorRequestException requestUrlRequired() {
    return new ConnectorRequestException(exceptionMessage("005", "Request url required."));
  }

  public ConnectorRequestException unknownHttpMethod(String method) {
    return new ConnectorRequestException(exceptionMessage("006", "Unknown or unsupported HTTP method '{}'", method));
  }

  public ConnectorRequestException unableToExecuteRequest(Exception cause) {
    return new ConnectorRequestException(exceptionMessage("007", "Unable to execute HTTP request"), cause);
  }

  public ConnectorRequestException invalidConfigurationOption(String optionName, Exception cause) {
    return new ConnectorRequestException(exceptionMessage("008", "Invalid value for request configuration option: {}", optionName), cause);
  }

  public void ignoreConfig(String field, Object value) {
    logInfo("009", "Ignoring request configuration option with name '{}' and value '{}'", field, value);
  }

}
