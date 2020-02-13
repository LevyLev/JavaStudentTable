/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Student;

/**
 *
 * @author Administrator
 */
public class StudentDao {
    
    private Connection con;
    
    private PreparedStatement stmt1,stmt2,stmt3,stmt4;
    
    public StudentDao(Connection con){
        try{
            this.con = con;
            stmt1 = con.prepareStatement("INSERT INTO studenti VALUES (NULL,?,?,?)");
            stmt2 = con.prepareStatement("SELECT * FROM studenti");
            stmt3 = con.prepareStatement("DELETE FROM studenti WHERE id = ?");
            stmt4 = con.prepareStatement("SELECT * FROM studenti WHERE Cnp=?");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public void adaugaStudent(Student s) throws SQLException{
        stmt1.setString(1,s.getNume());
        stmt1.setString(2,s.getPrenume());
        stmt1.setInt(3,s.getCnp());
        stmt1.executeUpdate();
    }
    
    public List<Student> getAllStudents() throws SQLException{
        List<Student> studenti = new ArrayList<>();
        ResultSet rs = stmt2.executeQuery();
        
        while(rs.next()){
            int id = rs.getInt("Id");
            String nume = rs.getString("Nume");
            String prenume = rs.getString("Prenume");
            int cnp = rs.getInt("Cnp");
            studenti.add(new Student(id,nume,prenume,cnp));
        }
        
        return studenti;
    }
    
    public void stergeStudent(int id) throws SQLException{
        stmt3.setInt(1, id);
        stmt3.executeUpdate();
    }
    
     public Optional<Student> findStudent(int Cnp) throws SQLException{
        stmt4.setInt(1,Cnp);
        Student student=null;
        try(ResultSet rs=stmt4.executeQuery()){
            if(rs.next()){
                student = new Student();
                student.setId(rs.getInt("Id"));
                student.setNume(rs.getString("Nume"));
                student.setPrenume(rs.getString("Prenume"));
                student.setCnp(rs.getInt("Cnp"));
                
            }
        }
        return Optional.ofNullable(student);
    }
}
