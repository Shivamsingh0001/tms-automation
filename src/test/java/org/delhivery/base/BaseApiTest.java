package org.delhivery.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.delhivery.config.AuthConfig;
import org.delhivery.constants.HttpHeaders;
import org.delhivery.filters.ApiCaptureFilter;
import org.delhivery.filters.MetricsFilter;
import org.delhivery.utils.ConfigManager;
import org.delhivery.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.UUID;

/**
 * Base test class for all TMS API tests.
 * Handles config loading, REST Assured setup, and auth headers.
 * Extend this in your test classes.
 */
public class BaseApiTest {

    @BeforeSuite
    public void beforeSuite(ITestContext context) {
        ConfigManager.initialize();

        RestAssured.baseURI = ConfigManager.getBaseUrl();
        LogUtils.info("Environment: {}", ConfigManager.getEnvironment());
        LogUtils.info("Base URL: {}", RestAssured.baseURI);

        RestAssured.filters(
                new AllureRestAssured(),
                new MetricsFilter(),
                new ApiCaptureFilter(),
                new RequestLoggingFilter(LogDetail.ALL, true, System.out),
                new ResponseLoggingFilter(LogDetail.ALL, true, System.out)
        );
    }

    @AfterSuite
    public void afterSuite() {
        LogUtils.info("Test suite completed.");
    }

    /**
     * Creates a pre-configured RequestSpecification with all required TMS/CoreOS headers.
     */
    public RequestSpecification given() {
        return RestAssured.given()
                .baseUri(ConfigManager.getBaseUrl())
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaders.APPLICATION_JSON)
                .header(HttpHeaders.X_COREOS_REQUEST_ID, UUID.randomUUID().toString())
                .header(HttpHeaders.X_COREOS_TID, AuthConfig.getTenantId())
                .header(HttpHeaders.X_COREOS_APPID, AuthConfig.getAppId())
                .header(HttpHeaders.X_COREOS_APP_ID, AuthConfig.getAppId());
    }

    /**
     * Creates a RequestSpecification with auth token.
     * Override getToken() to provide your auth mechanism.
     */
    public RequestSpecification givenWithAuth(String token) {
        return given()
                .header(HttpHeaders.X_COREOS_ACCESS, token)
                .header(HttpHeaders.X_COREOS_ORIGIN_TOKEN, token)
                .header(HttpHeaders.X_COREOS_USER_INFO, AuthConfig.getClientId());
    }

    public static String baseURI() {
        return ConfigManager.getBaseUrl();
    }

    public static String getEnvironment() {
        return ConfigManager.getEnvironment();
    }
}
