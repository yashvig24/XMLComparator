package model;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class XMLTag implements Comparable<XMLTag> {

    private String tag;
    private String tagType;
    private String tagText;

    public XMLTag(String tag) {
        this.tag = tag;
        this.tagType = extractTagType(tag);
    }
    
    private String extractTagType(String tag) {
        String tagPattern = "([a-zA-z]+)";
        Pattern pattern = Pattern.compile(tagPattern);
        Matcher matcher = pattern.matcher(tag);
        if(matcher.find()) {
            this.tagText = matcher.group(1);
            String[] allWords = this.tagText.split("\\s");
            return allWords[0];
        }
        else
            return "";
    }

    public XMLTag getOpeningTag() {
        return new XMLTag("<" + this.tagText + ">");
    }

    public String getTagType() {
        return this.tagType;
    }

    public boolean isClosingTag() {
        return this.tag.charAt(1) == '/';
    }

    public boolean isOpeningTag() {
        return !isClosingTag();
    }

    public boolean isSelfClosingTag() {
        return this.tag.charAt(this.tag.length() - 2) == '/';
    }

    public boolean isOppositeTag(XMLTag other) {
        return (other.equals(getOppositeTag()));
    }

    public XMLTag getOppositeTag() {
        if(isClosingTag()) {
            return new XMLTag("<" + getTagType() + ">");
        }
        else {
            return new XMLTag("</" + getTagType() + ">");
        }
    }

    public boolean equals(Object other) {
        if(other instanceof XMLTag) {
            XMLTag o = (XMLTag) other;
            return o.tagType.equals(this.tagType);
        }
        return false;
    }

    public int hashCode() {
        return tagType.hashCode();
    }

    public String toString() {
        return this.tag;
    }

    public int compareTo(XMLTag other) {
        return this.tag.compareTo(other.tag);
    }
}