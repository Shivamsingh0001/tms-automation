package org.delhivery.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.delhivery.utils.LogUtils;

/**
 * REST Assured filter that captures API call details for reporting.
 */
public class ApiCaptureFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);

        if (response.getStatusCode() >= 400) {
            LogUtils.warn("API call failed: {} {} => {}",
                    requestSpec.getMethod(), requestSpec.getURI(), response.getStatusCode());
        }

        return response;
    }
}
