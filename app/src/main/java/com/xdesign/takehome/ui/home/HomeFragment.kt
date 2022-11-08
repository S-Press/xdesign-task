package com.xdesign.takehome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.xdesign.takehome.databinding.FragmentHomeBinding
import com.xdesign.takehome.models.ApiCharacter
import com.xdesign.takehome.ui.CharacterRecyclerViewAdapter
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

const val base_Url = "https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com"

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHomeBinding.inflate(inflater, container, false).apply {
        var retrofit = Retrofit.Builder().baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).client(OkHttpClient.Builder().build()).build()
        var service = retrofit.create(Service::class.java)
        viewLifecycleOwner.lifecycleScope.launch {
            var _characters = service.getCharacters("Bearer 754t!si@glcE2qmOFEcN")
            var charactersBody = _characters.body()!!
            characters.adapter = CharacterRecyclerViewAdapter(charactersBody)
        }
    }.root
}

interface Service {
    @GET("/characters")
    suspend fun getCharacters(@Header("Authorization") token: String): Response<List<ApiCharacter>>
}