package org.delhivery.data;

import java.util.UUID;

public final class TestData {

    private TestData() {}

    // Common statuses
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_PENDING = "PENDING";

    // Invalid data for negative tests
    public static final String INVALID_TOKEN = "invalid-token-12345";
    public static final String NONEXISTENT_ID = "nonexistent-" + System.currentTimeMillis();
    public static final String EMPTY_STRING = "";

    public static String uniqueId() {
        return UUID.randomUUID().toString();
    }

    public static String uniqueName(String prefix) {
        return prefix + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String nonexistentId() {
        return "nonexistent-" + System.currentTimeMillis();
    }
}
