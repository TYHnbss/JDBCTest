package TYH.PreparetStatement;

import com.mysql.cj.protocol.Resultset;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    /**\
     * 基于获取连接和关闭连接的频繁操作
     * 我们可以将获取连接和关闭连接的方法封装
     *
     * 对于数据的填充也是一个频繁的操作
     * 我们可以将此封装为方法，施行增删改
     * */
//获取连接
    public static Connection getConnection() throws Exception {
            InputStream RAS = PrepareStatementTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(RAS);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);

            return DriverManager.getConnection(url,user,password);
    }

    //关闭连接
    public static void closeConnection(Connection connection, Statement preparedStatement){
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Test
    public static void closeResult(Connection connection, Statement preparedStatement, ResultSet rs){
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert connection != null;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            assert rs != null;
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //增删改方法
    public static void upDate(String sql,Object...args){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtil.getConnection();
            ps = connection.prepareStatement(sql);
        //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1,args[i]);
            }
            //执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭连接
        JDBCUtil.closeConnection(connection,ps);
    }
}
