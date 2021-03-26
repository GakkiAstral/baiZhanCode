import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcTest {
    //向Departments表中添加一条数据
    public void insertDepartments(String department_id,String department_name, int location_id) {
        Connection conn = null;
        Statement state = null;
        try {
            conn = JdbcUtil.getConnection();
            String sql = "insert into departments values('"+department_id+"','" + department_name + "'," + location_id + ")";
            state = conn.createStatement();
            int flag = state.executeUpdate(sql);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(state, conn, null);
        }
    }

    //更新departments表中的department_id为6的数据，将部门名称修改为教学部，location_id修改为6
    public void updateDempartments(String department_name, int location_id, int department_id) {
        Connection conn = null;
        Statement state = null;
        try {
            conn = JdbcUtil.getConnection();
            state = conn.createStatement();
            String sql = "update departments d set d.department_name = '" + department_name + "',d.location_id = " + location_id + " where d.department_id =" + department_id;
            int flag = state.executeUpdate(sql);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(state, conn, null);
        }
    }

    //查询Departmetns表中部门ID为6的部门信息
    public void selectDepartmentsById(int departmentId) {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            state = conn.createStatement();
            String sql = "select * from departments d where d.department_id = " + departmentId;
            //执行查询返回结果
            rs = state.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("department_id") + " " + rs.getString("department_name") + " " + rs.getInt(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(state, conn, rs);
        }
    }

    //查询departments表中的所有的数据，并且通过ResultSet实现逻辑分页
    public void selectDeptPage(int currentPage, int pageRows) {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            state = conn.createStatement();
            String sql = "select * from departments";
            rs = state.executeQuery(sql);
            //开始位置与结束位置
            int begin = (currentPage - 1) * pageRows;
            int end = currentPage * pageRows;

            //当前位置的计数器
            int currentNum = 0;
            while (rs.next()) {
                //什么情况下获取结果集中的数据
                if (currentNum >= begin && currentNum < end) {
                    System.out.println(rs.getInt("department_id") + " " + rs.getString("department_name"));
                    //结束操作ResultSet的边界条件
                    if (currentNum == end - 1) {
                        break;
                    }
                }
                currentNum++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(state, conn, rs);
        }
    }

    //sql注入
    public void sqlInject(String departmentName, int locationId) {
        Connection conn = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getConnection();
            state = conn.createStatement();
            String sql = "select * from departments where department_name ='" + departmentName + "' and location_id = " + locationId;
            System.out.println(sql);
            rs = state.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("department_id") + " " + rs.getString("department_name") + " " + rs.getInt("location_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(state, conn, rs);
        }
    }

    public static void main(String[] args) {
        JdbcTest test = new JdbcTest();
        //test.insertDepartments("65535","教学部", 9);
        test.updateDempartments("研发部", 8, 65535);
        //test.selectDepartmentsById(6);
        //test.sqlInject("研发部' or 1=1 -- ", 8);
    }
}
