package com.main.skriblio.data.remote.ws.models

import com.main.skriblio.util.Constants.TYPE_GAME_ERROR

data class GameError(
    val errorType: Int
): BaseModel(TYPE_GAME_ERROR) {

    companion object {

        const val ERROR_ROOM_NOT_FOUND = 0
    }
}
