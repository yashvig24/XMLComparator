package xml;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class XMLTag implements Comparable<XMLTag> {

    private String tag;

    public XMLTag(String tag) {
        this.tag = tag;
    }

    public String getTagText() {
        String tagPattern = "([a-zA-z]+)";
        Pattern pattern = Pattern.compile(tagPattern);
        Matcher matcher = pattern.matcher(this.tag);
        if(matcher.find())
            return matcher.group(1);
        else
            return "";
    }

    public boolean isClosingTag() {
        return this.tag.charAt(1) == '/';
    }

    public boolean isOpeningTag() {
        return !isClosingTag();
    }

    public boolean isOppositeTag(XMLTag other) {
        return (other.equals(getOppositeTag()));
    }

    public XMLTag getOppositeTag() {
        if(isClosingTag()) {
            return new XMLTag("<" + getTagText() + ">");
        }
        else {
            return new XMLTag("</" + getTagText() + ">");
        }
    }

    public boolean equals(Object other) {
        if(other == null && this == null)
            return true;
        if(other instanceof XMLTag) {
            XMLTag o = (XMLTag) other;
            return o.tag.equals(this.tag);
        }
        return false;
    }

    public int hashCode() {
        return tag.hashCode();
    }

    public String toString() {
        return this.tag;
    }

    public int compareTo(XMLtag other) {
        return this.tag.compareTo(other.getTag());
    }
}