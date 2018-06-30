package xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;

public abstract class XMLTree {

    protected XMLNode root;
    protected String name;
    
    public XMLTree(String filepath) {
        String[] pathnames = filepath.split("/");
        this.name = pathnames[pathnames.length - 1];
        File file = new File(filepath);
        try {
            Scanner s = new Scanner(file);
            List<XMLTag> listTags = makeList(s);
            makeTree(listTags);
            s.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public String getName() {
        return this.name;
    }

    /**
     * @requires scanner is scanning a valid xml file
     */
    private List<XMLTag> makeList(Scanner s) {
        List<XMLTag> listTags = new ArrayList<XMLTag>();
        Pattern p = Pattern.compile("(<[^(><)]*>)");
        while(s.hasNextLine()) {
            Matcher m = p.matcher(s.nextLine());
            while(m.find()) {
                XMLTag tag = new XMLTag(m.group(1));
                listTags.add(tag);
            }
        }
        return listTags;
    }

    protected abstract void makeTree(List<XMLTag> listTags);

    protected abstract String printTree();

    public abstract boolean equals(Object other);

    public abstract Set<List<XMLTag>> getAllPaths();

    public abstract boolean containsPath(List<XMLTag> list);

    public abstract String compare(XMLTree other);
}