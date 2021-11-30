package com.main.skriblio.data.remote.ws.models

import com.main.skriblio.util.Constants.TYPE_CHOSEN_WORD

data class ChosenWord(
    val chosenWord: String,
    val roomName: String
): BaseModel(TYPE_CHOSEN_WORD)
