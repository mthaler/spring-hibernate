package com.mthaler.springhibernate.entities

import javax.persistence.*

@Entity
@Table(name = "instrument")
class Instrument {

    @Id
    @Column(name = "INSTRUMENT_ID")
    var instrumentId: String? = null

    @ManyToMany
    @JoinTable(
        name = "singer_instrument",
        joinColumns = [JoinColumn(name = "INSTRUMENT_ID")],
        inverseJoinColumns = [JoinColumn(name = "SINGER_ID")]
    )
    var singers: Set<Singer> = HashSet()
}