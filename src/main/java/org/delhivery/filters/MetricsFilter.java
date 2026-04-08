package org.delhivery.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.delhivery.utils.LogUtils;

/**
 * REST Assured filter that tracks API response time metrics.
 */
public class MetricsFilter implements Filter {

    private static final long SLOW_THRESHOLD_DEFAULT = 2000;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                          FilterableResponseSpecification responseSpec,
                          FilterContext ctx) {
        long start = System.currentTimeMillis();
        Response response = ctx.next(requestSpec, responseSpec);
        long duration = System.currentTimeMillis() - start;

        if (duration > SLOW_THRESHOLD_DEFAULT) {
            LogUtils.warn("SLOW API: {} {} took {}ms", requestSpec.getMethod(), requestSpec.getURI(), duration);
        }

        return response;
    }
}
