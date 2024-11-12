package com.emailkafkamicroservice.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageEvent {

    private Integer trackingNumber;

    private Boolean isEligible;

}
