package com.xdesign.takehome.repositories

import com.xdesign.takehome.api.CharacterAPI
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

open class BaseRepositoryTests {

    @Mock
    var characterAPI: CharacterAPI? = null

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
    }
}