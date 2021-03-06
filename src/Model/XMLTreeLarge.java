package model;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class XMLTreeLarge extends XMLTree {

    public XMLTreeLarge(String filepath) {
        super(filepath);
    }

    protected void makeTree(List<XMLTag> listTags) {
        iterativeTree(listTags);
    }

    private void iterativeTree(List<XMLTag> listTags) {
        Stack<XMLNode> stack = new Stack<XMLNode>();
        int i = 0;
        XMLTag currTag = listTags.get(i);
        stack.push(new XMLNode(currTag));
        i++;
        while(!stack.isEmpty()) {
            currTag = listTags.get(i);
            if(currTag.isOpeningTag()) {
                stack.push(new XMLNode(currTag));
            }
            else {
                XMLNode close = stack.pop();
                if(!stack.isEmpty())
                    stack.peek().addChild(close);
                else
                    this.root = close;
            }
            i++;
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
            return this.root.equals(o.root);
        }
        return false;
    }

    public Set<List<XMLTag>> getAllPaths() {
        return itirateAllPaths();
    }

    private List<XMLTag> pathRootToNode(XMLNode node, Map<XMLNode, XMLNode> parents) {
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

    private Set<List<XMLTag>> itirateAllPaths() {
        XMLNode current = this.root; 
        Set<List<XMLTag>> set = new HashSet<List<XMLTag>>();
        Stack<XMLNode> stack = new Stack<XMLNode>();
        stack.push(current);
        Map<XMLNode, XMLNode> parents = new HashMap<XMLNode, XMLNode>();
        parents.put(current, null);
        set.add(pathRootToNode(current, parents));
        while(!stack.isEmpty()) {
            current = stack.pop();
            if(!current.hasChild()) {
                List<XMLTag> list = pathRootToNode(current, parents);
                set.add(list);
            }
            else {
                for(XMLNode child : current.getChildren()) {
                    stack.push(child);
                    parents.put(child, current);
                    set.add(pathRootToNode(child, parents));
                }
            }
        }
        return set;
    }

    public boolean containsPath(List<XMLTag> list) {
        return containsPathIterative(list);
    }

    private boolean containsPathIterative(List<XMLTag> list) {
        if(list.size() == 0)
            return true;
        XMLNode current = this.root;
        int i = 0;
        if(!current.getTag().equals(list.get(i)))
            return false;
        i++;
        Stack<XMLNode> stack = new Stack<XMLNode>();
        stack.push(current);
        while(!stack.isEmpty() && i != list.size()) {
            if(!stack.peek().hasChildWithTag(list.get(i))) {
                stack.pop();
            }
            else {
                current = stack.peek();
                for(XMLNode child : current.getChildrenWithTag(list.get(i))) {
                    stack.push(child);
                }
                i++;
            }
        }
        // return true only if we have pushed all elements from list into stack
        // which would mean that we have found a path
        return stack.size() == list.size();
    }

    public String compare(XMLTree other) {
        String missing = "";
        String extra = "";
        Set<List<XMLTag>> setThis = getAllPaths();
        Set<List<XMLTag>> setOther = other.getAllPaths();
        if(hasCommon(setThis, setOther)) {
            return "The two XML files have completely different data";
        }
        for(List<XMLTag> path : setThis) {
            if(!setOther.contains(path))
                missing += path.get(path.size() - 1).toString() + "\n";
        }
        for(List<XMLTag> path : setOther) {
            if(!setThis.contains(path))
                extra += path.get(path.size() - 1).toString() + "\n";
        }
        if(missing.length() == 0 && extra.length() == 0) {
            return "Both XML files have the same structure";
        }
        else {
            if(missing.length() != 0) {
                String missingIntro = "These are the missing tags in " + other.getName() + "\n";
                missing = missingIntro + missing;
            }
            if(extra.length() != 0) {
                String extraIntro = "These are the extra tags in " + other.getName() + "\n";
                extra = extraIntro + extra;
            }
            return missing + extra;
        }
    }

    private boolean hasCommon(Set<List<XMLTag>> setThis, Set<List<XMLTag>> setOther) {
        for(List<XMLTag> path : setThis) {
            if(setOther.contains(path))
                return false;
        } 
        return true;
    }
}