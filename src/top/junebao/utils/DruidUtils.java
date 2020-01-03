package top.junebao.utils;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtils {
    private static DataSource dataSource = null;
    static {
        Properties pro = new Properties();
        try {
            pro.load(DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回数据源对象
     * @return dataSource
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 返回一个连接对象
     * @return Connection/null
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        if(dataSource != null) {
            return dataSource.getConnection();
        } else {
            return null;
        }
    }

    /**
     * 释放资源
     * @param connection 数据库连接对象
     * @param statement 执行SQL对象
     */
    public static void close(Connection connection, Statement statement) {
        if(statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(connection != null) {
            try {
                connection.close();  // 归还数据库连接池连接对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放/归还资源
     * @param connection 数据库连接对象
     * @param statement 执行SQL对象
     * @param resultSet 查询结果集
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(connection, statement);
        if(resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
