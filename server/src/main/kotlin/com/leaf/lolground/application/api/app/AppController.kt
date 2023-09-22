package com.leaf.lolground.application.api.app

import com.leaf.lolground.application.annotations.ApiController
import com.leaf.lolground.domain.app.AppService
import com.leaf.lolground.domain.app.dto.AppDto
import com.leaf.lolground.infrastructure.exceptions.NotFoundException
import com.leaf.lolground.infrastructure.helper.logger
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.servlet.http.HttpServletRequest

@ApiController
class AppController(
    val appService: AppService,
) {

    @PostMapping("/app")
    fun postApp(@RequestBody appDto: AppDto, request: HttpServletRequest): ResponseEntity<AppDto?> {
        return try {
            val saved = appService.save(appDto, request.remoteAddr)
            ResponseEntity.ok(saved)
        } catch (e: DuplicateKeyException) {
            logger.warn("", e)
            ResponseEntity
                .status(HttpStatus.CONFLICT)
                .build()
        }
    }

    @PutMapping("/app/fcmToken")
    fun putFcmToken(@RequestBody appDto: AppDto, request: HttpServletRequest): ResponseEntity<AppDto?> {
        return try {
            val saved = appService.updateFcmToken(appDto, request.remoteAddr)
            ResponseEntity.ok(saved)
        } catch (e: NotFoundException) {
            logger.warn("", e)
            ResponseEntity
                .notFound()
                .build()
        }
    }

}
