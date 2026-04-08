package org.delhivery.constants;

public final class HttpHeaders {

    private HttpHeaders() {}

    // Standard
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";

    // CoreOS / TMS specific headers
    public static final String X_COREOS_ACCESS = "X-COREOS-ACCESS";
    public static final String X_COREOS_REQUEST_ID = "X-COREOS-REQUEST-ID";
    public static final String X_COREOS_USER_INFO = "X-COREOS-USER-INFO";
    public static final String X_COREOS_TID = "X-COREOS-TID";
    public static final String X_COREOS_APPID = "X-COREOS-APPID";
    public static final String X_COREOS_APP_ID = "X-COREOS-APP-ID";
    public static final String X_COREOS_ORIGIN_TOKEN = "X-COREOS-ORIGIN-TOKEN";

    // Content type values
    public static final String APPLICATION_JSON = "application/json";
}
