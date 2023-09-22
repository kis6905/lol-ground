package com.leaf.lolground.infrastructure.database.app.repository

import com.leaf.lolground.infrastructure.database.app.entity.App
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppRepository: JpaRepository<App, String> {
}