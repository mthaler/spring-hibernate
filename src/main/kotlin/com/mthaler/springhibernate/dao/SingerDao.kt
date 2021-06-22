package com.mthaler.springhibernate.dao

import com.mthaler.springhibernate.entities.Singer

interface SingerDao {
    fun findAll(): List<Singer>

    fun findAllWithAlbum(): List<Singer>

    fun findById(id: Long): Singer?

    fun save(singer: Singer): Singer

    fun delete(singer: Singer)
}