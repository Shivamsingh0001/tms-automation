package org.delhivery.config;

import org.delhivery.utils.ConfigManager;

/**
 * Authentication configuration reader.
 * Reads auth properties from the environment-specific config file.
 */
public final class AuthConfig {

    public static final String KEY_TENANT_ID = "auth.tenant.id";
    public static final String KEY_CLIENT_ID = "auth.client.id";
    public static final String KEY_CLIENT_SECRET = "auth.client.secret";
    public static final String KEY_AUDIENCE = "auth.audience";
    public static final String KEY_APP_ID = "auth.app.id";

    private static final String DEFAULT_AUDIENCE = "platform:app:tms";

    private AuthConfig() {}

    public static String getTenantId() {
        return ConfigManager.get(KEY_TENANT_ID, "");
    }

    public static String getClientId() {
        return ConfigManager.get(KEY_CLIENT_ID, "");
    }

    public static String getClientSecret() {
        return ConfigManager.get(KEY_CLIENT_SECRET, "");
    }

    public static String getAudience() {
        return ConfigManager.get(KEY_AUDIENCE, DEFAULT_AUDIENCE);
    }

    public static String getAppId() {
        return ConfigManager.get(KEY_APP_ID, "");
    }

    public static String getEnvironment() {
        return ConfigManager.getEnvironment();
    }
}
