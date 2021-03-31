package service.impl;


import dao.DepartmentsDao;
import dao.impl.DepartmentsDaoImpl;
import pojo.Departments;
import service.DepartmentService;

public class DepartmentsServiceImpl implements DepartmentService {
    @Override
    public void addDepartments(Departments dept) {
        DepartmentsDao departmentsDao = new DepartmentsDaoImpl();
        departmentsDao.insertDept(dept);
    }
}
