package com.roi.planner;

public class NetworkFrame {
    private long actuatorId;
    private long sectionId;
    private long sourceId;

    public long getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(long actuatorId) {
        this.actuatorId = actuatorId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }
}
