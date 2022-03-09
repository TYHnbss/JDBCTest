package TYH.PreparetStatement;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class PrepareStatementTest {
    @Test
//操作繁琐
    public void PrepareStatement_Test1(){
//获取连接
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            InputStream RAS = PrepareStatementTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(RAS);

            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            String driverClass = properties.getProperty("driverClass");

            Class.forName(driverClass);

            connection = DriverManager.getConnection(url,user,password);
//预编译
            String s = "insert into customers(name,email,birth) values(?,?,?)";
            preparedStatement = connection.prepareStatement(s);
//填充数据
            preparedStatement.setString(1,"王雄戊");
            preparedStatement.setString(2,"123@qq.com");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parse = simpleDateFormat.parse("2001-10-20");
            preparedStatement.setDate(3,new java.sql.Date(parse.getTime()));

            //执行操作
            preparedStatement.execute();
        } catch (IOException | ParseException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //关闭资源
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
    public void PrepareStatement_Test2 () throws Exception {

        //获取连接
        Connection connection = JDBCUtil.getConnection();

        //填充数据
        String s = "insert into customers(name,email,birth) values(?,?,?)";
        PreparedStatement preparedStatement =connection.prepareStatement(s);

        preparedStatement.setString(1,"许龙杰");
        preparedStatement.setString(2,"456@qq.com");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parse = simpleDateFormat.parse("2002-3-3");
        preparedStatement.setDate(3,new java.sql.Date(parse.getTime()));

        //执行操作
        preparedStatement.execute();

        //关闭连接
        JDBCUtil.closeConnection(connection,preparedStatement);
    }
    @Test
    public void customerQuery() throws Exception {
        Connection conn = JDBCUtil.getConnection();

        String sql = "select id,name,email,birth from customers";
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet resultSet = ps.executeQuery();

        if (resultSet.next()) {

            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            Date birth = resultSet.getDate(4);

//            System.out.println();
            Customers customers = new Customers(id, name, email, birth);
            System.out.println(customers);
            JDBCUtil.closeResult(conn,ps,resultSet);
        }

    }
}
