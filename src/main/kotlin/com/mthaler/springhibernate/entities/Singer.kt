package com.mthaler.springhibernate.entities

import java.text.SimpleDateFormat
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

@Entity
@Table(name = "singer")
@NamedQueries(
    NamedQuery(name=Singer.FIND_SINGER_BY_ID,
        query="select distinct s from Singer s " +
                "left join fetch s.albums a " +
                "left join fetch s.instruments i " +
                "where s.id = :id"),
    NamedQuery(name=Singer.FIND_ALL_WITH_ALBUM,
        query="select distinct s from Singer s " +
                "left join fetch s.albums a " +
                "left join fetch s.instruments i")
)
class Singer(): AbstractEntity() {

    @Column(name = "FIRST_NAME")
    var firstName: String? = null

    @Column(name = "LAST_NAME")
    var lastName: String? = null

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    var birthDate: Date? = null

    @OneToMany(mappedBy = "singer", cascade = [CascadeType.ALL], orphanRemoval = true)
    var albums: MutableSet<Album> = HashSet()

    @ManyToMany
    @JoinTable(
        name = "singer_instrument",
        joinColumns = [JoinColumn(name = "SINGER_ID")],
        inverseJoinColumns = [JoinColumn(name = "INSTRUMENT_ID")]
    )
    var instruments: MutableSet<Instrument> = HashSet()



    fun addAlbum(album: Album): Boolean {
        album.singer = this
        return albums.add(album)
    }

    fun removeAlbum(album: Album) {
        albums.remove(album)
    }

    fun addInstrument(instrument: Instrument): Boolean {
        return instruments.add(instrument)
    }

    override fun toString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return String.format(
            "Singer - id: %d, First name: %s, Last name: %s, Birthday: %s",
            id, firstName, lastName, sdf.format(birthDate)
        )
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        if (!super.equals(o)) return false
        val singer = o as Singer
        if (if (firstName != null) firstName != singer.firstName else singer.firstName != null) return false
        if (if (lastName != null) lastName != singer.lastName else singer.lastName != null) return false
        return if (birthDate != null) birthDate == singer.birthDate else singer.birthDate == null
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + if (firstName != null) firstName.hashCode() else 0
        result = 31 * result + if (lastName != null) lastName.hashCode() else 0
        result = 31 * result + if (birthDate != null) birthDate.hashCode() else 0
        return result
    }

    companion object {
        const val FIND_SINGER_BY_ID = "Singer.findById"
        const val FIND_ALL_WITH_ALBUM = "Singer.findAllWithAlbum"
    }
}