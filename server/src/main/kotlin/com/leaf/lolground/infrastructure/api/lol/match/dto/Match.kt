package com.leaf.lolground.infrastructure.api.lol.match.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Match(
    val info: MatchInfo,
)
