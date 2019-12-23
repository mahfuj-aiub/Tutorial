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

public class Student extends JFrame{
	private Container con;
	private JTabbedPane jtp;
	private JPanel panel1,panel2,panel3,panel4,panel5;
	private JButton log,ok,oktype,addCourseStudent;
	final JLabel[] jloutput=new JLabel[4];
	final JLabel[] jl=new JLabel[4];
	private JLabel jtea,jtype,sCourseStudent,allCourses;
	private JTextArea jteainfo,syllabusOfCourse;
	private JComboBox jcb,jctb,comboCourse,showCourse,selectCourseStudent;
	private JButton[] uplType=new JButton[2];
	private JTextArea[] uplText=new JTextArea[2];
	private JScrollPane scroll,scrollStudent;
	
	private ArrayList<String> teacherName=new ArrayList<String>() ;
	private String[] labelName= {"Your Name","Your ID","Your Contact No","Your Email Id"};
	private String[] type= {"Books","Videos","Assignments","Lecture Note"};
	private String[] uploadType= {"Upload Assignment","Upload Note"};
	private ArrayList<String> takenCourses=new ArrayList<String>();
	private ArrayList<String> totCourses=new ArrayList<String>();
	
	private String[] uploaddata=new String[4];
	private String id;
	JFileChooser choose;
	public Student(String id)
	{
		this.id=id;
		
		this.setTitle("Student Side");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(200, 100, 600, 600);
		con=this.getContentPane();
		con.setLayout(null);
		inIt(this);
	    this.setVisible(true);
		
	}
	
    public void inIt(Student st)
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
    	panel5=new JPanel();
    	jtp.addTab("Home", panel1);
    	jtp.addTab("Teacher", panel2);
    	jtp.addTab("Course Material", panel3);
    	jtp.addTab("Upload", panel4);
    	jtp.addTab("Course", panel5);
    	panelSideShow(st);
    	
    	
    }

     public void panelSideShow(Student st)
    {
        panel1.setLayout(null);
        int y=10;
       this.inputFromStudentDatabase();
     
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
        jtea=new JLabel("Select Teacher");
        jtea.setBounds(10, 10, 130, 40);
        panel2.add(jtea);
        addingComboBoxValue();
        String[] teacherList=new String[teacherName.size()];
        int loop=0;
        for(String i:teacherName) {
        	teacherList[loop]=i;
        	loop++;
        }
        
       jcb=new JComboBox(teacherList);
        jcb.setBounds(150, 10, 250, 40);
        jcb.setEditable(true);
        panel2.add(jcb);
        
        ok=new JButton("Ok");
        ok.setBounds(420, 10, 50, 40);
        panel2.add(ok);
       
        jteainfo=new JTextArea();
		
		jteainfo.setLineWrap(true);
		jteainfo.setWrapStyleWord(true);
		jteainfo.setEditable(false);
		scroll=new JScrollPane(jteainfo);
		scroll.setBounds(10, 60, 300, 300);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel2.add(scroll);
		
	
		
		
		
		panel3.setLayout(null);
		jtype=new JLabel("Select Type ");
		jtype.setBounds(10, 10, 130, 40);
		panel3.add(jtype);
		jctb=new JComboBox(type);
		jctb.setBounds(150, 10, 250, 40);
		panel3.add(jctb);
		oktype=new JButton("Ok");
		oktype.setBounds(420, 10, 50, 40);
		panel3.add(oktype);
		panel4.setLayout(null);
		int uy=50;
		for(int i=0;i<2;i++)
		{
			uplType[i]=new JButton(uploadType[i]);
			uplType[i].setBounds(200, uy, 135, 40);
			panel4.add(uplType[i]);
			
			uy+=60;
		}
		panel5.setLayout(null);
		
		allCourses=new JLabel("Select Course");
		allCourses.setBounds(5, 20, 100, 40);
		panel5.add(allCourses);
		addStudentCoursetoCombo(uploaddata[1]);
		syllabusOfCourse=new JTextArea();
		syllabusOfCourse.setEditable(false);
		syllabusOfCourse.setLineWrap(true);
		syllabusOfCourse.setWrapStyleWord(true);
        scrollStudent=new JScrollPane(syllabusOfCourse);
        scrollStudent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollStudent.setBounds(10, 60, 300, 300);
        panel5.add(scrollStudent);
        
        sCourseStudent=new JLabel("Add Course");
        sCourseStudent.setBounds(370, 40, 100, 40);
        panel5.add(sCourseStudent);
        allCoursesOffered();
		
		
		
				
		
		
        actionPerform(st);

        
    	 
    }
    public void allCoursesOffered() {
    	Connection con;
   		try {
   			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
   		      Statement mystat=con.createStatement();
         
   			    ResultSet co=mystat.executeQuery("Select * from course ");
   			    while(co.next()) {

   			    	totCourses.add(co.getString("c_name"));
   			    }
   			    co.close();
   			   
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		
   	  String[] cLists=new String[totCourses.size()];
      int count=0;
      for(String name:totCourses) {
      	cLists[count]=name;
      	//System.out.println(courseList[count]);
      	count++;

      }
      selectCourseStudent=new JComboBox<String>(cLists);

      selectCourseStudent.setBounds(350, 90,200, 40);
      panel5.add(selectCourseStudent);
		
		
	}

	public void addStudentCoursetoCombo(String string) {
    	Connection con;
   		try {
   			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
   		      Statement mystat=con.createStatement();
         
   			    ResultSet co=mystat.executeQuery("Select * from course where std_id= '"+string+"'");
   			    while(co.next()) {

   			    	takenCourses.add(co.getString("c_name"));
   			    }
   			    co.close();
   			   
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		
   	  String[] cList=new String[takenCourses.size()];
      int count=0;
      for(String name:takenCourses) {
      	cList[count]=name;
      	//System.out.println(courseList[count]);
      	count++;

      }
      showCourse=new JComboBox<String>(cList);

      showCourse.setBounds(117, 20, 200, 40);
      panel5.add(showCourse);
	}

	public void actionPerform(Student st)
    {
    	ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//String str=jcb.getSelectedItem().toString();
				jteainfo.setText(Teacher.getTeacherDetails(jcb.getSelectedItem().toString()));	
				
				
			}
    		
    	});
    	oktype.addActionListener(new ActionListener() {

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
    		
    		
    	});
    	log.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				st.dispose();
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
    }
    public void fetchFile()
    {
    	choose=new JFileChooser();
    	choose.setCurrentDirectory(new java.io.File("Desktop/"));
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
		 
		    ResultSet myrs=mystat.executeQuery("Select * from student where std_id = '"+this.id+"'");
		    if(myrs.next()) {
		    	uploaddata[0]=myrs.getString("std_first_name")+" "+myrs.getString("std_last_name");
		    	uploaddata[1]=myrs.getString("std_id");
		    	uploaddata[2]=myrs.getString("std_contact");
		    	uploaddata[3]=myrs.getString("std_mail");	
		    }
		    myrs.close();
		 

             
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	
    }
    public void addingComboBoxValue() {
    	 Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
		      Statement mystat=con.createStatement();

			    ResultSet teachers=mystat.executeQuery("Select * from teacher");
			    while(teachers.next()) {

			    	teacherName.add(teachers.getString("t_first_name")+" "+teachers.getString("t_last_name"));
			    }
			    teachers.close();
			   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
   
	/*public static void main(String []args) {
		Student s=new Student();
	
		s.setVisible(true);
		
		

	}*/

}
