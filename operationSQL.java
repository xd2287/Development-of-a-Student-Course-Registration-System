//package SQL;

import java.io.*;
import java.sql.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;
import java.util.Arrays;

public class operationSQL
{
    public static OracleConnection conn;
    public static Statement stmt;

    /*public static void main(String[] args) throws SQLException, IOException{
        operationSQL test = new operationSQL();
        //test.question_jStu("51000189");
		Object[][] data=test.question_m();
		for (int i=0;i<data.length;i++) System.out.println ( Arrays.toString (data[i]));
    }*/
	
    public operationSQL() throws SQLException, IOException
    {
        String username, password;
        username = "\"18081127d\"";			// Your Oracle Account ID
        password ="newpasswd"; 		// Password of Oracle Account

        // Connection
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        conn =
                (OracleConnection)DriverManager.getConnection(
                        "jdbc:oracle:thin:@studora.comp.polyu.edu.hk:1521:dbms",
                        username, password);
        stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        //stmt.executeUpdate("alter profile APP_PROFILE limit sessions_per_user 100");
    }
	
		public String getGrade(String stuID,String courseID) throws SQLException,IOException{
		ResultSet rset = stmt.executeQuery("SELECT GRADE FROM ENROLLMENT WHERE STUDENT_ID='"+stuID+"' AND COURSE_ID='"+courseID+"'");
        String data="";
        while(rset.next())
        {
            data = rset.getInt(1)+"";
        }
        //System.out.println(data);
        conn.close();
        return data;
	}

    public static int searchStuId(String stuIdLogin) throws SQLException {
        ResultSet rset = stmt.executeQuery("SELECT * FROM STUDENTS WHERE STUDENT_ID='"+stuIdLogin+"'");
        rset.last();
        return rset.getRow();
    }

    public static String getStuInformation(String stuIdLogin, String info) throws SQLException, IOException{
        ResultSet rset = stmt.executeQuery("SELECT "+info+" FROM STUDENTS WHERE STUDENT_ID='"+stuIdLogin+"'");
        String data="";
        while(rset.next())
        {
            data = rset.getString(1);
        }
        //System.out.println(data);
        conn.close();
        return data;
    }
	
	public static String[] getAllDept() throws SQLException,IOException{
		ResultSet rset = stmt.executeQuery("SELECT DISTINCT(DEPARTMENT) FROM STUDENTS");
		rset.last();
        int rowCount = rset.getRow();
        rset.beforeFirst();
		String[] data=new String[rowCount];
		int i=0;
		while(rset.next()){
			data[i]=rset.getString(1);
			i++;
		}
		conn.close();
		return data;
	}
    public static Object[][] question_b() throws SQLException, IOException{
        ResultSet rset = stmt.executeQuery("SELECT * FROM COURSES");
        rset.last();
        int rowCount = rset.getRow();
        rset.beforeFirst();
        Object[][] data=new Object[rowCount][4];
        int i=0;
        while (rset.next())
        {
            data[i][0]=rset.getString(1);
            data[i][1]=rset.getString(2);
            data[i][2]=rset.getString(3);
            data[i][3]=rset.getString(4);
            i++;
        }
        conn.close();
        return data;
    }

    public static Object[][] question_c(String ID) throws SQLException,IOException{
        ResultSet rset=stmt.executeQuery("SELECT * FROM COURSES WHERE COURSE_ID IN (SELECT COURSE_ID FROM ENROLLMENT WHERE STUDENT_ID='"+ID+"')");
        rset.last();
        int rowCount=rset.getRow();
        rset.beforeFirst();
        Object[][] data=new Object[rowCount][4];
        int i=0;
        while(rset.next()){
            data[i][0]=rset.getString(1);
            data[i][1]=rset.getString(2);
            data[i][2]=rset.getString(3);
            data[i][3]=rset.getString(4);
            i++;
        }
        conn.close();
        return data;
    }

    public static Object[][] question_dSelect(String sIdLogin) throws SQLException, IOException{
        ResultSet rset = stmt.executeQuery("SELECT * "+
                "FROM COURSES "+
                "WHERE COURSE_ID NOT IN "+
                "(SELECT COURSE_ID FROM ENROLLMENT WHERE STUDENT_ID='"+sIdLogin+"')");
        rset.last();
        int rowCount = rset.getRow();
        rset.beforeFirst();
        Object[][] data=new Object[rowCount][4];
        int i=0;
        while (rset.next())
        {
            data[i][0]=rset.getString(1);
            data[i][1]=rset.getString(2);
            data[i][2]=rset.getString(3);
            data[i][3]=rset.getString(4);
            i++;
        }
        conn.close();
        return data;
    }

    public static void question_dReg(String sIdLogin, String cIdReg, String regDate) throws SQLException, IOException{
        stmt.executeUpdate("INSERT INTO ENROLLMENT VALUES('"+sIdLogin+"','"+cIdReg+"',TO_DATE('"+regDate+"','YYYY/MM/DD'),"+null+")");
        /*ResultSet rsetCourse = stmt.executeQuery("SELECT * FROM ENROLLMENT WHERE STUDENT_ID='"+sIdLogin+"'");
        while (rsetCourse.next()) {
            System.out.println(rsetCourse.getString(1)
                    + " " + rsetCourse.getString(2)
                    + " " + rsetCourse.getString(3)
                    + " " + rsetCourse.getInt(4));
        }
        System.out.println();*/
        conn.close();
    }

    public static void question_e(String attribute,String modified_data,String ID) throws SQLException,IOException{
        if (attribute.equals("BIRTHDATE")){
            stmt.executeUpdate("UPDATE STUDENTS SET BIRTHDATE=TO_DATE('"+modified_data+"','YYYY/MM/DD') WHERE STUDENT_ID='"+ID+"'");
        }
        else stmt.executeUpdate("UPDATE STUDENTS SET "+attribute+"='"+modified_data+"' WHERE STUDENT_ID='"+ID+"'");
        conn.close();
    }

    public static Object[][] question_fCourse() throws SQLException, IOException {
        ResultSet rsetCourse = stmt.executeQuery("SELECT * FROM COURSES");
        rsetCourse.last();
        int rowCount = rsetCourse.getRow();
        rsetCourse.beforeFirst();
        Object[][] data=new Object[rowCount][4];
        int i=0;
        while (rsetCourse.next())
        {
            data[i][0]=rsetCourse.getString(1);
            data[i][1]=rsetCourse.getString(2);
            data[i][2]=rsetCourse.getString(3);
            data[i][3]=rsetCourse.getString(4);
            i++;
        }
        conn.close();
        return data;
    }

    public static Object[][] question_fStu() throws SQLException, IOException{
        ResultSet rsetStu = stmt.executeQuery("SELECT * FROM STUDENTS");
        rsetStu.last();
        int rowCount = rsetStu.getRow();
        rsetStu.beforeFirst();
        Object[][] data=new Object[rowCount][6];
        int i=0;
        while (rsetStu.next())
        {
            data[i][0]=rsetStu.getString(1);
            data[i][1]=rsetStu.getString(2);
            data[i][2]=rsetStu.getString(3);
            data[i][3]=rsetStu.getString(4);
            data[i][4]=rsetStu.getString(5);
            data[i][5]=rsetStu.getString(6);
            i++;
        }
        conn.close();
        return data;
    }

    public static Object[][] question_g(String dept) throws SQLException,IOException{
        ResultSet rset=stmt.executeQuery("SELECT * FROM STUDENTS WHERE DEPARTMENT='"+dept+"'");
        rset.last();
        int rowCount=rset.getRow();
        rset.beforeFirst();
        Object[][] data=new Object[rowCount][6];
        int i=0;
        while(rset.next()){
            data[i][0]=rset.getString(1);
            data[i][1]=rset.getString(2);
            data[i][2]=rset.getString(3);
            data[i][3]=rset.getString(4);
            data[i][4]=rset.getString(5);
            data[i][5]=rset.getString(6);
            i++;
        }
        conn.close();
        return data;
    }

    public static void question_hCourse(String cIdAdd, String cTitleAdd, String cStaffNameAdd, String cSectionAdd) throws SQLException, IOException{
        stmt.executeUpdate("INSERT INTO COURSES VALUES('"+cIdAdd+"','"+cTitleAdd+"','"+cStaffNameAdd+"','"+cSectionAdd+"')");
        //question_fCourse();
        conn.close();
    }

    public static void question_hStu(String sIdAdd, String sNameAdd, String sDepartmentAdd, String sAddressAdd, String sBirthdateAdd, String sGenderAdd) throws SQLException, IOException{
        stmt.executeUpdate("INSERT INTO STUDENTS VALUES('"+sIdAdd+"','"+sNameAdd+"','"+sDepartmentAdd+"','"+sAddressAdd+"',TO_DATE('"+sBirthdateAdd+"','YYYY/MM/DD'),'"+sGenderAdd+"')");
        //question_fStu();
        conn.close();
    }

    public static void question_iCourse(String courseID,String attribute,String modified_data) throws SQLException,IOException{
        stmt.executeUpdate("UPDATE COURSES SET "+attribute+"='"+modified_data+"' WHERE COURSE_ID='"+courseID+"'");
        conn.close();
    }
    public static void question_iStu(String attribute,String modified_data,String stuID)throws SQLException,IOException{
        question_e(attribute,modified_data,stuID);
        conn.close();
    }

    public static void question_jCourse(String cIdDelete) throws SQLException, IOException{
        stmt.executeUpdate("DELETE COURSES WHERE COURSE_ID='"+cIdDelete+"'");
        //question_fCourse();
        conn.close();
    }

    public static void question_jStu(String sIdDelete) throws SQLException, IOException{
        stmt.executeUpdate("DELETE STUDENTS WHERE STUDENT_ID='"+sIdDelete+"'");
        //question_fStu();
        conn.close();
    }

    public static void question_k(String grade,String stuID,String courseID) throws SQLException,IOException{
        stmt.executeUpdate("UPDATE ENROLLMENT SET GRADE=TO_NUMBER('"+grade+"') WHERE STUDENT_ID='"+stuID+"' AND COURSE_ID='"+courseID+"'");
        conn.close();
    }
	
	public Object[][] question_l() throws SQLException,IOException{
        ResultSet rset = stmt.executeQuery("SELECT STUDENT_ID,COUNT(COURSE_ID) FROM ENROLLMENT GROUP BY STUDENT_ID ORDER BY (COUNT(COURSE_ID)) DESC FETCH FIRST 5 ROWS WITH TIES");
        rset.last();
        int rowCount = rset.getRow();
        rset.beforeFirst();
        Object[][] data=new Object[rowCount][2];
        int i=0;
        while (rset.next())
        {
            data[i][0]=rset.getString(1);
            data[i][1]=rset.getString(2);
            i++;
        }
        conn.close();
        return data;
    }

	public Object[][] question_m() throws SQLException,IOException{
        ResultSet rset = stmt.executeQuery("SELECT STUDENT_ID,AVG(GRADE) FROM ENROLLMENT GROUP BY STUDENT_ID ORDER BY (AVG(GRADE)) DESC FETCH FIRST 5 ROWS WITH TIES");
        rset.last();
        int rowCount = rset.getRow();
        rset.beforeFirst();
        Object[][] data=new Object[rowCount][2];
        int i=0;
        while (rset.next())
        {
            data[i][0]=rset.getString(1);
            data[i][1]=rset.getString(2);
            i++;
        }
        conn.close();
        return data;
    }
}



