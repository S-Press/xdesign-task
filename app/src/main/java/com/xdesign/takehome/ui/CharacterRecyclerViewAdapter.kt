package com.xdesign.takehome.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xdesign.takehome.databinding.FragmentCharacterBinding
import com.xdesign.takehome.models.ApiCharacter

class CharacterRecyclerViewAdapter(
    private val values: List<ApiCharacter>
) : RecyclerView.Adapter<CharacterRecyclerViewAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {

        return CharacterViewHolder(
            FragmentCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(values[position])

    override fun getItemCount(): Int = values.size

    inner class CharacterViewHolder(private val binding: FragmentCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(c: ApiCharacter) {
            binding.apply {
                character = c
                born.text = c.born
                culture.text = c.culture
                died.text = c.died
                seasons.text = c.tvSeries.joinToString {
                    when (it) {
                        "Season 1" -> "I "
                        "Season 2" -> "II, "
                        "Season 3" -> "III, "
                        "Season 4" -> "IV, "
                        "Season 5" -> "V, "
                        "Season 6" -> "VI, "
                        "Season 7" -> "VII, "
                        "Season 8" -> "VIII"
                        else -> ""
                    }
                }
            }
        }
    }
}

