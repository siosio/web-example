package db.migration

import org.flywaydb.core.api.migration.jdbc.*
import org.springframework.security.crypto.bcrypt.*
import java.sql.*

class V001_1__InsertAdminUser : JdbcMigration {

    private val passwordEncoder = BCryptPasswordEncoder()

    override fun migrate(connection: Connection) {
        connection.prepareStatement("insert into user(user_id, password) values (?, ?)").use {
            it.setString(1, "admin")
            it.setString(2, passwordEncoder.encode("admin"))
            it.executeUpdate()
        }

        connection.prepareStatement("insert into user_roles(user_id, role) values (?, ?)").use {
            it.setString(1, "admin")
            it.setString(2, "ROLE_ADMIN")
            it.executeUpdate()
        }
    }

}
