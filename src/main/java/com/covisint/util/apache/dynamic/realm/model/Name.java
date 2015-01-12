package com.covisint.util.apache.dynamic.realm.model;

import java.io.Serializable;

import com.google.common.base.Objects;

public class Name implements Serializable, Comparable<Name> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4238114567152775520L;

    private String lang;
    private String text;

    /**
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang
     *            the lang to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Name)) {
            return false;
        }

        Name otherName = (Name) obj;

        return Objects.equal(this.lang, otherName.lang) && Objects.equal(text, otherName.text);

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.lang, this.text);
    }

    @Override
    public int compareTo(Name o) {
        int textCompare = text.compareTo(o.text);
        return 0 == textCompare ? lang.compareTo(o.lang) : textCompare;
    }

}
