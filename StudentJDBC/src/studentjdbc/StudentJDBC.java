/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author eiwte
 */
public class StudentJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws ClassNotFoundException, SQLException {
        String derbyClientDriver = "org.apache.derby.jdbc.ClientDriver";
        Class.forName(derbyClientDriver);
        
        String url = "jdbc:derby://localhost:1527/student";
        String user = "app";
        String passwd = "app";
        
        Connection con = DriverManager.getConnection(url, user, passwd);
        Statement stmt = con.createStatement();
        
        Student s1 = new Student(1, "Jane", 3.50);
        Student s2 = new Student(2, "Tommy", 2.75);
        
        insertStudentPreparedStatement(con, s1);
        insertStudentPreparedStatement(con, s2);
        
        stmt.close();
        con.close();
    }
    
    public static void printAllStudent(ArrayList<Student> studentList) {
        for(Student s : studentList) {
           System.out.print(s.getId() + " ");
           System.out.print(s.getName() + " ");
           System.out.println(s.getGPA() + " ");
        }
    }
    
    public static ArrayList<Student> getAllStudent (Connection con) throws SQLException {
        String sql = "select * from student order by id";
        PreparedStatement ps = con.prepareStatement(sql);
        ArrayList<Student> studentList = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
           Student student = new Student();
           student.setId(rs.getInt("id"));
           student.setName(rs.getString("name"));
           student.setGPA(rs.getDouble("gpa"));
           studentList.add(student);
       }
       rs.close();
       return studentList;
       
    }
    
   public static Student getStudentById(Statement stmt, int id) throws SQLException {
       Student s = null;
       String sql = "select * from student where id = " + id;
       ResultSet rs = stmt.executeQuery(sql);
       if (rs.next()) {
           s = new Student();
           s.setId(rs.getInt("id"));
           s.setName(rs.getString("name"));
           s.setGPA(rs.getDouble("gpa"));
       }
       return s;
   } 
   
   public static void insertStudent(Statement stmt, Student s) throws SQLException {
        String sql = "insert into student (id, name, gpa)" +
                     " values (" + s.getId() + "," + "'" + s.getName() + "'" + "," + s.getGPA() + ")";
        int result = stmt.executeUpdate(sql);
        System.out.println("Insert " + result + " row");
   } 
   
   public static void deleteStudent(Statement stmt, Student s) throws SQLException {
       String sql = "delete from student where id = " + s.getId();
       int result = stmt.executeUpdate(sql);
       System.out.println("delete " + result + " row");
   }
   
   public static void updateStudentGPA(Statement stmt, Student s) throws SQLException {
       String sql = "update student set gpa  = " + s.getGPA() + " where id = " + s.getId();
       int result = stmt.executeUpdate(sql);
       System.out.println("update " + result + " row");
   }
   
   public static void updateStudentName(Statement stmt, Student s) throws SQLException {
       String sql = "update student set gpa  = '" + s.getName() + "'" + " where id = " + s.getId();
       int result = stmt.executeUpdate(sql);
       System.out.println("update " + result + " row");
   }
   
   public static void insertStudentPreparedStatement(Connection con, Student s) throws SQLException {
       String sql = "insert into student (id, name, gpa)" + " values (?,?,?)";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, s.getId());
       ps.setString(2, s.getName());
       ps.setDouble(3, s.getGPA());
       int result = ps.executeUpdate();
       System.out.println("Insert " + result + " row");
   }
   
   public static void deleteStudentPreparedStatement(Connection con, Student s) throws SQLException {
       String sql ="delete from student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, s.getId());
       int result = ps.executeUpdate();
       System.out.println("Delete " + result + " row");
   }
   
   public static void updateStudentGPAPreparedStatement(Connection con, Student s) throws SQLException {
       String sql = "update student set gpa  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setDouble(1, s.getGPA());
       ps.setInt(2, s.getId());
       int result = ps.executeUpdate();
       System.out.println("update " + result + " row");
   }
   
   public static void updateStudentNamePreparedStatement(Connection con, Student s) throws SQLException {
       String sql = "update student set name  = ? where id = ? ";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setString(1, s.getName());
       ps.setInt(2, s.getId());
       int result = ps.executeUpdate();
       System.out.println("update " + result + " row");
   }
   
   public static Student getStudentByIdPreparedStatement(Connection con, int id) throws SQLException {
       Student s = null;
       String sql = "select * from student where id = ?";
       PreparedStatement ps = con.prepareStatement(sql);
       ps.setInt(1, id);
       ResultSet rs = ps.executeQuery();
       if (rs.next()) {
           s = new Student();
           s.setId(rs.getInt("id"));
           s.setName(rs.getString("name"));
           s.setGPA(rs.getDouble("gpa"));
       }
       return s;
   }
   
}

