

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class LogIn extends JFrame{
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Registration reg;
	   private Container con;
	   private JTextField jt;
		private JLabel[] jl=new JLabel[2];
		private JButton[] jb=new JButton[3];
		private JPasswordField jpf;
		private JRadioButton jr;
		private  String[] str= {"User ID","Password"};
		private  String[] button= {"Sign Up","Sign In","Forgot Password ?"};
	   public LogIn()
	  {
		  this.setTitle("Start" );
		  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  this.setBounds(200, 100, 600, 600);
		 
		  con=this.getContentPane();
		  con.setLayout(null);
		  inIt();
		  
	  }
	public void inIt() {
		jt =new JTextField();
		jt.setBounds(210, 150, 200, 50);
		con.add(jt);
		int y=150;
		for(int i=0;i<2;i++)
		{
		
			jl[i]=new JLabel(str[i]);
			jl[i].setBounds(100,y , 100, 50);
			y+=70;
			con.add(jl[i]);
		}
		jpf=new JPasswordField();
		jpf.setBounds(210, 220, 200, 50);
		jpf.setEchoChar('*');
		
		con.add(jpf);
		jr=new JRadioButton("Show Password");
		jr.setBounds(210, 275, 200, 50);
		con.add(jr);
		int x=170;
		for(int i=0;i<2;i++)
		{
			jb[i]=new JButton(button[i]);
			jb[i].setBounds(x, 330,100, 50);
			
			x+=150;
			con.add(jb[i]);
			
		}
		jb[2]=new JButton(button[2]);
		jb[2].setBounds(190, 400, 200, 50);
		con.add(jb[2]);
		
	
		
		
		actionEventPerform(this);
		
	}
	public void actionEventPerform( LogIn l)
	{
		jr.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					jpf.setEchoChar((char) 0); 
				}
				else
					jpf.setEchoChar('*');
				
				
			}

			
			
		});
		jb[0].addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			     l.dispose();
				 reg=new Registration();
			}
			
			
		});
		jb[1].addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String str=jt.getText().toString().replaceAll(" ", "");	
				
				Connection con;
				Statement st = null;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Tutorial","root","aiub18@$");
					Statement mystat=con.createStatement();
					st=mystat;
				
					//
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			    
				
				if(str.charAt(2)=='-'&&str.length()==10) {
					String s="Select * from student where std_id = '"+str+"'";
					String pass=null;
					try {
						
						ResultSet rs=st.executeQuery(s);
						
						if(rs.next()) {
							pass=rs.getString("std_password");
							
						}
						if(pass.equals(jpf.getText().toString().trim())) {
						   Student stu=new Student(str);
						   
						l.dispose();
					}
					else
					{
						jpf.setText(null);
					
						JOptionPane.showMessageDialog(null, "Password missmathced", "Warning", 0);
					}
					
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}			
				}
				else if(str.charAt(4)=='-'&&str.length()==11)
				{
					String s="Select * from teacher where t_id = '"+str+"'";
					String pass=null;
					String user_id = null;
					try {
						
						ResultSet rs=st.executeQuery(s);
						
						if(rs.next()) {
							pass=rs.getString("t_password");
							user_id=rs.getString("t_id");
							
						}
					if(pass.equals(jpf.getText().toString().trim())&&user_id.equals(jt.getText().toString())) {
						 Teacher stu=new Teacher(str);
							   l.dispose();
					}
					else
					{
						jpf.setText(null);
						JOptionPane.showMessageDialog(null, "Password missmathced", "Warning", 0);
					}
					
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}			
					
					
				}
				else
				{
					jt.setText(null);
					jpf.setText(null);
					JOptionPane.showMessageDialog(null, "User id not found", "Warning", 0);
				}
			}
			
		});
	}
	

	public static void main(String[] args) {
		LogIn log=new LogIn();
		log.setVisible(true);
		
		
		
		

	}

}
