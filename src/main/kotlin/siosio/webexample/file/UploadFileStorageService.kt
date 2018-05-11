package siosio.webexample.file

import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.*
import org.springframework.stereotype.*
import org.springframework.web.multipart.*
import java.io.*

@Service
interface UploadFileStorageService {
    fun save(multipartFile: MultipartFile)
}

@Configuration
class UploadFileStorageConfiguration {

    @Value("\${file.upload.save.dir}")
    private lateinit var saveDir: String

    @Bean
    fun local(): UploadFileStorageService {
        return object : UploadFileStorageService {
            override fun save(multipartFile: MultipartFile) {
                multipartFile.transferTo(File(saveDir, "uploadfile"))
            }
        }
    }
}