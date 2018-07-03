package controller;

import model.*;

import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class CompareXML {

    private static final int LARGE_FILE_LINES = 1;
    
    public static String compare(String filepath1, String filepath2) {
        System.out.println(filepath1);
        System.out.println(filepath2);
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
        return t1.compare(t2);
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