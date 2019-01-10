import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.print.attribute.AttributeSet;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.lang.reflect.Field;
import java.math.BigInteger;

public class PasswordGenerator extends JFrame implements ActionListener {
	
	private JFrame frame;
	private JCheckBox LowerCase;
	private JCheckBox UpperCase;
	private JCheckBox Numbers;
	private JCheckBox SpecialCharacters;
	private int length = 0;
	private int count = 1;
	PasswordCreator creator = new PasswordCreator(length);
	PasswordStrength test = new PasswordStrength();
	private int PasswordCharacters = 62;
	private static final String NL = System.getProperty("line.separator");  
	
	
	public PasswordGenerator() {
		frame = new JFrame("Password Generator");
		frame.setSize(800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		JTextField InputLength = new JTextField(2);
		
		JTextField InputStrength = new JTextField(6);
		InputStrength.setEditable(false);
		
		JTextField TimeToCrack = new JTextField(16);
		TimeToCrack.setEditable(false);
		
		TextComponentLimit.addTo(InputLength, 2);
		
		JTextArea output = new JTextArea(3,60);
		output.setEditable(false);
		output.setRows(4);
		
		Container container = frame.getContentPane();
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		container.setLayout(layout);
		
		JButton GeneratorButton = new JButton("Generate Password");
		JButton SaveButton = new JButton("Save");
		JButton CopyButton = new JButton("Copy");
		JButton ResetButton = new JButton("Reset");
		
		JLabel Lengthlabel = new JLabel("Please Enter a Password Length: ");
		JLabel Speciallabel = new JLabel("Special Characters: ");
		JLabel Lowerlabel = new JLabel("LowerCase Characters: " );
		JLabel Upperlabel = new JLabel("UpperCase Characters: ");
		JLabel Generatedlabel = new JLabel("Your Generated Password: ");
		JLabel Strengthlabel = new JLabel("Password Strength: ");
		JLabel Timelabel = new JLabel("Time to Crack Password: ");
		
		JCheckBox special = new JCheckBox("");
		JCheckBox lower = new JCheckBox("");
		JCheckBox upper = new JCheckBox("");
		
		special.setSelected(false);
		lower.setSelected(true);
		upper.setSelected(true);
		
		//JLabel whitespace = new JLabel(" ");
		//JLabel whitespace2 = new JLabel("                   ");
		
		special.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {		
				if (special.isSelected()) {
					creator.setSpecial(true);
					PasswordCharacters = PasswordCharacters + 23;
					//System.out.println("SPECIAL IS TRUE");
				}
				
				if (!special.isSelected()) {
					creator.setSpecial(false);
					PasswordCharacters = PasswordCharacters - 23;
					//System.out.println("SPECIAL IS FALSE");
				}
			}
		});
		
		lower.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (lower.isSelected()) {
					creator.setLower(true);
					PasswordCharacters = PasswordCharacters + 26;
					//System.out.println("LOWER IS TRUE");
				}
				
				if (!lower.isSelected()) {
					creator.setLower(false);
					PasswordCharacters = PasswordCharacters - 26;
					//System.out.println("LOWER IS FALSE");
				}
			}
		});

		upper.addActionListener(new ActionListener() {
	
			public void actionPerformed(ActionEvent e) {
				if (upper.isSelected()) {
					creator.setUpper(true);
					PasswordCharacters = PasswordCharacters + 26;
					//System.out.println("UPPER IS TRUE");
				}
				
				if (!upper.isSelected()) {
					creator.setUpper(false);
					PasswordCharacters = PasswordCharacters - 26;
					//System.out.println("UPPER IS FALSE");
				}
				
				
			}
		});
		
		GeneratorButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
                String input = InputLength.getText();
                
                try {
                	length = Integer.parseInt(input);
                	
                	if (length > 64 || length < 10) {
                		JOptionPane.showMessageDialog(null, "Please Enter a length between 10 - 64", "Invalid Length" , JOptionPane.ERROR_MESSAGE);
                		InputLength.setText("");
                		return;
                	}
                	
                	creator.setSize(length);
                    
                    if (count == 2) {
                    	JOptionPane.showMessageDialog(null, "Can only generate 1 password at a time");
                    	count = 1;
                    	return;
                    }
                    
                    BigInteger value = test.TimeToCrackPassword(length,PasswordCharacters);
                    BigInteger passwordstrength = new BigInteger("100");
                    
int result;
                    
                    result = value.compareTo(passwordstrength);
                    
                    if (result == -1) {
                    	InputStrength.setBackground(Color.RED); // HOW TO CHANGE BACKGROUND COLOR OF TEXTFIELD
                    	InputStrength.setText("Weak");
                    } // less than
                    
                    if (result == 0) {
                    	InputStrength.setBackground(Color.ORANGE); // HOW TO CHANGE BACKGROUND COLOR OF TEXTFIELD
                    	InputStrength.setText("Medium");
                    }// equal too
                    
                    if (result == 1) {
                    	InputStrength.setBackground(Color.GREEN); // HOW TO CHANGE BACKGROUND COLOR OF TEXTFIELD
                    	InputStrength.setText("Strong");
                    }// greater than
                                                                              
                    TimeToCrack.setText(value + " Years to Crack!");
                    output.append(creator.Generator() + "\n");
                    count++;
                }
                catch (NumberFormatException e1) {
                	JOptionPane.showMessageDialog(null, e1.toString(), "No Length Entered" , JOptionPane.ERROR_MESSAGE);
                	InputLength.setText("");
                	count = 1;
                }

            }
		});
		
		ResetButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				InputLength.setText("");
				output.setText("");
				special.setSelected(false);
				lower.setSelected(true);
				upper.setSelected(true);
				
				InputStrength.setBackground(Color.WHITE);
				InputStrength.setText("");
				
				TimeToCrack.setText("");
				
				count = 1;
				PasswordCharacters = 62;
				
				JOptionPane.showMessageDialog(null, "Data has been Cleared");
			}
		});
		
		CopyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection ss = new StringSelection(output.getText());
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss,ss);
			}
		});
		
		SaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            BufferedWriter bf = new BufferedWriter(new FileWriter("test.txt", true));
			            
			            if (count == 1) {
			            	JOptionPane.showMessageDialog(null, "No Password has been generated" , "Error", JOptionPane.ERROR_MESSAGE, null);
			            	return;
			            }
			            
			            bf.append(output.getText());
			            bf.newLine();
			            bf.flush();
			            bf.close();
			        } 
				 
				 catch (IOException er) {
			            er.printStackTrace();
			        }

			    }
		});
		 
		 container.add(Lengthlabel);
	     container.add(InputLength);
	     //container.add(whitespace);
	     
	     container.add(Speciallabel);
	     container.add(special);
	     //container.add(whitespace);
	     //container.add(Box.createHorizontalStrut(12));
	     
	     container.add(Lowerlabel);
	     container.add(lower);
	     //container.add(Box.createHorizontalGlue());
	     
	     container.add(Upperlabel);
	     container.add(upper);
	     
	     //container.add(whitespace2);
	     //container.add(Box.createHorizontalStrut(55));
	     
	     container.add(output);
	     container.add(GeneratorButton);
	     container.add(SaveButton);
	     container.add(CopyButton);
	     container.add(ResetButton);
	     
	     container.add(Strengthlabel);
	     container.add(InputStrength);
	     container.add(Timelabel);
	     container.add(TimeToCrack);
         
        // makes the frame visible
        frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

