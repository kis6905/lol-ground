package com.leaf.lolground.infrastructure.database.app.entity

import com.leaf.lolground.infrastructure.database.common.entity.AuditingEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "app")
@Entity
class App(
    @Id
    @Column(name = "appId", nullable = false)
    var appId: String,

    @Column(name = "fcmToken", nullable = true)
    var fcmToken: String?,

    @Column(name = "ip", nullable = true)
    var ip: String?,

    @Column(name = "lastLoginAt", nullable = true)
    var lastLoginAt: LocalDateTime?,

    @Transient
    var _createdBy: String,
    @Transient
    var _modifiedBy: String,

): AuditingEntity(
    createdBy = _createdBy,
    modifiedBy = _modifiedBy
)