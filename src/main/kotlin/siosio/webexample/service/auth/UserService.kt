package siosio.webexample.service.auth

import org.springframework.security.core.authority.*
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.*
import siosio.webexample.dao.user.*
import siosio.webexample.value.*

@Service
class UserService(private val userDao: UserDao) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val userId = UserId(username ?: throw UsernameNotFoundException(username))

        return userDao.findByUserId(userId)?.let {
            val roles = userDao.findRolesByUserId(userId)
            User(it.userId.value, it.password, roles.map { SimpleGrantedAuthority(it.role) })
        } ?: throw UsernameNotFoundException(username)
    }
}
