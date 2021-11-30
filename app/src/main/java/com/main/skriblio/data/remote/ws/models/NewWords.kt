package com.main.skriblio.data.remote.ws.models

import com.main.skriblio.util.Constants.TYPE_NEW_WORDS

data class NewWords(
    val newWords: List<String>
): BaseModel(TYPE_NEW_WORDS)
