package com.leaf.lolground.infrastructure.api.lol.match.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Match(
    val info: MatchInfo?,
) {
    companion object {
        fun empty(): Match = Match(null)
    }

    var isEmpty: Boolean = true
        get() = this.info?.let { true } ?: false
}