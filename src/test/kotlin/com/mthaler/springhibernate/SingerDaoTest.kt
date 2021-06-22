package com.mthaler.springhibernate

import com.mthaler.springhibernate.config.AdvancedConfig
import com.mthaler.springhibernate.dao.SingerDao
import com.mthaler.springhibernate.entities.Album
import com.mthaler.springhibernate.entities.Instrument
import com.mthaler.springhibernate.entities.Singer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericApplicationContext
import java.sql.Date
import java.util.*
import java.util.function.Consumer

class SingerDaoTest {

    @Test
    fun testFindAll() {
        val singers = singerDao.findAll()
        assertEquals(3, singers.size)
        listSingers(singers)
    }

    @Test
    fun testFindAllWithAlbum() {
        val singers = singerDao.findAllWithAlbum()
        assertEquals(3, singers.size)
        listSingersWithAlbum(singers)
    }

    @Test
    fun testFindByID() {
        val singer = singerDao.findById(1L)
        assertNotNull(singer)
        //logger.info(singer.toString())
        println(singer)
    }

    @Test
    fun testInsert() {
        val singer = Singer()
        singer.firstName = "BB"
        singer.lastName = "King"
        singer.birthDate = Date(
            GregorianCalendar(1940, 8, 16).time.time
        )
        var album = Album()
        album.title = "My Kind of Blues"
        album.releaseDate = Date(
            GregorianCalendar(1961, 7, 18).time.time
        )
        singer.addAlbum(album)
        album = Album()
        album.title = "A Heart Full of Blues"
        album.releaseDate = Date(
            GregorianCalendar(1962, 3, 20).time.time
        )
        singer.addAlbum(album)
        singerDao.save(singer)
        assertNotNull(singer.id)
        val singers = singerDao.findAllWithAlbum()
        //assertEquals(4, singers.size)
        listSingersWithAlbum(singers)
    }

    @Test
    fun testUpdate() {
        val singer = singerDao.findById(1L)
        if (singer != null) {
            //making sure such singer exists
            assertNotNull(singer)
            //making sure we got expected record
            assertEquals("Mayer", singer.lastName)
            //retrieve the album
            val album = singer.albums.stream().filter { a: Album -> a.title == "Battle Studies" }.findFirst().get()
            singer.firstName = "John Clayton"
            singer.removeAlbum(album)
            singerDao.save(singer)
            listSingersWithAlbum(singerDao.findAllWithAlbum())
        } else {
            println("SINGER NULL!!!")
        }
    }

    @Test
    fun testDelete() {
        val singer = singerDao.findById(4L)
        if (singer != null) {
            //making sure such singer exists
            assertNotNull(singer)
            singerDao.delete(singer)
            listSingersWithAlbum(singerDao.findAllWithAlbum())
        } else {
            println("SINGER NULL!!!")
        }
    }

    companion object {

        private val logger: Logger = LoggerFactory.getLogger(SingerDaoTest::class.java)

        private lateinit var ctx: GenericApplicationContext
        private lateinit var singerDao: SingerDao

        @JvmStatic
        @BeforeAll
        fun setUp() {
            ctx = AnnotationConfigApplicationContext(AdvancedConfig::class.java)
            singerDao = ctx.getBean(SingerDao::class.java)
            assertNotNull(singerDao)
        }

        @JvmStatic
        @AfterAll
        fun tearDown() {
            ctx.close()
        }


        private fun listSingers(singers: List<Singer>) {
            logger.info(" ---- Listing singers:")
            for (singer in singers) {
                //logger.info(singer.toString())
                println(singer)
            }
        }

        private fun listSingersWithAlbum(singers: List<Singer>) {
            logger.info(" ---- Listing singers with instruments:")
            singers.forEach(Consumer { s: Singer ->
                logger.info(s.toString())
                if (s.albums != null) {
                    s.albums.forEach(Consumer { a: Album -> logger.info("\t" + a.toString()) })
                }
                if (s.instruments != null) {
                    s.instruments.forEach(Consumer { i: Instrument ->
                        //logger.info("\tInstrument: " + i.instrumentId)
                        println("\tInstrument: " + i.instrumentId)
                    })
                }
            })
        }
    }
}