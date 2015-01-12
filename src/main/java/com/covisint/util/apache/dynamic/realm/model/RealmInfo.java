package com.covisint.util.apache.dynamic.realm.model;

import static com.covisint.util.apache.dynamic.util.Objects.equal;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covisint.util.apache.dynamic.util.NotEqualException;
import com.google.common.base.Objects;

public class RealmInfo implements Serializable, Comparable<RealmInfo> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1812848567492174228L;

    private String alias;
    private String apiManagerHostname;
    private List<Attributes> attributes;
    private Map<String, String> attributeMap;
    private String authenticatorHostname;
    private Number creation;
    private String creator;
    private String creatorAppId;
    private String domain;
    private String fstHostname;
    private String id;
    private String identityBrokerHostname;
    private Number initialCreation;
    private String name;
    private String portalHostname;
    private String provisionerHostname;
    private Realm realm;
    private String realmId;
    private String runtimeHostname;
    private String scManagerHostname;
    private String solutionId;
    private String solutionRealmId;
    private Number version;

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getApiManagerHostname() {
        return this.apiManagerHostname;
    }

    public void setApiManagerHostname(String apiManagerHostname) {
        this.apiManagerHostname = apiManagerHostname;
    }

    public List<Attributes> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<Attributes> attributes) {
        this.attributes = attributes;
    }

    public String getAuthenticatorHostname() {
        return this.authenticatorHostname;
    }

    public void setAuthenticatorHostname(String authenticatorHostname) {
        this.authenticatorHostname = authenticatorHostname;
    }

    public Number getCreation() {
        return this.creation;
    }

    public void setCreation(Number creation) {
        this.creation = creation;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorAppId() {
        return this.creatorAppId;
    }

    public void setCreatorAppId(String creatorAppId) {
        this.creatorAppId = creatorAppId;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getFstHostname() {
        return this.fstHostname;
    }

    public void setFstHostname(String fstHostname) {
        this.fstHostname = fstHostname;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentityBrokerHostname() {
        return this.identityBrokerHostname;
    }

    public void setIdentityBrokerHostname(String identityBrokerHostname) {
        this.identityBrokerHostname = identityBrokerHostname;
    }

    public Number getInitialCreation() {
        return this.initialCreation;
    }

    public void setInitialCreation(Number initialCreation) {
        this.initialCreation = initialCreation;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortalHostname() {
        return this.portalHostname;
    }

    public void setPortalHostname(String portalHostname) {
        this.portalHostname = portalHostname;
    }

    public String getProvisionerHostname() {
        return this.provisionerHostname;
    }

    public void setProvisionerHostname(String provisionerHostname) {
        this.provisionerHostname = provisionerHostname;
    }

    public Realm getRealm() {
        return this.realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public String getRealmId() {
        return this.realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public String getRuntimeHostname() {
        return this.runtimeHostname;
    }

    public void setRuntimeHostname(String runtimeHostname) {
        this.runtimeHostname = runtimeHostname;
    }

    public String getScManagerHostname() {
        return this.scManagerHostname;
    }

    public void setScManagerHostname(String scManagerHostname) {
        this.scManagerHostname = scManagerHostname;
    }

    public String getSolutionId() {
        return this.solutionId;
    }

    public void setSolutionId(String solutionId) {
        this.solutionId = solutionId;
    }

    public String getSolutionRealmId() {
        return this.solutionRealmId;
    }

    public void setSolutionRealmId(String solutionRealmId) {
        this.solutionRealmId = solutionRealmId;
    }

    public Number getVersion() {
        return this.version;
    }

    public void setVersion(Number version) {
        this.version = version;
    }

    /**
     * @return the attributeMap
     */
    public Map<String, String> getAttributeMap() {
        return attributeMap;
    }

    /**
     * @param attributeMap
     *            the attributeMap to set
     */
    public void setAttributeMap(Map<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    /**
     * Checking to make sure that the RealmInfo Id and versions match
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof RealmInfo)) {
            return false;
        }

        RealmInfo other = (RealmInfo) obj;

        try {
            equal(this.id, other.id);
            equal(this.version, other.version);
        } catch (NotEqualException nee) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.version);
    }

    @Override
    public int compareTo(RealmInfo o) {

        int idCompare = id.compareTo(o.id);

        return 0 == idCompare ? ((int) (version.longValue() - o.version.longValue())) : idCompare;
    }
}
