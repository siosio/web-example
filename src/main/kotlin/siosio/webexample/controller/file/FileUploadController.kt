package siosio.webexample.controller.file

import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.*
import siosio.webexample.file.*

@Controller
@RequestMapping("/file")
class FileUploadController(
    private val uploadFileStorageService: UploadFileStorageService
) {

    @GetMapping
    fun index(): String = "file/index"

    @PostMapping
    fun upload(@RequestParam("file") multipartFile: MultipartFile): String {
        println("multipartFile.originalFilename = ${multipartFile.originalFilename}")
        uploadFileStorageService.save(multipartFile)
        return "file/index"
    }
}