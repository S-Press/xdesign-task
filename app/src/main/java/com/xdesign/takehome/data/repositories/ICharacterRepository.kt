package com.xdesign.takehome.data.repositories

import com.xdesign.takehome.api.wrappers.ApiResponse
import com.xdesign.takehome.data.models.ApiCharacter

interface ICharacterRepository {
    suspend fun getCharacters(): ApiResponse<List<ApiCharacter>>
}