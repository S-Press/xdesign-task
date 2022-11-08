package com.xdesign.takehome.api

import com.xdesign.takehome.data.models.ApiCharacter
import retrofit2.Call
import retrofit2.http.GET

interface CharacterAPI {

    @GET("characters")
    fun getCharacters(): Call<List<ApiCharacter>>
}