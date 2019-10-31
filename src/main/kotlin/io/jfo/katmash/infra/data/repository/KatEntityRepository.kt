package io.jfo.katmash.infra.data.repository

import io.jfo.katmash.infra.data.entity.KatEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface KatEntityRepository: ReactiveCrudRepository<KatEntity, Int>, KatEntityCustomRepository
