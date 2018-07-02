package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import xml.*;
import test.CompareXML;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.*;

class ComparisonFrame extends JFrame {

  private JButton btnCompare  = new JButton("Compare");
  private JButton btnClearA  = new JButton("X");
  private JButton btnClearB  = new JButton("X");

  private JLabel lblA = new JLabel("Base File: ");
  private JLabel lblB = new JLabel("File to compare: ");
  private JLabel result = new JLabel("Results: ");

  private JTextArea txtboxA;
  private JTextArea txtboxB;

  private JTextArea resultField; 

  private JScrollPane scroll;
  private JScrollPane scrollfilepath1;
  private JScrollPane scrollfilepath2;

  public ComparisonFrame(){
    setTitle("XML Comparison Tool");
    setSize(380,320);
    setLocation(new Point(300,200));
    setLayout(null);    
    setResizable(false);

    initComponent();    
    initEvent();    
    
    setLocationRelativeTo(null);
  }

  private void initComponent(){
    btnCompare.setBounds(145, 250, 80, 25);
    btnClearA.setBounds(135, 10, 35, 15);
    btnClearB.setBounds(310, 10, 35, 15);

    lblA.setBounds(20,10,200,20);
    lblB.setBounds(200,10,200,20);
    result.setBounds(20, 50, 140, 100);

    txtboxA =new JTextArea();
    txtboxA.setBounds(20, 35, 150, 40);
    scrollfilepath1 = new JScrollPane(txtboxA);
    scrollfilepath1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    txtboxB =new JTextArea();
    txtboxB.setBounds(200, 35, 150, 40);
    scrollfilepath2 = new JScrollPane(txtboxB);
    scrollfilepath2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    resultField = new JTextArea();
    resultField.setBounds(20, 110, 320, 120);
    resultField.setEditable(false);

    scroll = new JScrollPane(resultField);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);



    add(resultField);
    add(txtboxB);
    add(txtboxA);
    add(btnCompare);
    add(lblA);
    add(lblB);
    add(result);
    add(btnClearA);
    add(btnClearB);

    new FileDrop( System.out, txtboxA, /*dragBorder,*/ new FileDrop.Listener()
        {   public void filesDropped( java.io.File[] files )
            {   for( int i = 0; i < files.length; i++ )
                {   try
                    {   txtboxA.append( files[i].getCanonicalPath() + "\n" );
                    }   // end try
                    catch( java.io.IOException e ) {}
                }   // end for: through each dropped file
            }   // end filesDropped
        }); // end FileDrop.Listener

    new FileDrop( System.out, txtboxB, /*dragBorder,*/ new FileDrop.Listener()
        {   public void filesDropped( java.io.File[] files )
            {   for( int i = 0; i < files.length; i++ )
                {   try
                    {   txtboxB.append( files[i].getCanonicalPath() + "\n" );
                    }   // end try
                    catch( java.io.IOException e ) {}
                }   // end for: through each dropped file
            }   // end filesDropped
        }); // end FileDrop.Listener
    }

    private void initEvent(){

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
            System.exit(1);
            }
        });

        btnCompare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            btnCompareClick(e);
            }
        });

        btnClearA.addActionListener((new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextA();
            }
        }));

        btnClearB.addActionListener((new ActionListener(){
        
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextB();
            }
        }));
    }


    private void btnCompareClick(ActionEvent evt) {
        try {
            String relativeA = convertToRelative(txtboxA.getText());
            String relativeB = convertToRelative(txtboxB.getText());
            String result = CompareXML.compare(relativeA.trim(), relativeB.trim());
            resultField.setText(result);
        }
        catch(IOException e) {
            System.out.println("File not found\n");
        }
    }  

    private void clearTextA() {
        txtboxA.setText("");
    }

    private void clearTextB() {
        txtboxB.setText("");
    }

    private String convertToRelative(String absolute) throws IOException {
        String base = new File(".").getCanonicalPath();
        return new File(base).toURI().relativize(new File(absolute).toURI()).getPath();
    }
}