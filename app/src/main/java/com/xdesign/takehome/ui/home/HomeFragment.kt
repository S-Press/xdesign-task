package com.xdesign.takehome.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xdesign.takehome.base.BaseFragment
import com.xdesign.takehome.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    lateinit var characterAdapter: CharacterRecyclerViewAdapter

    private val observers by lazy {
        viewModel.errorLiveData.observe(this) {
            it?.let {
                showError(it)
            }
        }

        viewModel.characterListLiveData.observe(this) {
            it?.let { list ->
                characterAdapter = CharacterRecyclerViewAdapter(list)
                binding.characters.adapter = characterAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener { viewModel.getCharacters() }

        observers
        viewModel.getCharacters()
        setSearchQueryListener()
    }

    private fun setSearchQueryListener() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    characterAdapter.filter(query)
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    characterAdapter.filter(query)
                    return false
                }
            })
        }
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Okay") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}