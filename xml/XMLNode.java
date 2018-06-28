package xml;

import java.util.List;
import java.util.ArrayList;
import com.google.common.collect.Multiset;
import com.google.common.collect.TreeMultiset;

import sun.font.TrueTypeGlyphMapper;

public class XMLNode implements Comparable<XMLNode> {

    private XMLTag tag;
    private TreeMultiset<XMLNode> children;

    public XMLNode(XMLTag tag) {
        this.tag = tag;
        this.children = new ArrayList<XMLNode>();
    }

    public void addChild(XMLTag child) {
        this.children.add(new XMLNode(child));
    }

    public void addChild(XMLNode child) {
        this.children.add(child);
    }

    public TreeMultiset<XMLNode> getChildren() {
        return new TreeMultiset<XMLNode>(this.children);
    }

    public XMLTag getTag() {
        return this.tag;
    }

    public boolean hasChild() {
        return !children.isEmpty();
    }

    public boolean equalsRecursive(XMLNode o) {
        if((this.getTag().equals(o.getTag()))) {
            TreeMultiset<XMLNode> otherChildren = o.getChildren();
            if(otherChildren.size() == this.children.size()) {
                boolean hasAllChildren = true;
                for(XMLNode child : otherChildren) {
                    hasAllChildren = hasAllChildren && this.hasChild(child);
                }
                return hasAllChildren;
            }
            else
                return false;
        }
        else
            return false;
    }

    public int compareTo(XMLNode other) {
        return this.tag.compareTo(other.tag);
    }

    public boolean hasChild(XMLNode o) {
        boolean has = false;
        for(XMLNode child : this.children) 
            has = has || child.equalsRecursive(o);
        return has; 
    }

    public boolean hasChildWithTag(XMLTag tag) {
        boolean has = false;
        for(XMLNode child : this.children) 
            has = has || child.equals(new XMLNode(o));
        return has; 
    }

    public boolean equals(Object other) {
        if(this == null && other == null)
            return true;
        if(other instanceof XMLNode) {
            XMLNode o = (XMLNode) other;
            return this.tag.equals(o.tag);
        }
        return false;
    }
}