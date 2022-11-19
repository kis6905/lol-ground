package com.leaf.lolground.infrastructure.api.lol.match.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Match(
    @JsonProperty("info")
    val matchInfo: MatchInfo,
)
