package io.jfo.katmash.infra.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("kats")
data class KatEntity(
        @Id
        val id: Int,
        val url: String,
        val score: Int
)
