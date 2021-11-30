package com.main.skriblio.data.remote.ws.models

import com.main.skriblio.data.remote.ws.Room
import com.main.skriblio.util.Constants.TYPE_PHASE_CHANGE

data class PhaseChange(
        var phase: Room.Phase?,
        var time: Long,
        val drawingPlayer: String? = null
) : BaseModel(TYPE_PHASE_CHANGE)
