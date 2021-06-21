package com.mthaler.springhibernate.config

import com.mthaler.springhibernate.dao.InstrumentDao
import com.mthaler.springhibernate.dao.SingerDao
import com.mthaler.springhibernate.entities.Album
import com.mthaler.springhibernate.entities.Instrument
import com.mthaler.springhibernate.entities.Singer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date
import java.util.*
import javax.annotation.PostConstruct

@Service
class DBInitializer {

    @Autowired
    private lateinit var singerDao: SingerDao
    @Autowired
    private lateinit var instrumentDao: InstrumentDao

    @PostConstruct
    public fun initDB(){
        logger.info("Starting database initialization...")

        val guitar = Instrument()
        guitar.instrumentId = "Guitar"
        instrumentDao.save(guitar)

        val piano = Instrument()
        piano.instrumentId = "Piano"
        instrumentDao.save(piano)

        val voice = Instrument()
        voice.instrumentId = "Voice"
        instrumentDao.save(voice)

        var singer = Singer()
        singer.firstName = "John"
        singer.lastName = "Mayer"
        singer.birthDate = Date(GregorianCalendar(1977, 9, 16).time.time)
        singer.addInstrument(guitar)
        singer.addInstrument(piano)

        val album1 = Album()
        album1.title = "The Search For Everything"
        album1.releaseDate = Date(
            GregorianCalendar(2017, 0, 20).time.time
        )
        singer.addAlbum(album1)

        val album2 = Album()
        album2.title = "Battle Studies"
        album2.releaseDate = Date(
            GregorianCalendar(2009, 10, 17).time.time
        )
        singer.addAlbum(album2)

        singerDao.save(singer)

        singer = Singer()
        singer.firstName = "Eric"
        singer.lastName = "Clapton"
        singer.birthDate = Date(GregorianCalendar(1945, 2, 30).time.time)
        singer.addInstrument(guitar)

        val album = Album()
        album.title = "From The Cradle"
        album.releaseDate = Date(
            GregorianCalendar(1994, 8, 13).time.time
        )
        singer.addAlbum(album)

        singerDao.save(singer)

        singer = Singer()
        singer.firstName = "John"
        singer.lastName = "Butler"
        singer.birthDate = Date(GregorianCalendar(1975, 3, 1).time.time)
        singer.addInstrument(guitar)

        singerDao.save(singer)
        logger.info("Database initialization finished.")
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(DBInitializer::class.java)
    }
}