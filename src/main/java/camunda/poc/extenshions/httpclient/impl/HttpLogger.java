package camunda.poc.extenshions.httpclient.impl;

import org.camunda.commons.logging.BaseLogger;

public abstract class HttpLogger extends BaseLogger {

  public static final String PROJECT_CODE = "HTCL";

  public static HttpConnectorLogger HTTP_LOGGER = createLogger(HttpConnectorLogger.class, PROJECT_CODE, "org.camunda.bpm.connect.httpclient.connector", "02");
}
