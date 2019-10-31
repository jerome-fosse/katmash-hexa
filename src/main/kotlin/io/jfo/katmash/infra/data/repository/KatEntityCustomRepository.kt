package io.jfo.katmash.infra.data.repository

import io.jfo.katmash.infra.data.entity.KatEntity
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

interface KatEntityCustomRepository {

    /**
     * Get a pair of kats
     *
     * @return a pair of katsEntity
     */
    fun findTwoRandomKats(): Flux<KatEntity>

    /**
     * Return the n first kats ordered by score
     *
     * @param limit the number of kats to return
     * @return a Flux of kats
     */
    fun findKatOrderByScoreDescLimitBy(limit: Long): Flux<KatEntity>
}

@Repository
class KatEntityCustomRepositoryImpl(private val databaseClient: DatabaseClient): KatEntityCustomRepository {

    override fun findTwoRandomKats(): Flux<KatEntity> {
        return databaseClient.select()
                .from(KatEntity::class.java)
                .orderBy(Sort.by("random()").descending())
                .`as`(KatEntity::class.java)
                .all()
                .limitRequest(2)
    }

    override fun findKatOrderByScoreDescLimitBy(limit: Long): Flux<KatEntity> {
        return databaseClient.select()
                .from(KatEntity::class.java)
                .orderBy(Sort.by("score").descending())
                .`as`(KatEntity::class.java)
                .all()
                .limitRequest(limit)
    }
}
