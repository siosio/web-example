package siosio.webexample.controller.client

import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import siosio.webexample.entity.*
import siosio.webexample.service.client.*

@RestController
@RequestMapping("/clients")
class ClientRestController(private val clientService: ClientService) {

    @GetMapping("/search", produces = arrayOf(MediaType.APPLICATION_JSON_UTF8_VALUE))
    fun search(): List<ClientEntity> {
        return clientService.searchClients()
    }
}
