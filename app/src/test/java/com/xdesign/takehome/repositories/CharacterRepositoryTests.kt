package com.xdesign.takehome.repositories

import com.xdesign.takehome.api.CallMock
import com.xdesign.takehome.api.wrappers.ResourceFactory
import com.xdesign.takehome.data.models.ApiCharacter
import com.xdesign.takehome.data.repositories.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CharacterRepositoryTests : BaseRepositoryTests() {

    private val apiCharacter = ApiCharacter(
        "Jon Snow",
        "Male",
        "North",
        "200",
        "222",
        emptyList(),
        listOf("Season 1", "Season 2"),
        listOf("Kit")
    )

    private val characterResponseMock = listOf(apiCharacter)

    private var characterRepository: CharacterRepository? = null

    private val errorMessage = "error"
    private val contentType = "text/plain"
    private val errorCode = 400
    private val successCode = 200

    private val characterListResourceFactory = ResourceFactory<List<ApiCharacter>>()

    @Before
    fun setup() {
        characterAPI?.let {
            characterRepository = CharacterRepository(it)
        }
    }

    @Test
    fun test_get_character_data_success() = runBlocking {
        Mockito.`when`(characterAPI?.getCharacters())
            .thenReturn(CallMock.mockSuccessResponse(characterResponseMock))

        val response = characterRepository?.getCharacters()
        var characterListResults: List<ApiCharacter>? = null

        response?.let {
            characterListResourceFactory.getResource(it).data?.let { characterListResultsCallback ->
                characterListResults = characterListResultsCallback
            }
        }

        Assert.assertNotNull(characterListResults)
        Assert.assertEquals(response?.code, successCode)
        Assert.assertEquals(characterListResults?.get(0)?.name, "Jon Snow")
        Assert.assertEquals(characterListResults?.get(0)?.born, "200")
        Assert.assertEquals(characterListResults?.get(0)?.playedBy?.get(0), "Kit")
    }

    @Test
    fun test_get_character_data_error() = runBlocking {
        Mockito.`when`(characterAPI?.getCharacters())
            .thenReturn(CallMock.mockErrorResponse(errorCode, contentType, errorMessage))

        val response = characterRepository?.getCharacters()

        Assert.assertNotNull(response)
        Assert.assertNotNull(response?.code)
        Assert.assertEquals(response?.code, errorCode)
    }
}