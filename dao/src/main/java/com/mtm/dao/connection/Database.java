package com.mtm.dao.connection;

import java.sql.Connection;

/**
 * Created by Admin on 2/24/2019.
 */
public interface Database {

    public Connection getConnection() throws  Exception;


}
