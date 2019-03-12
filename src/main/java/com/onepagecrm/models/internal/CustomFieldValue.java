package com.onepagecrm.models.internal;

import com.onepagecrm.models.serializers.BaseSerializer;
import com.onepagecrm.models.serializers.CustomFieldValueSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class CustomFieldValue implements Serializable {

    private String stringValue;
    private Long longValue;
    private BigDecimal floatingValue;
    private String[] stringArrayValue;

    public Serializable getValue() {
        if (stringValue != null) {
            return this.stringValue;
        } else if (longValue != null) {
            return this.longValue;
        } else if (floatingValue != null) {
            return this.floatingValue;
        } else if (stringArrayValue != null) {
            return this.stringArrayValue;
        }
        return null;
    }

    @SuppressWarnings("unused")
    public String getStringValue() {
        String retString = "";
        if (stringValue != null) {
            retString = this.stringValue;
        } else if (longValue != null) {
            retString = String.valueOf(this.longValue);
        } else if (floatingValue != null) {
            retString = String.valueOf(this.floatingValue);
        } else if (stringArrayValue != null) {
            retString = BaseSerializer.toCommaSeparatedString(this.stringArrayValue);
        }
        return retString;
    }

    public Serializable setValue(Object object) {
        if (object instanceof String) {
            this.setValue((String) object);
        }
        if (object instanceof Long) {
            this.setValue((Long) object);
        }
        if (object instanceof BigDecimal) {
            this.setValue((BigDecimal) object);
        }
        if (object instanceof String[]) {
            this.setValue((String[]) object);
        }
        return this;
    }

    @Override
    public String toString() {
        return CustomFieldValueSerializer.toJsonObject(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomFieldValue)) return false;
        CustomFieldValue that = (CustomFieldValue) o;
        return Objects.equals(stringValue, that.stringValue) &&
                Objects.equals(longValue, that.longValue) &&
                Objects.equals(floatingValue, that.floatingValue) &&
                Arrays.equals(stringArrayValue, that.stringArrayValue);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(stringValue, longValue, floatingValue);
        result = 31 * result + Arrays.hashCode(stringArrayValue);
        return result;
    }

    public Serializable setValue(String stringValue) {
        this.stringValue = stringValue;
        this.longValue = null;
        this.floatingValue = null;
        this.stringArrayValue = null;
        return getValue();
    }

    public Serializable setValue(Long longValue) {
        this.stringValue = null;
        this.longValue = longValue;
        this.floatingValue = null;
        this.stringArrayValue = null;
        return getValue();
    }

    public Serializable setValue(BigDecimal floatingValue) {
        this.stringValue = null;
        this.longValue = null;
        this.floatingValue = floatingValue;
        this.stringArrayValue = null;
        return getValue();
    }

    public Serializable setValue(String[] stringArrayValue) {
        this.stringValue = null;
        this.longValue = null;
        this.floatingValue = null;
        this.stringArrayValue = stringArrayValue;
        return getValue();
    }
}
