package org.delhivery.constants;

/**
 * TMS API endpoint constants.
 * Add your TMS-specific endpoints here.
 */
public final class ApiEndpoints {

    private ApiEndpoints() {}

    // Base paths
    public static final String TMS_BASE = "/api/v1";

    // Trip Management
    public static final String TRIPS = TMS_BASE + "/trips";
    public static final String TRIP_BY_ID = TRIPS + "/{tripId}";

    // Vehicle Management
    public static final String VEHICLES = TMS_BASE + "/vehicles";
    public static final String VEHICLE_BY_ID = VEHICLES + "/{vehicleId}";

    // Route Management
    public static final String ROUTES = TMS_BASE + "/routes";
    public static final String ROUTE_BY_ID = ROUTES + "/{routeId}";

    // Driver Management
    public static final String DRIVERS = TMS_BASE + "/drivers";
    public static final String DRIVER_BY_ID = DRIVERS + "/{driverId}";

    // Health
    public static final String HEALTH = "/health";

    // Helper methods
    public static String tripById(String tripId) {
        return TRIP_BY_ID.replace("{tripId}", tripId);
    }

    public static String vehicleById(String vehicleId) {
        return VEHICLE_BY_ID.replace("{vehicleId}", vehicleId);
    }

    public static String routeById(String routeId) {
        return ROUTE_BY_ID.replace("{routeId}", routeId);
    }

    public static String driverById(String driverId) {
        return DRIVER_BY_ID.replace("{driverId}", driverId);
    }
}
