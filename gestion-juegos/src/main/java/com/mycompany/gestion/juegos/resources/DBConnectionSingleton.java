package com.mycompany.gestion.juegos.resources;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ronald
 */
public class DBConnectionSingleton {
    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "sistema_juegos"; 
    private static final String USER_NAME = "admin";
    private static final String PASSWORD = "Ronald01!";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static DBConnectionSingleton instance;
    private DataSource datasource;
    
    private DBConnectionSingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PoolProperties p = new PoolProperties();
            p.setUrl(URL);
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(USER_NAME);
            p.setPassword(PASSWORD);
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(50);
            p.setInitialSize(5);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(5);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer"
            );
            datasource = new DataSource(p);
        } catch (ClassNotFoundException ex) {
            System.getLogger(DBConnectionSingleton.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al cargar el driver JDBC", ex);
        }
    }

    //Método para obtener conexión
    public Connection getConnection() {
        try {
            return datasource.getConnection();
        } catch (SQLException ex) {
            System.getLogger(DBConnectionSingleton.class.getName())
                    .log(System.Logger.Level.ERROR, "Error al obtener la conexión", ex);
        }
        return null;
    }

    //Método estático para obtener la instancia única
    public static DBConnectionSingleton getInstance() {
        if (instance == null) {
            instance = new DBConnectionSingleton();
        }
        return instance;
    }
}