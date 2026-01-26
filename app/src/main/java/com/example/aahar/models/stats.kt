package com.example.aahar.models

data class Stats(
    val updated: Long,
    val cases: Long,
    val todayCases: Int,
    val deaths: Long,
    val todayDeaths: Int,
    val recovered: Long,
    val todayRecovered: Int,
    val active: Long,
    val critical: Int,
    val casesPerOneMillion: Double,
    val deathsPerOneMillion: Double,
    val tests: Long,
    val testsPerOneMillion: Double,
    val population: Long,
    val oneCasePerPeople: Int,
    val oneDeathPerPeople: Int,
    val oneTestPerPeople: Int,
    val activePerOneMillion: Double,
    val recoveredPerOneMillion: Double,
    val criticalPerOneMillion: Double,
    val affectedCountries: Int
)
