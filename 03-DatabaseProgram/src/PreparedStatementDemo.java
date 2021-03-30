import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PreparedStatement接口测试使用
 */
public class PreparedStatementDemo {
    //向departments表中数据插入
    public void insertDepartments(String department_id, String department_name, int location_id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("insert into departments values(?,?,?)");
            //占位符定义
            ps.setString(1, department_id);
            ps.setString(2, department_name);
            ps.setInt(3, location_id);
            //占位符复制
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(ps, conn, null);
            //父接口用子接口对象也可以的
        }
    }

    //更新数据
    public void updateDepartment(String department_id, String department_name, int location_id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("update departments set department_name = ?,location_id = ? where department_id = ?");
            ps.setString(1, department_name);
            ps.setInt(2, location_id);
            ps.setString(3, department_id);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(ps, conn, null);
        }
    }

    //查询数据,不做输出控制台，而是查询结果返回，需要一个数据模型类
    //查询返回的是单条结果集
    public Departments selectDepartmentsById(String department_id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Departments dept = null;
        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("select * from departments where department_id = ?");
            ps.setString(1, department_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                dept = new Departments();
                dept.setDepartment_id(rs.getString("department_id"));
                dept.setDepartment_name(rs.getString("department_name"));
                dept.setLocation_id(rs.getInt("location_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(ps, conn, rs);
        }
        return dept;
    }

    //查询数据,查询部门表中的部门名称，找到那些包含人力的部门信息
    //查询返回的是多条结果集
    public List<Departments> selectDepartmentsByLikeName(String department_name) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Departments> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("select * from departments where department_name like ?");
            ps.setString(1, "%" + department_name + "%");
            //sql里面%'name'%,所以要拼接？里面的内容，不拼接的话会产生错误
            rs = ps.executeQuery();
            while (rs.next()) {
                Departments dept = new Departments();
                //dept需要在while循环里面，否则会出现永远只有一条数据。
                dept.setDepartment_id(rs.getString("department_id"));
                dept.setDepartment_name(rs.getString("department_name"));
                dept.setLocation_id(rs.getInt("location_id"));
                list.add(dept);
                //dept只能在循环里面插入
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(ps, conn, rs);
        }
        return list;
    }

    //批量添加数据
    public void addBatch(List<Departments> list) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement("insert into departments values(?,?,?)");
            for (int i = 0; i < list.size(); i++) {
                ps.setString(1, list.get(i).getDepartment_id());
                ps.setString(2, list.get(i).getDepartment_name());
                ps.setInt(3, list.get(i).getLocation_id());
                //添加批处理
                ps.addBatch();
            }
            int[] arr = ps.executeBatch();
            for (int i = 0; i < arr.length; i++) {
                System.out.println(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(ps, conn, null);
        }
    }

    //事务处理
    public void deleteDepartments(String department_name){
        Connection conn = null;
        PreparedStatement ps = null;
        try{
            conn = JdbcUtil.getConnection();
            //关闭事务的自动提交
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("delete from departments where department_name like ?");
            ps.setString(1,"%"+department_name+"%");
            ps.executeUpdate();
            conn.commit();
        }catch (Exception e){
            e.printStackTrace();
            JdbcUtil.rollback(conn);
        }finally {
            JdbcUtil.closeResource(ps,conn,null);
        }
    }

    public static void main(String[] args) {
        PreparedStatementDemo preparedStatementDemo = new PreparedStatementDemo();
        //preparedStatementDemo.insertDepartments("6666", "软件测试", 78);
        //preparedStatementDemo.updateDepartment("666", "研发部运维"d, 88);

        /*Departments dept = preparedStatementDemo.selectDepartmentsById("666");
        if (dept != null) {
            System.out.println(dept.getDepartment_id() + " " + dept.getDepartment_name() + " " + dept.getLocation_id());
        }*/

        /*List<Departments> list = preparedStatementDemo.selectDepartmentsByLikeName("研发");
        for (Departments dept : list) {
            System.out.println(dept.getDepartment_id() + " " + dept.getDepartment_name() + " " + dept.getLocation_id());
        }*/

        /*List<Departments> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Departments dept = new Departments();
            dept.setDepartment_id("innover1" + i);
            dept.setDepartment_name("产品部运维" + i);
            dept.setLocation_id(30 + i);
            list.add(dept);
        }
        preparedStatementDemo.addBatch(list);*/

        preparedStatementDemo.deleteDepartments("产品部运维");
    }
}
