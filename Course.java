import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {

	private Teacher teacher;
	public void addTeacher(Teacher t)
	{
		
	}
	public Teacher getTeacher()
	{
		return teacher;
	}
	
	public static void addCourse(String courseName,String id) {
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
			String sql="Insert into course (c_name,t_id)values(?,?)";
			PreparedStatement stat=con.prepareStatement(sql);
			//stat.setInt(1,2 );
			stat.setString(1,courseName);
			stat.setString(2,id);
			stat.executeUpdate();
		}   catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void setCourseTopics(String item,String topic) {
		
		Connection con;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
			
			String sql="update course set topics = concat_ws(' ',topics,?) where c_name = ?" ;
			PreparedStatement stat=con.prepareStatement(sql);
			stat.setString(1, topic);
			stat.setString(2, item);
			stat.executeUpdate();
		//	System.out.println(item+ " "+topic);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
	}
	public static String getCourseTopics(String str)
	{
		Connection con;
		String cTopics=null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tutorial","root","aiub18@$");
		    Statement mystat=con.createStatement();
		    ResultSet myrs=mystat.executeQuery("Select topics from course where c_name='"+str+"'");
		    if(myrs.next()) {
		    	cTopics= myrs.getString(1);
		    }
		   
		   
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cTopics;

	    
	    
		
	}

}
