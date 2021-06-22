package com.mthaler.springhibernate.dao

import com.mthaler.springhibernate.entities.Singer
import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

@SuppressWarnings("unchecked")
@Transactional
@Repository("singerDao")
class SingerDaoImpl: SingerDao {

    private lateinit var sessionFactory: SessionFactory

    fun getSessionFactory(): SessionFactory? {
        return sessionFactory
    }

    @Resource(name = "sessionFactory")
    fun setSessionFactory(sessionFactory: SessionFactory) {
        this.sessionFactory = sessionFactory
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<Singer> {
        return sessionFactory.currentSession.createQuery("from Singer s").list() as List<Singer>
    }

    @Transactional(readOnly = true)
    override fun findAllWithAlbum(): List<Singer> {
        return sessionFactory.currentSession.getNamedQuery("Singer.findAllWithAlbum").list() as List<Singer>
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): Singer? {
        return sessionFactory.currentSession.getNamedQuery("Singer.findById").setParameter("id", id)
            .uniqueResult() as Singer?
    }

    override fun save(singer: Singer): Singer {
        sessionFactory.currentSession.saveOrUpdate(singer)
        logger.info("Singer saved with id: " + singer.id)
        return singer
    }

    override fun delete(singer: Singer) {
        sessionFactory.currentSession.delete(singer)
        logger.info("Singer deleted with id: " + singer.id)
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SingerDaoImpl::class.java)
    }
}