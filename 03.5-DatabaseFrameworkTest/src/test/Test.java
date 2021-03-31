package test;

import pojo.Departments;
import service.DepartmentService;
import service.impl.DepartmentsServiceImpl;

public class Test {
    public static void main(String[] args) {
        Departments dept = new Departments();
        dept.setDepartment_id("100");
        dept.setDepartment_name("研发部");
        dept.setLocation_id(100);
        DepartmentService ds = new DepartmentsServiceImpl();
        ds.addDepartments(dept);
    }
}
