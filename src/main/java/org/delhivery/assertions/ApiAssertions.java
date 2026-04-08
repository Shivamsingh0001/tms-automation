package org.delhivery.assertions;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.delhivery.utils.LogUtils;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Fluent assertions for REST API response validation with Allure reporting.
 *
 * Usage:
 *   ApiAssertions.assertThat(response)
 *       .hasStatusCode(200)
 *       .hasJsonPath("data.id")
 *       .jsonPathEquals("data.status", "ACTIVE")
 *       .validate();
 */
public class ApiAssertions {

    private final Response response;
    private final SoftAssert softAssert;
    private final List<String> validations = new ArrayList<>();
    private boolean useSoftAssert = false;

    private ApiAssertions(Response response) {
        this.response = response;
        this.softAssert = new SoftAssert();
    }

    public static ApiAssertions assertThat(Response response) {
        return new ApiAssertions(response);
    }

    public ApiAssertions soft() {
        this.useSoftAssert = true;
        return this;
    }

    @Step("Verify status code is {expected}")
    public ApiAssertions hasStatusCode(int expected) {
        int actual = response.getStatusCode();
        String msg = String.format("Status code should be %d", expected);
        if (useSoftAssert) {
            softAssert.assertEquals(actual, expected, msg);
        } else {
            Assert.assertEquals(actual, expected, msg + "\nResponse: " + response.asPrettyString());
        }
        validations.add("Status code: " + actual);
        return this;
    }

    @Step("Verify status code is 2xx")
    public ApiAssertions isSuccess() {
        int actual = response.getStatusCode();
        boolean ok = actual >= 200 && actual < 300;
        if (useSoftAssert) {
            softAssert.assertTrue(ok, "Expected 2xx but got " + actual);
        } else {
            Assert.assertTrue(ok, "Expected 2xx but got " + actual + "\nResponse: " + response.asPrettyString());
        }
        validations.add("Success status: " + actual);
        return this;
    }

    @Step("Verify JSON path '{jsonPath}' exists")
    public ApiAssertions hasJsonPath(String jsonPath) {
        Object value = response.jsonPath().get(jsonPath);
        String msg = "JSON path '" + jsonPath + "' should exist";
        if (useSoftAssert) {
            softAssert.assertNotNull(value, msg);
        } else {
            Assert.assertNotNull(value, msg + "\nResponse: " + response.asPrettyString());
        }
        validations.add("Has path: " + jsonPath);
        return this;
    }

    @Step("Verify JSON path '{jsonPath}' equals '{expected}'")
    public ApiAssertions jsonPathEquals(String jsonPath, Object expected) {
        Object actual = response.jsonPath().get(jsonPath);
        String msg = String.format("JSON path '%s' should equal '%s'", jsonPath, expected);
        if (useSoftAssert) {
            softAssert.assertEquals(actual, expected, msg);
        } else {
            Assert.assertEquals(actual, expected, msg + "\nActual: " + actual);
        }
        validations.add(jsonPath + " = " + expected);
        return this;
    }

    @Step("Verify JSON path '{jsonPath}' contains '{substring}'")
    public ApiAssertions jsonPathContains(String jsonPath, String substring) {
        String actual = response.jsonPath().getString(jsonPath);
        boolean contains = actual != null && actual.contains(substring);
        if (useSoftAssert) {
            softAssert.assertTrue(contains, jsonPath + " should contain " + substring);
        } else {
            Assert.assertTrue(contains, jsonPath + " should contain " + substring + "\nActual: " + actual);
        }
        validations.add(jsonPath + " contains: " + substring);
        return this;
    }

    @Step("Verify JSON array at '{jsonPath}' is not empty")
    public ApiAssertions jsonArrayNotEmpty(String jsonPath) {
        List<?> list = response.jsonPath().getList(jsonPath);
        boolean notEmpty = list != null && !list.isEmpty();
        if (useSoftAssert) {
            softAssert.assertTrue(notEmpty, "Array at '" + jsonPath + "' should not be empty");
        } else {
            Assert.assertTrue(notEmpty, "Array at '" + jsonPath + "' should not be empty");
        }
        validations.add("Array not empty: " + jsonPath);
        return this;
    }

    @Step("Verify response time < {maxMillis}ms")
    public ApiAssertions responseTimeLessThan(long maxMillis) {
        long actual = response.getTime();
        if (useSoftAssert) {
            softAssert.assertTrue(actual < maxMillis, "Response time " + actual + "ms should be < " + maxMillis + "ms");
        } else {
            Assert.assertTrue(actual < maxMillis, "Response time " + actual + "ms should be < " + maxMillis + "ms");
        }
        validations.add("Response time: " + actual + "ms");
        return this;
    }

    @Step("Verify multiple JSON fields")
    public ApiAssertions jsonPathsEqual(Map<String, Object> expectations) {
        expectations.forEach(this::jsonPathEquals);
        return this;
    }

    @Step("Custom validation")
    public ApiAssertions satisfies(Consumer<Response> validator) {
        validator.accept(response);
        validations.add("Custom validation passed");
        return this;
    }

    public void validate() {
        attachReport();
        if (useSoftAssert) softAssert.assertAll();
    }

    public Response andReturn() {
        attachReport();
        if (useSoftAssert) softAssert.assertAll();
        return response;
    }

    private void attachReport() {
        if (!validations.isEmpty()) {
            String report = String.join("\n", validations);
            Allure.addAttachment("Validations", "text/plain", report);
            LogUtils.debug("Validations:\n{}", report);
        }
    }
}
