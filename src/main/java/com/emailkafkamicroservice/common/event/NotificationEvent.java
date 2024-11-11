package com.emailkafkamicroservice.common.event;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class NotificationEvent {

    private String receiverEmail;

}
