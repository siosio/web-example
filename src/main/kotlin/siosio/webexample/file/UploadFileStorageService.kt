package siosio.webexample.file

import com.amazonaws.services.s3.*
import com.amazonaws.services.s3.model.*
import com.amazonaws.services.s3.transfer.*
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
    @Profile("default")
    fun local(): UploadFileStorageService {
        return object : UploadFileStorageService {
            override fun save(multipartFile: MultipartFile) {
                multipartFile.transferTo(File(saveDir, "uploadfile"))
            }
        }
    }

    @Bean
    @Profile("aws")
    fun s3(@Value("\${s3.bucket.name}") bucketName: String, s3: AmazonS3): UploadFileStorageService {
        val transferManager = TransferManagerBuilder.standard()
            .withS3Client(s3)
            .build()
        return object : UploadFileStorageService {
            override fun save(multipartFile: MultipartFile) {
                val waitForUploadResult = transferManager
                    .upload(bucketName, "$saveDir/uploadfile", multipartFile.inputStream, ObjectMetadata())
                    .waitForUploadResult()
                println("waitForUploadResult.eTag = ${waitForUploadResult.eTag}")
            }
        }
    }
}