package com.mazer.bongobet.domain.entities.pojo

import android.os.Parcelable
import com.mazer.bongobet.domain.entities.LiveMatchHistory
import com.mazer.bongobet.domain.entities.MatchHistory
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchHistoryResponse(
    val summoners_rift: List<MatchHistory>,
    val live_match: LiveMatchHistory? = null
): Parcelable
