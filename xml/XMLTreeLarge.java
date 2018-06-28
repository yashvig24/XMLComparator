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

public class XMLTreeLarge extends XMLTree {

    public XMLTreeLarge(String filepath) {
        super(filepath);
    }

    protected void makeTree() {
        iterativeTree();
    }

    private void iterativeTree() {
        this.root = new XMLNode(new XMLTag("<main>"));
        XMLNode pointer = this.root;
        Stack<XMLNode> stack = new Stack<XMLNode>();
        for(int i = 0; i < this.listTags.size(); i++) {
            XMLTag tag = this.listTags.get(i);
            if(tag.isOpeningTag()) {
                XMLNode currentNode = new XMLNode(tag);
                pointer.addChild(currentNode);
                stack.push(currentNode);
                pointer = currentNode;
            }
            else {
                stack.pop();
                pointer = stack.peek();
            }
        }
    }

    protected String printTree() {
        String opens = printIterative();
        if(opens.length() > 0)
            return opens.substring(0, opens.length() - 2);
        else
            return opens;
    }

    private String printIterative() {
        return "";
    }

    public boolean equals(Object other) {
        if(other instanceof XMLTree) {
            XMLTree o = (XMLTree) other;
            return equalsIterative(o);
        }
        return false;
    }

    private boolean equalsIterative(XMLTree other) {
        Map<List<XMLTag>, Integer> mapThis = this.getAllPaths();
        Map<List<XMLTag>, Integer> mapOther = other.getAllPaths();
        if(mapThis.keySet().size() != mapOther.keySet().size())
            return false;
        for(List<XMLTag> llist : mapThis.keySet()) {
            if(!mapOther.containsKey(llist))
                return false;
            else if(mapThis.get(llist) != mapOther.get(llist)) 
                return false;
        }
        for(List<XMLTag> llist : mapOther.keySet()) {
            if(!mapThis.containsKey(llist))
                return false;
            else if(mapThis.get(llist) != mapOther.get(llist)) 
                return false;
        }
        return true;
    }

    public Map<List<XMLTag>, Integer> getAllPaths() {
        Map<List<XMLTag>, Integer> map = new HashMap<List<XMLTag>, Integer>();
        map = itirateAllPaths();
        return map;
    }

    private List<XMLTag> pathRootToLeaf(XMLNode node, Map<XMLNode, XMLNode> parents) {
        Stack<XMLNode> stack = new Stack<XMLNode>();
        List<XMLTag> path = new LinkedList<XMLTag>();
        while(node != null) {
            stack.push(node);
            node = parents.get(node);
        }
        while(!stack.isEmpty()) {
            path.add(stack.pop().getTag());
        }
        return path;
    }

    private Map<List<XMLTag>, Integer> itirateAllPaths() {
        XMLNode current = this.root; 
        Map<List<XMLTag>, Integer> map = new HashMap<List<XMLTag>, Integer>();
        Stack<XMLNode> stack = new Stack<XMLNode>();
        stack.push(current);
        Map<XMLNode, XMLNode> parents = new HashMap<XMLNode, XMLNode>();
        parents.put(current, null);
        while(!stack.isEmpty()) {
            current = stack.pop();
            if(!current.hasChild()) {
                List<XMLTag> list = pathRootToLeaf(current, parents);
                if(!map.containsKey(list)) {
                    map.put(list, 1);
                }
                else {
                    map.put(list, map.get(list) + 1);
                }
            }
            else {
                for(XMLNode child : current.getChildren()) {
                    stack.push(child);
                }
            }
        }
        return map;
    }

    public boolean containsPath(List<XMLTag> list) {
        return containsPathIterative(list);
    }

    private boolean containsPathIterative(List<XMLTag> list) {
        
    }
}