package com.leaf.lolground.domain.match.dto

data class RecentMatch(
    val win: Boolean, // TODO: 다시하기가 있다... data 확인해서 수정 필요
    val playedAgo: String,
)
