package com.mthaler.springhibernate.entities

import java.io.Serializable
import javax.persistence.*


@MappedSuperclass
abstract class AbstractEntity: Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    var id: Long? = null

    @Version
    @Column(name = "VERSION")
    private var version = 0


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as AbstractEntity
        return if (if (id != null) id != that.id else that.id != null) false else true
    }

    override fun hashCode(): Int {
        return if (id != null) id.hashCode() else 0
    }
}