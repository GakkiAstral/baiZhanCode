package dao;

import pojo.Departments;

import java.util.List;

public interface DepartmentsDao extends BaseDao{

    public List<Departments> selectDeptByName(String deptName);
    public void insertDept(Departments dept);


}
