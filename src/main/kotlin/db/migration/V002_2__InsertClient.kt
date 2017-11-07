package db.migration

import org.flywaydb.core.api.migration.jdbc.*
import java.sql.*

class V002_2__InsertClient : JdbcMigration {
    override fun migrate(connection: Connection) {
        connection.prepareStatement("insert into client (name) values (?)").use { ps ->
            (1..5).forEach {
                ps.setString(1, "顧客${it}")
                ps.addBatch()
            }
            ps.executeBatch()
        }

        connection.createStatement().use {
            it.executeQuery("select * from client").use {
                while (it.next()) {
                    println("it.getLong(1) = ${it.getLong(1)}")
                    println("it.getString(2) = ${it.getString(2)}")
                }
            }
        }
    }
}
