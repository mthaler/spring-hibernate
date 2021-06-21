package com.mthaler.springhibernate.dao

import com.mthaler.springhibernate.entities.Instrument
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@Transactional
@Repository("instrumentDao")
class InstrumentDaoImpl: InstrumentDao {

    private lateinit var sessionFactory: SessionFactory

    fun getSessionFactory(): SessionFactory {
        return sessionFactory
    }

    @Resource(name = "sessionFactory")
    fun setSessionFactory(sessionFactory: SessionFactory) {
        this.sessionFactory = sessionFactory
    }

    override fun save(instrument: Instrument): Instrument {
        sessionFactory.getCurrentSession().saveOrUpdate(instrument);
        logger.info("Instrument saved with id: " + instrument.instrumentId);
        return instrument;
    }

    companion object {
        private val logger = LogFactory.getLog(InstrumentDaoImpl::class.java)
    }
}