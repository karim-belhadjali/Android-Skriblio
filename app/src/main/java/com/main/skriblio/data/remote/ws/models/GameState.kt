package com.main.skriblio.data.remote.ws.models

import com.main.skriblio.util.Constants.TYPE_GAME_STATE

data class GameState(
    val drawingPlayer: String,
    val word: String
): BaseModel(TYPE_GAME_STATE)
