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

public abstract class XMLTree {

    protected XMLNode root;
    protected List<XMLTag> listTags;
    
    public XMLTree(String filepath) {
        File file = new File(filepath);
        try {
            Scanner s = new Scanner(file);
            this.listTags = new ArrayList<XMLTag>();
            makeList(s);
            makeTree();
            this.root = this.root.getChild(0);
            s.close();
            // printTree();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public List<XMLTag> getList() {
        return new ArrayList<XMLTag>(this.listTags);
    }

    /**
     * @requires scanner is scanning a valid xml file
     */
    private void makeList(Scanner s) {
        Pattern p = Pattern.compile("(<[^(><)]*>)");
        while(s.hasNextLine()) {
            Matcher m = p.matcher(s.nextLine());
            while(m.find()) {
                XMLTag tag = new XMLTag(m.group(1));
                this.listTags.add(tag);
            }
        }
    }

    protected abstract void makeTree();

    protected abstract String printTree();

    public abstract boolean equals(Object other);

    public abstract Map<List<XMLTag>, Integer> getAllPaths();

    public abstract boolean containsPath(List<XMLTag> list);
}