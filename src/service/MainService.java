/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.StudentDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import model.Student;

/**
 *
 * @author Administrator
 */
public class MainService {
    private String user="root";
    private String pass="";
    private String url="jdbc:mysql://localhost/java1pexamen";
    private Connection con;
    private Object studentiDao;
    private Object StudentiDao;
    
    private MainService(){
        try{
            con=DriverManager.getConnection(url,user,pass);
        } catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    private static final class SingletonHolder {
        private static final MainService INSTANCE = new MainService();
    }
    
    public static MainService getInstance(){
        return SingletonHolder.INSTANCE;
    }
    
    public boolean adaugaStudent(Student s){
        StudentDao studentDao = new StudentDao(con);
        boolean rez=false;
        
         try{
            Optional<Student> optionalStudent = studentDao.findStudent(s.getCnp());
            
            if(!optionalStudent.isPresent()){
                studentDao.adaugaStudent(s);
                rez=true;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }       
        
        return rez;
    }
    
    public List<Student> getAllStudents(){
        StudentDao studentDao = new StudentDao(con);
        try{
            return studentDao.getAllStudents();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public void stergeStudent(int id){
        StudentDao studentDao = new StudentDao(con);
        try{
            studentDao.stergeStudent(id);
        }catch(SQLException ex){
            ex.printStackTrace();
        } 
    }
}
