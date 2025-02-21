package uz.polat.xotira.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import eightbitlab.com.blurview.RenderScriptBlur
import uz.polat.xotira.R
import uz.polat.xotira.data.TypeEnum
import uz.polat.xotira.databinding.DialogInfoBinding
import uz.polat.xotira.databinding.ScreenMenuBinding
import uz.polat.xotira.domain.locale.LocalStorage
import uz.polat.xotira.util.canClick

class MenuScreen:Fragment(R.layout.screen_menu) {
    private val binding: ScreenMenuBinding by viewBinding(ScreenMenuBinding::bind)
    private val localStorage by lazy { LocalStorage(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        makeCardsBlurry()
        setVisibility()

        binding.blurFruits.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            findNavController().navigate(MenuScreenDirections.actionMenuScreenToLevelScreen(TypeEnum.FRUIT))
        }

        binding.blurAnimals.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            findNavController().navigate(MenuScreenDirections.actionMenuScreenToLevelScreen(TypeEnum.ANIMAL))
        }

        binding.cardInfo.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            val d = AlertDialog.Builder(requireContext()).create()
            val dialog = DialogInfoBinding.inflate(layoutInflater)
            dialog.okBtn.setOnClickListener {
                d.dismiss()
            }
            d.setView(dialog.root)
            d.show()
        }

    }

    private fun setVisibility() {
        binding.easyTickFruit.isVisible = localStorage.fruit_easy
        binding.mediumTickFruit.isVisible = localStorage.fruit_normal
        binding.hardTickFruit.isVisible = localStorage.fruit_hard

        binding.easyTickAnimal.isVisible = localStorage.animal_easy
        binding.mediumTickAnimal.isVisible = localStorage.animal_normal
        binding.hardTickAnimal.isVisible = localStorage.animal_hard
    }

    private fun makeCardsBlurry() {
        val  decorView = requireActivity().window.decorView;
        val rootView = decorView.findViewById<View>(R.id.root_menu) as ViewGroup
        val windowBackground = decorView.background
        binding.blurFruits.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurFruits.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurFruits.clipToOutline = true;


        binding.blurAnimals.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurAnimals.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurAnimals.clipToOutline = true;

        binding.blurBack.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurBack.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurBack.clipToOutline = true;
    }
}