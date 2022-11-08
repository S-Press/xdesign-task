package com.xdesign.takehome.data.repositories

import com.xdesign.takehome.api.CharacterAPI
import com.xdesign.takehome.api.wrappers.ApiResponse
import com.xdesign.takehome.data.models.ApiCharacter
import javax.inject.Singleton

@Singleton
class CharacterRepository(
    private val characterAPI: CharacterAPI
) : ICharacterRepository {
    override suspend fun getCharacters(): ApiResponse<List<ApiCharacter>> {
        return ApiResponse(characterAPI.getCharacters())
    }
}