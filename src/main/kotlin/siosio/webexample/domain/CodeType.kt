package siosio.webexample.domain

interface CodeType {
    val id: String
    val label: String

    fun toName(id: String) {

    }
}
