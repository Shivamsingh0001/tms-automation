package org.delhivery.models.tms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sample POJO for TMS Trip requests.
 * Replace/extend with your actual TMS API request models.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {
    private String origin;
    private String destination;
    private String vehicleId;
    private String driverId;
    private String scheduledDate;
}
