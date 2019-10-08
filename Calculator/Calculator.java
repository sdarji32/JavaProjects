
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 A simplified calculator. 
 The only operations are addition and subtraction.
*/
public class Calculator extends JFrame 
                        implements ActionListener
{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;
    public static final int NUMBER_OF_DIGITS = 10;

    private JTextField firstField; 
    private JTextField secondField;
    private double result = 0.0;
    private JLabel resultOut;
    private JComboBox operation;

    public static void main(String[] args)
    {
        Calculator aCalculator = new Calculator( );
        aCalculator.setVisible(true);
    }

    public Calculator( )
    {
        setTitle("Simplified Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout( ));

        JPanel textPanel = new JPanel( );
        textPanel.setLayout(new FlowLayout( ));
        firstField = new JTextField("0", NUMBER_OF_DIGITS);
        firstField.setBackground(Color.WHITE);
        textPanel.add(firstField);
        
       // String[] ops = { "+", "-", "*", "/"};
        operation = new JComboBox();
        operation.addItem("+");
        operation.addItem("-");
        textPanel.add(operation);
        
        
        secondField = new JTextField("0", NUMBER_OF_DIGITS);
        secondField.setBackground(Color.WHITE);
        textPanel.add(secondField);
        
        resultOut = new JLabel(" = ");
        textPanel.add(resultOut);
        
        add(textPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel( );
        buttonPanel.setBackground(Color.BLUE);
        buttonPanel.setLayout(new FlowLayout( )); 

        JButton addButton = new JButton("Calculate"); 
        addButton.addActionListener(this);
        buttonPanel.add(addButton); 
        
        JButton resetButton = new JButton("Reset"); 
        resetButton.addActionListener(this);
        buttonPanel.add(resetButton);

       add(buttonPanel, BorderLayout.CENTER);
    }
        
	    
	    public void actionPerformed(ActionEvent e)
	    {
	        String actionCommand = e.getActionCommand( );
	        result=stringToDouble(firstField.getText())+
	        		stringToDouble(secondField.getText());
	        resultOut.setText(" = "+result);	        
	     }
	    
	    private double stringToDouble(String stringObject)
	    {
	        return Double.parseDouble(stringObject.trim( ));
	    }
    

}
