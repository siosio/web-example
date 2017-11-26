package siosio.webexample.service.client

import org.springframework.stereotype.*
import siosio.webexample.dao.project.*
import siosio.webexample.entity.*

@Service
class ClientService (private val clientDao: ClientDao){
    
    fun searchClients(): List<ClientEntity> = clientDao.searchClients()
}
