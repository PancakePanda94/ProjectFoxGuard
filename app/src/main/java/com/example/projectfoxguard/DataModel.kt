package com.example.projectfoxguard

import net.sourceforge.jtds.jdbc.DateTime

data class DataModel(
    val eventId: String,
    val eventName: String,
    val eventType: String,
    val eventLocation: String,
    val eventDate: String,
    val eventDescription: String
    )
