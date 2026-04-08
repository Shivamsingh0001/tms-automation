package org.delhivery.api.tms;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.delhivery.assertions.ApiAssertions;
import org.delhivery.base.BaseApiTest;
import org.delhivery.constants.ApiEndpoints;
import org.delhivery.utils.LogUtils;
import org.testng.annotations.Test;

/**
 * Sample TMS test to verify the framework is wired up correctly.
 * Replace with your actual TMS API tests.
 */
@Feature("TMS - Sample")
public class SampleTmsTest extends BaseApiTest {

    @Test(groups = {"smoke", "sanity"})
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify TMS health endpoint returns 200")
    public void healthCheck_ShouldReturn200() {
        LogUtils.step("Calling TMS health endpoint");

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(ApiEndpoints.HEALTH);

        ApiAssertions.assertThat(response)
                .hasStatusCode(200)
                .validate();

        LogUtils.success("Health check passed");
    }

    @Test(groups = {"smoke"})
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify GET /trips returns a list")
    public void getTrips_ShouldReturnList() {
        LogUtils.step("Fetching all trips");

        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(ApiEndpoints.TRIPS);

        ApiAssertions.assertThat(response)
                .isSuccess()
                .validate();

        LogUtils.success("Trips endpoint responded successfully");
    }
}
