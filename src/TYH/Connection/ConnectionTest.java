package TYH.Connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
/**
 * 方式一：
 * */
    @Test
    public void connection_test() throws SQLException {
        //加载驱动
        Driver driver = new com.mysql.cj.jdbc.Driver();
        // 用于标识一个被注册的驱动程序，驱动程序管理器通过这个 URL 选择正确的
        //驱动程序，从而建立到数据库的连接。
        /*
        *  JDBC URL的标准由三部分组成，各部分间用冒号分隔。
 jdbc:子协议:子名称
 协议：JDBC URL中的协议总是jdbc
 子协议：子协议用于标识一个数据库驱动程序
 子名称：一种标识数据库的方法。子名称可以依不同的子协议而变化，用子名称的目的是为
了定位数据库提供足够的信息。包含主机名(对应服务端的ip地址)，端口号，数据库名
        *
        * */
        String url = "jdbc:mysql://localhost:3306/test";
        //将用户名和密码封装在Properties
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","t177133");

        Connection conn = driver.connect(url,info);

        System.out.println(conn);

    }

    /**
     *
     * 方式二：对方式一的迭代
     * */
    @Test
    public void connection_test1() throws Exception {
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver)clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test";
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","t177133");
        Connection connect = driver.connect(url, info);

        System.out.println(connect);
    }
//方式三：使用DriverManager替换Driver
    @Test
    public void connection_test2() throws Exception {
        //获取Driver类的实现类
        Class<?> clazz = Class.forName("com.mysql.cj.jdbc.Driver");

        Driver driver = (Driver) clazz.newInstance();

        //提供另外三个连接的信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "t177133";

        //注册驱动
        DriverManager.registerDriver(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }


    //方式四：
    @Test
    public void connection_test3() throws Exception {
        //加载Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
//得到driver对象
//      Driver driver = (Driver) clazz.newInstance();

        //提供另外三个连接的信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "t177133";

//        //注册驱动
//        DriverManager.registerDriver(driver);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);

    }

    //方式五：将数据库连接需要的四个基本信息放入配置文件，读取配置文件获取连接
    @Test
    public void connection_test4() throws Exception {
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(is);

        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driverClass = properties.getProperty("driverClass");

        //加载驱动C
        Class.forName(driverClass);

        //获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
    }

}
