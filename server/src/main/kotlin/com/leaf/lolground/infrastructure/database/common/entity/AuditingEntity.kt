package com.leaf.lolground.infrastructure.database.common.entity

import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditingEntity(
    @Column(name = "createdBy", nullable = false)
    var createdBy: String,

    @CreationTimestamp
    @CreatedDate
    @Column(name = "createdAt", nullable = false)
    var createdAt: LocalDateTime? = null,

    @Column(name = "modifiedBy", nullable = false)
    var modifiedBy: String? = null,

    @CreationTimestamp
    @LastModifiedDate
    @Column(name = "modifiedAt", nullable = false)
    var modifiedAt: LocalDateTime? = null,
)
