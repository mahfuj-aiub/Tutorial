import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Teacher extends JFrame {
    private Course courses;
    private String id;
	private Container con;
	private JTabbedPane jtp;
	private JPanel panel1,panel2,panel3,panel4;
	private JButton log,ok,oktype,okSelectCourse,okAddCourse,addbutcourse;
	final JLabel[] jloutput=new JLabel[4];
	final JLabel[] jl=new JLabel[4];
	private JLabel jtea,jtype,jcombo,jCourseUpload,addCourse;
	private JTextArea jteainfo,jCourseInfo,jtextUpload;
	private String[] uploaddata=new String[4];
	private static JComboBox<String> jCourse,selectmat,jComboUpload;
	private JButton[] uplType=new JButton[4];
	JFileChooser choose;
	private JTextField JAddCourse;
	private ArrayList<String> Courses=new ArrayList<String>();
	
	private JScrollPane scoll,scollUpload;
	private String[] labelName= {"Your Name","Your Id","Your Contact No","Your Email ID "};
	private String[] type= {"Books","Videos","Assignments","Lecture Note"};
	private String[] uploadType= {"Upload Assignment","Upload Videos","Upload Lectures","Upload Note"};
	public Teacher(String t)
	{
		this.id=t;
		this.setTitle("Teacher Side");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200, 100, 600, 600);
		con=this.getContentPane();
		con.setLayout(null);
		inIt();
		this.setVisible(true);
		
	}
	public void addCourse(Course e)
	{
		this.courses=e;
	}
	
    public void inIt()
    {
    	log=new JButton("Sign Out");
    	log.setBounds(500, 0, 100, 30);
    	con.add(log);
    	jtp=new JTabbedPane();
    	jtp.setBounds(0, 10, 590, 590);
    	con.add(jtp);
    	panel1=new JPanel();
    	panel2=new JPanel();
    	panel3=new JPanel();
    	panel4=new JPanel();
    	

    	jtp.addTab("Home", panel1);
    	jtp.addTab("Course", panel2);
    	jtp.addTab("Upload", panel3);
    	jtp.addTab("Course Material", panel4);
    	panelSideShow(this);
    	
    	
    	
    }
    public void panelSideShow(Teacher t)
    {
        panel1.setLayout(null);
        this.inputFromStudentDatabase();
        int y=10;
        for(int i=0;i<4;i++)
		{
			jl[i]=new JLabel(labelName[i]);
			jloutput[i]=new JLabel(uploaddata[i]); 
			jl[i].setBounds(10, y, 130,40);
			jloutput[i].setBounds(160, y, 250, 40);
			panel1.add(jl[i]);
			panel1.add(jloutput[i]);
			y+=60;
		}
        panel2.setLayout(null);
        jcombo=new JLabel("Select Course");
      //  getCourseFromDatabase();
        addCourseToComboBox();
      /*  String[] courseList=new String[Courses.size()];
        int count=0;
        for(String name:Courses) {
        	courseList[count]=name;
        	count++;
        }
        jCourse=new JComboBox(courseList);*/
        jCourseInfo=new JTextArea();
        okSelectCourse =new JButton("Ok");
        jCourseInfo.setWrapStyleWord(true);
        jCourseInfo.setLineWrap(true);
        jCourseInfo.setEditable(false);
        scoll=new JScrollPane(jCourseInfo);
        scoll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scoll.setBounds(10, 60, 300, 300);
        panel2.add(scoll);
        okSelectCourse.setBounds(320, 20, 40, 40);
        jcombo.setBounds(10, 20, 120, 40);
        //jCourse.setBounds(117, 20, 200, 40);
        panel2.add(jcombo);
     //   panel2.add(jCourse);
        panel2.add(okSelectCourse);
        
        jCourseUpload=new JLabel("Add Course Topics");
        addCourseTopicsToComboBox();
        //jComboUpload=new JComboBox(courseList);
        jtextUpload=new JTextArea();
        okAddCourse=new JButton("Upload");
        okAddCourse.setBounds(470, 440, 60, 40);
        jtextUpload.setWrapStyleWord(true);
        jtextUpload.setLineWrap(true);
        
        scollUpload=new JScrollPane(jtextUpload);
        scollUpload.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       // jCourseUpload.setBounds(320, 50, 150, 40);
        jComboUpload.setBounds(320, 90, 190, 40);
        scollUpload.setBounds(320, 140, 200, 300);
       // panel2.add(jCourseUpload);
        panel2.add(jComboUpload);
        panel2.add(scollUpload);
        panel2.add(okAddCourse); 
        
        
        addCourse=new JLabel("Add Course");
        JAddCourse=new JTextField();
        addbutcourse=new JButton("Add");
        addCourse.setBounds(10, 380, 75, 40);
        JAddCourse.setBounds(80, 380, 200, 40);
        addbutcourse.setBounds(40, 440, 40, 40);
        panel2.add(JAddCourse);
        panel2.add(addbutcourse);
        panel2.add(addCourse);
        
        
        
      
        
        
		panel3.setLayout(null);
		int uy=50;
		for(int i=0;i<4;i++)
		{
			uplType[i]=new JButton(uploadType[i]);
			uplType[i].setBounds(200, uy, 135, 40);
			panel3.add(uplType[i]);
			
			uy+=60;
		}
		
		panel4.setLayout(null);
		jtype=new JLabel("Select Type ");
		jtype.setBounds(10, 10, 130, 40);
		panel4.add(jtype);
       selectmat=new JComboBox<String>(type);
		selectmat.setBounds(150, 10, 250, 40);
		panel4.add(selectmat);
		/*oktype=new JButton("Upload");
		oktype.setBounds(40, 10, 50, 40);
		panel4.add(oktype);*/
		
		
	
		
		
        actionPerform(t);
        
    	 
    }
    public void actionPerform(Teacher t)
    {
    	/*ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
												
			}
    		
    	});*/
    	/*oktype.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String str=jctb.getSelectedItem().toString();
				if(str.equals("Books")) {
					books();
				}
				else if(str.equals("Videos")) {
					videos();
				}
				else if (str.equals("Assignments"))
				{
					assignments();
				}
				else if(str.equals("Lecture Notes")) {
					lectureNote();
				}
				
			}
    		
    		
    	});*/
    	addbutcourse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Course.addCourse(JAddCourse.getText().toString(), uploaddata[1]);
				addNewCourseToCombo(JAddCourse.getText().toString());
				JAddCourse.setText(null);
				
			}
    		
    	});
    	okSelectCourse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jCourseInfo.setText("Topics Need To Covered : "+Course.getCourseTopics(jCourse.getSelectedItem().toString()));
				
			}
    		
    	});
    	okAddCourse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uploadItem=jComboUpload.getSelectedItem().toString();
				String uploadTopics=jtextUpload.getText().toString();
			
				Course.setCourseTopics(uploadItem,uploadTopics);
				jtextUpload.setText(null);
				
			      
				
			}
    		
    	});
    	log.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			t.dispose();
			LogIn.main(null);
				
			}
    		
    	});
    	uplType[0].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fetchFile();
				
			}
    		
    	});
    	uplType[1].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fetchFile();
				
			}
    		
    	});
    	uplType[2].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fetchFile();
				
			}
    		
    	});
    	uplType[3].addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fetchFile();
				
			}
    		
    	});
    }
    public void fetchFile()
    {
    	choose=new JFileChooser();
    	choose.setCurrentDirectory(new java.io.File("."));
    	choose.setDialogTitle("Select File");
    	choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	choose.setAcceptAllFileFilterUsed(false);
    	if(choose.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
    		System.out.println(choose.getSelectedFile());
    	}
    	else
    		System.out.println("No Selection");
    	
    }
    public void books()
    {
    	
    }
    public void videos()
    {
    	
    }
    public void assignments()
    {
    	
    }
    public void lectureNote()
    {
    	
    }
    public void inputFromStudentDatabase() {
    	Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
		    Statement mystat=con.createStatement();
		    
		    ResultSet myrs=mystat.executeQuery("Select * from teacher where t_id = '"+this.id+"'");
		    if(myrs.next()) {
		    	uploaddata[0]=myrs.getString("t_first_name")+" "+myrs.getString("t_last_name");
		    	uploaddata[1]=myrs.getString("t_id");
		    	uploaddata[2]=myrs.getString("t_contact");
		    	uploaddata[3]=myrs.getString("t_mail");	
		    }

             
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	
    }
    public static String getTeacherDetails(String str) {
    	Connection con;
    	String retTeacherDetails = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
		    Statement mystat=con.createStatement();

		    ResultSet myrs=mystat.executeQuery("Select * from teacher where t_id = '"+"1909-2003-2'");
		    if(myrs.next()) {
		    	retTeacherDetails="Email ID  : "+myrs.getString("t_mail")+" Contact No : "+myrs.getString("t_contact");
		    	
		    }

             
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return retTeacherDetails;
    }
    public void getCourseFromDatabase()
    {
   	 Connection con;
   		try {
   			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
   		      Statement mystat=con.createStatement();

   			    ResultSet co=mystat.executeQuery("Select * from course");
   			    while(co.next()) {

   			    	Courses.add(co.getString(2));
   			    }
   			    co.close();
   			   
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
    }
    public void addCourseToComboBox() {
    	 getCourseFromDatabase();
    	  String[] courseList=new String[Courses.size()];
        int count=0;
        for(String name:Courses) {
        	courseList[count]=name;
        	//System.out.println(courseList[count]);
        	count++;

        }
      jCourse=new JComboBox<String>(courseList);
 
        jCourse.setBounds(117, 20, 200, 40);
        panel2.add(jCourse);
    	
    }
    public void addCourseTopicsToComboBox() {
    	 //getCourseFromDatabase();
   	    String[] courseList=new String[Courses.size()];
       int count=0;
       for(String name:Courses) {
       	courseList[count]=name;
       	count++;
       }
    	jComboUpload=new JComboBox<String>(courseList);
    	jCourseUpload.setBounds(320, 50, 150, 40);
    	panel2.add(jCourseUpload);
    	
    }
    public void addNewCourseToCombo(String newCourse) {
         
  	  jCourse.addItem(newCourse);
  	  jComboUpload.addItem(newCourse);
    }
  	  
    /*public static void main(String[] args) {
    
		Teacher s=new Teacher();
		s.
		
		

	}*/


}
