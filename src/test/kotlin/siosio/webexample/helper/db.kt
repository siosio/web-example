package siosio.webexample.helper

import org.seasar.doma.boot.autoconfigure.*
import java.sql.*

internal fun searchProject(domaConfig: DomaConfig): List<Map<String, Any>> {
    return query(domaConfig, "select * from project") {
        mapOf(
                "project_id" to it.getLong("project_id"),
                "name" to it.getString("name"),
                "type" to it.getString("type"),
                "client_id" to it.getLong("client_id")
        )
    }
}

internal fun searchProjectPeriod(domaConfig: DomaConfig): List<Map<String, Any>> {
    return query(domaConfig, "select * from project_period") {
        mapOf(
                "project_id" to it.getLong("project_id"),
                "start_date" to it.getString("start_date"),
                "end_date" to it.getString("end_date")
        )
    }
}

private fun query(domaConfig: DomaConfig, sql: String, block: (ResultSet) -> Map<String, Any>): List<Map<String, Any>> {
    return domaConfig.dataSource.connection.use {
        it.createStatement().use {
            it.executeQuery(sql).use {
                generateSequence { it }
                        .takeWhile { it.next() }
                        .map {
                            block(it)
                        }
                        .toList()
            }
        }
    }
}
