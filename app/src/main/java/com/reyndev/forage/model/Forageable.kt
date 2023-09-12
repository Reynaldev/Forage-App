package com.reyndev.forage.model

/**
 * Forageable entity to be stored in the forageable_database.
 */
// TODO: annotate this data class as an entity with a parameter for the table name
data class Forageable(
    // TODO: declare the id to be an autogenerated primary key
    val id: Long = 0,
    val name: String,
    val address: String,
    // TODO: make a custom column name for the inSeason variable that follows SQL column name
    //  conventions (the column name should be in_season)
    val inSeason: Boolean,
    val notes: String?
)