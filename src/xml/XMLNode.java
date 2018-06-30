package xml;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;

public class XMLNode implements Comparable<XMLNode> {

    private XMLTag tag;
    private Set<XMLNode> children;

    public XMLNode(XMLTag tag) {
        this.tag = tag;
        this.children = new TreeSet<XMLNode>();
    }

    public void addChild(XMLTag child) {
        this.children.add(new XMLNode(child));
    }

    public void addChildren(Set<XMLNode> set) {
        for(XMLNode child : set) {
            addChild(child);
        }
    }

    public void addChild(XMLNode child) {
        this.children.add(child);
    }

    public Set<XMLNode> getChildren() {
        return new TreeSet<XMLNode>(this.children);
    }

    public XMLTag getTag() {
        return this.tag;
    }

    public String getTagType() {
        return this.tag.getTagType();
    }

    public boolean hasChild() {
        return !children.isEmpty();
    }

    public int getNumChildren() {
        return this.children.size();
    }

    public boolean equalsRecursive(XMLNode o) {
        if((this.getTag().equals(o.getTag()))) {
            Set<XMLNode> otherChildren = o.getChildren();
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

    public boolean hasChild(XMLNode o) {
        boolean has = false;
        for(XMLNode child : this.children) 
            has = has || child.equalsRecursive(o);
        return has; 
    }

    public boolean hasChildWithTag(XMLTag tag) {
        boolean has = false;
        for(XMLNode child : this.children) 
            has = has || child.getTag().equals(tag);
        return has; 
    }

    public List<XMLNode> getChildrenWithTag(XMLTag tag) {
        List<XMLNode> list = new ArrayList<XMLNode>();
        for(XMLNode child : this.children) {
            if(child.getTag().equals(tag)) {
                list.add(child);
            }
        }
        return list;
    }

    public XMLNode getFirstChild() {
        for(XMLNode child : this.children) {
            return child;
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof XMLNode))
            return false;
        return equalsRecursive((XMLNode) other);
    }

    public int hashCode() {
        // return hashCodeRecursive(0);
        return hashCodeIterative();
    }

    public int compareTo(XMLNode other) {
        return this.hashCode().compareTo(other.hashCode());
    }

    private int hashCodeIterative() {
        int level = 1
        int hash = level*this.tag.hashCode();
    }

    public List<XMLTag> getCommon(XMLNode other) {
        List<XMLTag> commons = new LinkedList<XMLTag>();
        for(XMLNode child : this.getChildren()) {
            if(other.hasChildWithTag(child.getTag()))
                commons.add(child.getTag());
        }
        return commons;
    }

    /**
     * returns a list of extra children in node other
     */
    public List<XMLNode> getExtraChildren(XMLNode other) {
        List<XMLNode> extras = new LinkedList<XMLNode>();
        for(XMLNode childOther : other.getChildren()) {
            if(!this.hasChildWithTag(childOther.getTag())) 
                extras.add(childOther);
        }
        return extras;
    }
}