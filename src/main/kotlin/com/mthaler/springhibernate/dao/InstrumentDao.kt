package com.mthaler.springhibernate.dao

import com.mthaler.springhibernate.entities.Instrument

interface InstrumentDao {

    fun save(instrument: Instrument): Instrument
}