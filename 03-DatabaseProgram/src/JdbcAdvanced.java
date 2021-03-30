import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Jdbc进阶部分
 */
public class JdbcAdvanced {
    //动态查询
    public List<Departments> selectDeptByProperty(Departments departments) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Departments> list = new ArrayList<>();
        try {
            conn = JdbcUtil.getConnection();
            String sql = genSQL(departments);
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Departments dept = new Departments();
                dept.setDepartment_id(rs.getString("department_id"));
                dept.setDepartment_name(rs.getString("department_name"));
                dept.setLocation_id(rs.getInt("location_id"));
                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeResource(ps, conn, rs);
        }
        return list;
    }

    //拼接需要执行的SQL语句
    private String genSQL(Departments dept) {
        StringBuffer sb = new StringBuffer("select * from departments where 1=1");

        if (dept.getDepartment_id() != null && dept.getDepartment_id().length() > 0) {
            sb.append(" and department_id = '").append(dept.getDepartment_id()).append("'");
        }
        if (dept.getDepartment_name() != null && dept.getDepartment_name().length() > 0) {
            sb.append(" and department_name = '").append(dept.getDepartment_name()).append("'");
        }
        if (dept.getLocation_id() > 0) {
            sb.append(" and location_id = ").append(dept.getLocation_id());
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        JdbcAdvanced ad = new JdbcAdvanced();
        Departments dept = new Departments();
        //dept.setDepartment_name("研发部运维");
        List<Departments> list = ad.selectDeptByProperty(dept);
        for (Departments d:list){
            System.out.println(d.getDepartment_id()+" "+d.getDepartment_name()+" "+d.getLocation_id());
        }
    }
}
