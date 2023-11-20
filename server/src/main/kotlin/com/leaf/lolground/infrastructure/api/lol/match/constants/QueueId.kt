package com.leaf.lolground.infrastructure.api.lol.match.constants

enum class QueueId(
    val id: Int
) {
    SOLO_RANK(420),
    NORMAL(430),
    FREE_RANK(440),
    ;

    companion object {
        fun isSoloRank(queueId: Int): Boolean {
            return SOLO_RANK.id == queueId
        }
    }
}
