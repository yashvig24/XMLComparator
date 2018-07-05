package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import controller.CompareXML;
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

  private static final int WWIDTH = 750; 
  private static final int WHEIGHT = 700;
  private static final int CBWIDTH = WWIDTH/7;
  private static final int CBHEIGHT = WHEIGHT/20;
  private static final int FILETXTBOXH = 60 + WHEIGHT/9;
  private static final int FILETXTBOXW = WWIDTH/3;
  private static final int CLRBTNW = FILETXTBOXW/6;
  private static final int CLRBTNH = FILETXTBOXH/8;

  public ComparisonFrame(){
    setTitle("XML Comparison Tool");
    setSize(WWIDTH, WHEIGHT);
    setLocation(new Point(300,200));
    setLayout(null);    
    setResizable(true);

    initComponent();    
    initEvent();    
    
    setLocationRelativeTo(null);
  }

  private void initComponent() {
    btnCompare.setBounds(WWIDTH/2 - CBWIDTH/2, WHEIGHT - 50 - CBHEIGHT, CBWIDTH, CBHEIGHT);
    lblA.setBounds(WWIDTH/25, WHEIGHT/50, WWIDTH/6, WHEIGHT/13);
    lblB.setBounds(WWIDTH/2 + WWIDTH/25, WHEIGHT/50, WWIDTH/6, WHEIGHT/13);

    btnClearA.setBounds(WWIDTH/2 - WWIDTH/15, WHEIGHT/50, CLRBTNW, CLRBTNH);
    btnClearB.setBounds(WWIDTH - WWIDTH/15, WHEIGHT/50, CLRBTNW, CLRBTNH);
    
    result.setBounds(WWIDTH/25, WHEIGHT/3, WWIDTH/6, WHEIGHT/13);

    txtboxA = new JTextArea();
    txtboxA.setBounds(WWIDTH/25, WHEIGHT/10, WWIDTH/2 - WWIDTH/10, WWIDTH/5);
    txtboxA.setLineWrap(true);

    txtboxB = new JTextArea();
    txtboxB.setBounds(WWIDTH/2 + WWIDTH/25, WHEIGHT/10, WWIDTH/2 - WWIDTH/10, WWIDTH/5);
    txtboxB.setLineWrap(true);

    resultField = new JTextArea();
    resultField.setLineWrap(true);
    resultField.setBounds(WWIDTH/25, WHEIGHT/2 - WHEIGHT/25, WWIDTH - WWIDTH/10, WHEIGHT/3);
    resultField.setEditable(false);

    add(txtboxB);
    add(txtboxA);
    add(btnCompare);
    add(lblA);
    add(lblB);
    add(result);
    add(btnClearA);
    add(btnClearB);
    add(resultField);

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