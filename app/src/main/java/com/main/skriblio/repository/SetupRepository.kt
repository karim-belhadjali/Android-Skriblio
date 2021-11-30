package com.main.skriblio.repository

import com.main.skriblio.data.remote.ws.Room
import com.main.skriblio.util.Resource

interface SetupRepository {

    suspend fun createRoom(room: Room): Resource<Unit>

    suspend fun getRooms(searchQuery: String): Resource<List<Room>>

    suspend fun joinRoom(username: String, roomName: String): Resource<Unit>
}