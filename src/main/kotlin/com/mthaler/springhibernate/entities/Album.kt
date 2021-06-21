package com.mthaler.springhibernate.entities

import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "album")
class Album() : AbstractEntity() {

    @Column
    var title: String? = null

    @Temporal(TemporalType.DATE)
    @Column(name = "RELEASE_DATE")
    var releaseDate: Date? = null

    @ManyToOne
    @JoinColumn(name = "SINGER_ID")
    var singer: Singer? = null

    override fun toString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return String.format(
            "Album - id: %d, Singer id: %d, Title: %s, Release Date: %s",
            id, singer!!.id, title, sdf.format(releaseDate)
        )
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val album = o as Album
        if (if (title != null) title != album.title else album.title != null) return false
        return if (releaseDate != null) releaseDate!!.equals(album.releaseDate) else album.releaseDate == null
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + if (title != null) title.hashCode() else 0
        result = 31 * result + if (releaseDate != null) releaseDate.hashCode() else 0
        return result
    }
}