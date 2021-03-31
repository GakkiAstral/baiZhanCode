package dao.impl;

import commons.JdbcUtil;
import dao.DepartmentsDao;
import pojo.Departments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DepartmentsDao接口实现类
 */
public class DepartmentsDaoImpl extends BaseDaoImpl implements DepartmentsDao {
    @Override
    public List<Departments> selectDeptByName(String depart_name) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Departments> list = new ArrayList<>();
        try{
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("select * from departments where depart_name = ?");
            ps.setString(1,depart_name);
            rs = ps.executeQuery();
            while (rs.next()){
                Departments d = new Departments();
                d.setDepartment_id(rs.getString("department_id"));
                d.setDepartment_name(rs.getString("department_name"));
                d.setLocation_id(rs.getInt("location_id"));
                list.add(d);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeResource(ps,conn,null);
        }
        return list;
    }

    @Override
    public void insertDept(Departments dept) {
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = JdbcUtil.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("insert into departments values (?,?,?);");
            ps.setString(1,dept.getDepartment_id());
            ps.setString(2,dept.getDepartment_name());
            ps.setInt(3,dept.getLocation_id());
            ps.execute();
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {
            JdbcUtil.closeResource(ps,conn,null);
        }
    }
}
