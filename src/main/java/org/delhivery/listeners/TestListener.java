package org.delhivery.listeners;

import io.qameta.allure.Allure;
import org.delhivery.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestListener implements ITestListener {

    private final ConcurrentHashMap<String, Instant> testStartTimes = new ConcurrentHashMap<>();
    private final AtomicInteger passed = new AtomicInteger(0);
    private final AtomicInteger failed = new AtomicInteger(0);
    private final AtomicInteger skipped = new AtomicInteger(0);

    @Override
    public void onStart(ITestContext context) {
        LogUtils.section("Test Suite: " + context.getName());
        LogUtils.info("Starting test suite with {} test methods", context.getAllTestMethods().length);
    }

    @Override
    public void onFinish(ITestContext context) {
        LogUtils.separator();
        LogUtils.section("Test Suite Complete: " + context.getName());
        LogUtils.info("Results: {} passed, {} failed, {} skipped", passed.get(), failed.get(), skipped.get());
        long duration = context.getEndDate().getTime() - context.getStartDate().getTime();
        LogUtils.info("Total duration: {}s", duration / 1000.0);

        Allure.addAttachment("Suite Summary", "text/plain",
                String.format("Suite: %s%nPassed: %d%nFailed: %d%nSkipped: %d%nDuration: %.2fs",
                        context.getName(), passed.get(), failed.get(), skipped.get(), duration / 1000.0));
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = getTestName(result);
        testStartTimes.put(testName, Instant.now());
        LogUtils.testStart(testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = getTestName(result);
        long duration = getTestDuration(testName);
        passed.incrementAndGet();
        LogUtils.testPass(testName);
        LogUtils.info("Duration: {}ms", duration);
        Allure.addAttachment("Test Duration", "text/plain", duration + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = getTestName(result);
        long duration = getTestDuration(testName);
        failed.incrementAndGet();

        Throwable throwable = result.getThrowable();
        String errorMessage = throwable != null ? throwable.getMessage() : "Unknown error";
        LogUtils.testFail(testName, errorMessage);
        LogUtils.info("Duration: {}ms", duration);

        if (throwable != null) {
            StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            Allure.addAttachment("Stack Trace", "text/plain", sw.toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipped.incrementAndGet();
        String reason = result.getThrowable() != null ? result.getThrowable().getMessage() : "No reason";
        LogUtils.testSkip(getTestName(result), reason);
        Allure.addAttachment("Skip Reason", "text/plain", reason);
    }

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
    }

    private long getTestDuration(String testName) {
        Instant start = testStartTimes.remove(testName);
        return start != null ? Duration.between(start, Instant.now()).toMillis() : 0;
    }
}
