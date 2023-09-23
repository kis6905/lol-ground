package com.leaf.lolground.infrastructure.database.subscriber.entity

import com.leaf.lolground.infrastructure.database.common.entity.AuditingEntity
import javax.persistence.*
import kotlin.jvm.Transient

@Table(name = "subscriber")
@Entity
class Subscriber(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriberId", nullable = false)
    var subscriberId: Long = 0,

    @Column(name = "summonerName", nullable = true)
    var summonerName: String?,

    @Column(name = "appId", nullable = true)
    var appId: String?,

    @Transient
    var _createdBy: String,
    @Transient
    var _modifiedBy: String,
): AuditingEntity(
    createdBy = _createdBy,
    modifiedBy = _modifiedBy
)
