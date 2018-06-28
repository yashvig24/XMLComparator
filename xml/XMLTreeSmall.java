package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

import xml.XMLNode;

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

    protected void makeTree() {
        List<XMLTag> withMainTag = new ArrayList<XMLTag>(this.listTags);
        withMainTag.add(0, new XMLTag("<main>"));
        withMainTag.add(withMainTag.size(), new XMLTag("</main>"));
        this.root = recursiveTree(this.root, withMainTag);
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

    public Map<List<XMLTag>, Integer> getAllPaths() {
        Map<List<XMLTag>, Integer> map = new HashMap<List<XMLTag>, Integer>();
        recurseAllPaths(this.root, map, new LinkedList<XMLTag>());
        return map;
    }

    private void recurseAllPaths(XMLNode node, Map<List<XMLTag>, Integer> map, List<XMLTag> list) {
        if(!node.hasChild()) {
            list.add(node.getTag());
            if(!map.containsKey(list)) {
                map.put(list, 1);
            }
            else {
                map.put(list, map.get(list) + 1);
            }
        }
        else {
            list.add(node.getTag());
            for(XMLNode child : node.getChildren()) {
                recurseAllPaths(child, map, new LinkedList<XMLTag>(list));
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
}