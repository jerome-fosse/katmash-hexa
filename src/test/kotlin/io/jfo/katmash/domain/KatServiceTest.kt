package io.jfo.katmash.domain

import io.jfo.katmash.domain.exception.KatNotFoundException
import io.jfo.katmash.domain.spi.KatDataSource
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class KatServiceTest {

    private lateinit var service : KatService

    @BeforeEach
    fun beforeEach() {
        service = KatService(KatDataSourceMock())
    }

    @Test
    fun `voting for a cat that does not exists should throw a KatNotFoundException`() {
        // Given a Kat's id that is equal to 99
        val id = 99

        // When I vote for this kat
        val thrown = catchThrowable { service.voteForKatWithId(id).block() }

        // Then the exeception thrown should be a KatNotFoundException
        assertThat(thrown).isNotNull()
        assertThat(thrown).isInstanceOf(KatNotFoundException::class.java)
    }

    @Test
    fun `voting for an existing cat should increase his score by 1`() {
        // Given a Kat's id that is equal to 1
        val id = 1

        // When I vote for that kat
        val kat = service.voteForKatWithId(1).block()

        // Then his id is equal to 1
        assertThat(kat!!.id).isEqualTo(1)

        // And his score is equal to 1
        assertThat(kat.score).isEqualTo(1)
    }
}

private class KatDataSourceMock: KatDataSource {
    override fun getTwoRandomKats(): Flux<Kat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTenFirstKatsSortedByScore(): Flux<Kat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findKatById(id: Int): Mono<Kat> =
            when (id) {
                1 -> Mono.just(Kat(id = 1, url = "myurl", score = 0))
                else -> Mono.empty()
            }

    override fun save(kat: Kat): Mono<Kat> = Mono.just(kat)

}
