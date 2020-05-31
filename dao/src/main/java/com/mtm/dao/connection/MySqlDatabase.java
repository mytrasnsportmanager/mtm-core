package com.mtm.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Admin on 2/24/2019.
 */
public class MySqlDatabase implements Database {
    // DB Host 34.66.81.100

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtm","mtmwebuser", "mtm@123");
        return conn;
    }
}
