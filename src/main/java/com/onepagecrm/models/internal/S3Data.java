package com.onepagecrm.models.internal;

import com.onepagecrm.models.serializers.S3DataSerializer;

import java.io.Serializable;

/**
 * @author Cillian Myles <cillian@onepagecrm.com> on 27/09/2017.
 */
public class S3Data implements Serializable {

    private Long quota;
    private Long remaining;
    private String displayQuota;
    private String url;
    private String key;
    private Integer successStatus;
    private String acl;
    private String policy;
    private String xIgnorePattern;
    private String xAmzAlgorithm;
    private String xAmzCredential;
    private String xAmzDate;
    private String xAmzSignature;

    public S3Data() {

    }

    @Override
    public String toString() {
        return S3DataSerializer.toJsonString(this);
    }

    public boolean isValid() {
        return Utilities.notNullOrEmpty(url);
    }

    public Long getQuota() {
        return quota;
    }

    public S3Data setQuota(Long quota) {
        this.quota = quota;
        return this;
    }

    public Long getRemaining() {
        return remaining;
    }

    public S3Data setRemaining(Long remaining) {
        this.remaining = remaining;
        return this;
    }

    public String getDisplayQuota() {
        return displayQuota;
    }

    public S3Data setDisplayQuota(String displayQuota) {
        this.displayQuota = displayQuota;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public S3Data setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getKey() {
        return key;
    }

    public S3Data setKey(String key) {
        this.key = key;
        return this;
    }

    public Integer getSuccessStatus() {
        return successStatus;
    }

    public S3Data setSuccessStatus(Integer successStatus) {
        this.successStatus = successStatus;
        return this;
    }

    public String getAcl() {
        return acl;
    }

    public S3Data setAcl(String acl) {
        this.acl = acl;
        return this;
    }

    public String getPolicy() {
        return policy;
    }

    public S3Data setPolicy(String policy) {
        this.policy = policy;
        return this;
    }

    public String getxIgnorePattern() {
        return xIgnorePattern;
    }

    public S3Data setxIgnorePattern(String xIgnorePattern) {
        this.xIgnorePattern = xIgnorePattern;
        return this;
    }

    public String getxAmzAlgorithm() {
        return xAmzAlgorithm;
    }

    public S3Data setxAmzAlgorithm(String xAmzAlgorithm) {
        this.xAmzAlgorithm = xAmzAlgorithm;
        return this;
    }

    public String getxAmzCredential() {
        return xAmzCredential;
    }

    public S3Data setxAmzCredential(String xAmzCredential) {
        this.xAmzCredential = xAmzCredential;
        return this;
    }

    public String getxAmzDate() {
        return xAmzDate;
    }

    public S3Data setxAmzDate(String xAmzDate) {
        this.xAmzDate = xAmzDate;
        return this;
    }

    public String getxAmzSignature() {
        return xAmzSignature;
    }

    public S3Data setxAmzSignature(String xAmzSignature) {
        this.xAmzSignature = xAmzSignature;
        return this;
    }
}
