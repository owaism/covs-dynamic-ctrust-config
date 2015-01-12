package com.covisint.util.apache.dynamic.realm.model;

import static com.covisint.util.apache.dynamic.util.Objects.equal;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covisint.util.apache.dynamic.util.NotEqualException;

public class Realm implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6707261918053067414L;

    private List<Attributes> attributes;
    private Map<String, String> attributeMap;
    private Number creation;
    private String creator;
    private String creatorAppId;
    private String id;
    private List<Name> name;
    private Number version;

    public List<Attributes> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<Attributes> attributes) {
        this.attributes = attributes;
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Name> getName() {
        return this.name;
    }

    public void setName(List<Name> name) {
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Realm)) {
            return false;
        }

        Realm otherRealm = (Realm) obj;
        try {
            equal(attributes, otherRealm.attributes);
            equal(creation, otherRealm.creation);
            equal(creator, otherRealm.creator);
            equal(id, otherRealm.id);
            equal(creatorAppId, otherRealm.creatorAppId);
            equal(name, otherRealm.name);
            equal(version, otherRealm.version);
        } catch (NotEqualException nee) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(this.attributes, this.creation, this.creator, this.id,
                this.creatorAppId, this.name, this.version);
    }
}
