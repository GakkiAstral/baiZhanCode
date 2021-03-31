package dao.impl;

import commons.JdbcUtil;
import dao.BaseDao;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;

public class BaseDaoImpl implements BaseDao {

    /**
     * 封装通用的DML操作
     */

    @Override
    public int executeUpdate(String sql, Object[] param) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try{
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement(sql);
            //得到参数的个数
            ParameterMetaData pmd = ps.getParameterMetaData();
            //绑定参数
            for (int i = 0;i<pmd.getParameterCount();i++){
                ps.setObject(i+1,param[i]);
            }
            return rows;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeResource(ps,conn,null);
        }


        return 0;
    }
}
