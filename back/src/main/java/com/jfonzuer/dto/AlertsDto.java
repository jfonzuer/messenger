package com.jfonzuer.dto;

/**
 * Created by pgm on 01/12/16.
 */
public class AlertsDto {
    private boolean notifyVisit;
    private boolean notifyMessage;

    public AlertsDto() {
    }

    public boolean isNotifyVisit() {
        return notifyVisit;
    }

    public boolean isNotifyMessage() {
        return notifyMessage;
    }
}
