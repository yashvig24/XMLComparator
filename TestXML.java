import xml.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class TestXML {

    private static final int LARGE_FILE_LINES = 10000;

    public static void main(String args[]) {
        // compareXML(args[0], args[1]);
        // compareXML("data/awards.xml", "data/awardsmissing.xml");
        System.out.println(compareXML("data/test1xml.xml", "data/test1xmlmissing.xml"));
    }
    
    private static boolean compareXML(String filepath1, String filepath2) {
        XMLTree t1;
        XMLTree t2;
        int numLines1 = countLines(filepath1);
        int numLines2 = countLines(filepath2);
        if(numLines1 > LARGE_FILE_LINES || numLines2 > LARGE_FILE_LINES) {
            t1 = new XMLTreeLarge(filepath1);
            t2 = new XMLTreeLarge(filepath2);
        }
        else {
            t1 = new XMLTreeSmall(filepath1);
            t2 = new XMLTreeSmall(filepath2);
        }
        
        if(t1.equals(t2))
            return true;
        else {
            comparePathMaps(t1, t2);
            return false;
        }
    }

    private static void comparePathMaps(XMLTree t1, XMLTree t2) {
        Map<List<XMLTag>, Integer> map1 = t1.getAllPaths();
        Map<List<XMLTag>, Integer> map2 = t2.getAllPaths();
        for(List<XMLTag> list : map2.keySet()) {
            if(!map1.keySet().contains(list)) {
                if(!t1.containsPath(list))
                    System.out.println(getStringPathExtra(list, map2.get(list)));
            }
            else if(map1.get(list) < map2.get(list)) {
                int extra = map2.get(list) - map1.get(list);
                System.out.println(getStringPathExtra(list, extra));
            }
        }
        for(List<XMLTag> list : map1.keySet()) {
            if(!map2.keySet().contains(list)) {
                System.out.println(getStringPathMissing(list, map1.get(list)));
            }
            else if(map2.get(list) < map1.get(list)) {
                int extra = map1.get(list) - map2.get(list);
                System.out.println(getStringPathMissing(list, extra));
            }
        }

    }

    private static String getStringPathExtra(List<XMLTag> list, int n) {
        String path = ("Extra tag (" + n + ") times : \n");
        return path + getStringPath(list);
    }

    private static String getStringPathMissing(List<XMLTag> list, int n) {
        String path = ("Missing tag (" + n + ") times : \n");
        return path + getStringPath(list);
    }

    private static String getStringPath(List<XMLTag> list) {
        String tagPath = "";
        for(XMLTag tag : list) {
            tagPath += (tag + " - > ");
        }
        return tagPath.substring(0, tagPath.length() - 5);
    }

    private static int countLines(String filename) {
        try {
            InputStream is = new BufferedInputStream(new FileInputStream(filename));
            byte[] c = new byte[1024];
    
            int readChars = is.read(c);
            if (readChars == -1) {
                is.close();
                // bail out if nothing to read
                return 0;
            }
    
            // make it easy for the optimizer to tune this loop
            int count = 0;
            while (readChars == 1024) {
                for (int i=0; i<1024;) {
                    if (c[i++] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }
    
            // count remaining characters
            while (readChars != -1) {
                for (int i=0; i<readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }
            is.close();
            return count == 0 ? 1 : count;
        }
        catch(Exception e) {
            System.out.println("File not found");
        } 
        return 0;
    }
}