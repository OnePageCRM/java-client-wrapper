package com.onepagecrm.models.internal;

import com.onepagecrm.models.Deal;

import java.io.Serializable;
import java.util.List;

public class DealStage implements Serializable {

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
        return new DealStage().setLabel(Deal.STATUS_WON);
    }

    public static DealStage lost() {
        return new DealStage().setLabel(Deal.STATUS_LOST);
    }

    public String getDisplayText() {
        if (percentage != null) {
            if (label == null || label.equalsIgnoreCase("null")) {
                return percentage + "%";
            } else {
                return percentage + "%" + " - " + Utilities.capitalize(label);
            }
        } else {
            return Utilities.capitalize(label);
        }
    }

    public String getUniqueIdentifier() {
        return percentage == null ? label : String.valueOf(percentage);
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

    @Override
    public String toString() {
        return getDisplayText();
    }

    public String getStatus() {
        if (getUniqueIdentifier().equalsIgnoreCase(Deal.STATUS_WON)) {
            return Deal.STATUS_WON.toLowerCase();
        } else if (getUniqueIdentifier().equalsIgnoreCase(Deal.STATUS_LOST)) {
            return Deal.STATUS_LOST.toLowerCase();
        } else {
            return Deal.STATUS_PENDING.toLowerCase();
        }
    }

    public Integer getStage() {
        return getPercentage();
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