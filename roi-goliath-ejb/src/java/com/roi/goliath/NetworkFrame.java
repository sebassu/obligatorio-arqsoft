package com.roi.goliath;

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

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }
}
