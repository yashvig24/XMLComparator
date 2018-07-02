package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class XMLTreeSmall extends XMLTree {

    private int currIndex;

    public XMLTreeSmall(String filepath) {
        super(filepath);
    }

    protected void makeTree(List<XMLTag> listTags) {
        this.root = recursiveTree(this.root, listTags);
    }

    private XMLNode recursiveTree(XMLNode node, List<XMLTag> list) {
        if(currIndex < list.size() - 1) {
            XMLTag tag = list.get(currIndex);
            node = new XMLNode(tag);
            currIndex++;
            XMLTag newTag = list.get(currIndex);
            while(!tag.isOppositeTag(newTag)) {
                node.addChild(recursiveTree(null, list));
                currIndex++;
                newTag = list.get(currIndex);
            }
        }
        return node;
    }

    protected String printTree() {
        String opens = printRecursive(this.root);
        if(opens.length() > 0)
            return opens.substring(0, opens.length() - 2);
        else
            return opens;
    }

    private String printRecursive(XMLNode node) {
        if(node != null) {
            String result = node.getTag().toString() + ", ";
            for(XMLNode child : node.getChildren()) {
                result += printRecursive(child);
            }
            return result;
        }
        return "";
    }

    public boolean equals(Object other) {
        if(other instanceof XMLTree) {
            XMLTree o = (XMLTree) other;
            return equalsRecursive(o);
        }
        return false;
    }

    private boolean equalsRecursive(XMLTree other) {
        return this.root.equalsRecursive(other.root);
    }

    public Set<List<XMLTag>> getAllPaths() {
        Set<List<XMLTag>> set = new HashSet<List<XMLTag>>();
        recurseAllPaths(this.root, set, new LinkedList<XMLTag>());
        return set;
    }

    private void recurseAllPaths(XMLNode node, Set<List<XMLTag>> set, List<XMLTag> list) {
        if(!node.hasChild()) {
            list.add(node.getTag());
            set.add(list);
        }
        else {
            list.add(node.getTag());
            for(XMLNode child : node.getChildren()) {
                recurseAllPaths(child, set, new LinkedList<XMLTag>(list));
            }
        }
    }

    public boolean containsPath(List<XMLTag> list) {
        if(list.size() > 0)
            return containsPathRecursive(list, this.root);
        else    
            return true;
    }

    private boolean containsPathRecursive(List<XMLTag> list, XMLNode node) {
        if(!node.hasChild() && list.size() > 1)
            return false;
        if(list.size() == 1)
            return list.get(0).equals(node.getTag());
        XMLTag currTag = list.get(0);
        boolean sameTag = node.getTag().equals(currTag);
        if(sameTag) {
            boolean nextTag = false;
            for(XMLNode child : node.getChildren()) {
                nextTag = nextTag || containsPathRecursive(list.subList(1, list.size()), child);
            }
            return nextTag;
        }
        else
            return false;
    }

    public String compare(XMLTree other) {
        String result = compareRecursive();
        return result;
    }

    private String compareRecursive() {
        return "";
    }
}