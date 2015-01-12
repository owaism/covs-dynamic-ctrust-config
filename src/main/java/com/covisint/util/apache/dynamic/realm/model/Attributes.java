package com.covisint.util.apache.dynamic.realm.model;

import java.io.Serializable;

import com.google.common.base.Objects;

public class Attributes implements Serializable, Comparable<Attributes> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 9207430126376626923L;

    private String key;
    private String value;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Attributes)) {
            return false;
        }

        Attributes otherAttrbute = (Attributes) obj;

        return Objects.equal(this.key, otherAttrbute.key) && Objects.equal(value, otherAttrbute.value);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.key, this.value);
    }

    @Override
    public int compareTo(Attributes o) {
        int keyCompare = key.compareTo(o.key);
        return 0 == keyCompare ? value.compareTo(o.value) : keyCompare;
    }
}
