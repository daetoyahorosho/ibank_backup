package org.example;

import java.sql.SQLException;

public interface SchemaDeleter {

    void deleteSchema() throws SQLException;

}
