package com.onepagecrm.models.internal;

import java.io.Serializable;
import java.util.List;

public class DealStage implements Serializable {

    public static final String STATUS_WON = "Won";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_PENDING = "Pending";

    private Integer percentage;
    private String label;

    public DealStage() {

    }

    public static List<DealStage> addDefaults(List<DealStage> stages) {
        if (stages != null) {
            stages.add(won());
            stages.add(lost());
        }
        return stages;
    }

    public static DealStage won() {
        return new DealStage().setLabel(STATUS_WON);
    }

    public static DealStage lost() {
        return new DealStage().setLabel(STATUS_LOST);
    }

    public String getDisplayText() {
        if (percentage != null) {
            return percentage + "%" + ((label == null) ? "" : " - " + label);
        } else {
            return label;
        }
    }

    public String getUniqueIdentifier() {
        return percentage == null ? label : String.valueOf(percentage);
    }

    public String getStatus() {
        if (getUniqueIdentifier().equalsIgnoreCase(STATUS_WON)) {
            return STATUS_WON.toLowerCase();
        } else if (getUniqueIdentifier().equalsIgnoreCase(STATUS_LOST)) {
            return STATUS_LOST.toLowerCase();
        } else {
            return STATUS_PENDING.toLowerCase();
        }
    }

    public Integer getStage() {
        return getPercentage();
    }

    public boolean equals(Object object) {
        if (object instanceof DealStage) {
            DealStage toCompare = (DealStage) object;
            if (this.getUniqueIdentifier() != null && toCompare.getUniqueIdentifier() != null) {
                return this.getUniqueIdentifier().equals(toCompare.getUniqueIdentifier());
            }
        }
        return false;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public DealStage setPercentage(Integer percentage) {
        this.percentage = percentage;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public DealStage setLabel(String label) {
        this.label = label;
        return this;
    }
}