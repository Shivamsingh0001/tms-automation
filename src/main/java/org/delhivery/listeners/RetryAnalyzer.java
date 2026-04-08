package org.delhivery.listeners;

import org.delhivery.utils.ConfigManager;
import org.delhivery.utils.LogUtils;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final int DEFAULT_MAX_RETRY = 2;
    private static final ConcurrentHashMap<String, AtomicInteger> retryCounters = new ConcurrentHashMap<>();

    @Override
    public boolean retry(ITestResult result) {
        if (!isRetryEnabled()) return false;

        int maxRetry = getMaxRetryCount();
        String testKey = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
        AtomicInteger counter = retryCounters.computeIfAbsent(testKey, k -> new AtomicInteger(0));
        int current = counter.incrementAndGet();

        if (current <= maxRetry) {
            LogUtils.warn("Retrying test: {} (attempt {}/{})", result.getMethod().getMethodName(), current, maxRetry);
            return true;
        }

        LogUtils.error("Test failed after {} retries: {}", maxRetry, result.getMethod().getMethodName());
        retryCounters.remove(testKey);
        return false;
    }

    private boolean isRetryEnabled() {
        return Boolean.parseBoolean(ConfigManager.get("test.retry.enabled", "true"));
    }

    private int getMaxRetryCount() {
        return ConfigManager.getInt("test.retry.count", DEFAULT_MAX_RETRY);
    }

    public static void resetCounters() {
        retryCounters.clear();
    }
}
