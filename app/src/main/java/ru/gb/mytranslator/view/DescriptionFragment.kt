package ru.gb.mytranslator.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import geekbrains.ru.translator.databinding.FragmentDescriptionBinding
import ru.gb.data.DataModel

class DescriptionFragment(val data: DataModel) : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.descriptionHeader.text = data.text
        binding.descriptionTextview.text =
            data.meanings?.get(0)?.translation?.translation
        context?.let {
            Glide.with(it)
                .load("https:" + data.meanings?.get(0)?.imageUrl)
                .into(binding.descriptionImageview)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}