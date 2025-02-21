package uz.polat.xotira.presentation

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import eightbitlab.com.blurview.RenderScriptBlur
import uz.polat.xotira.R
import uz.polat.xotira.data.LevelEnum
import uz.polat.xotira.data.TypeEnum
import uz.polat.xotira.databinding.ScreenLevelBinding
import uz.polat.xotira.util.canClick

class LevelScreen:Fragment(R.layout.screen_level) {
    private val binding:ScreenLevelBinding by viewBinding(ScreenLevelBinding::bind)
    private val args:LevelScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        makeCardsBlurry()
        setImages(args.type)

        binding.cardBack.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            findNavController().popBackStack()
        }

        binding.cardEasy.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(
                level = LevelEnum.EASY, type = args.type)
            )
        }

        binding.cardNormal.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(
                level = LevelEnum.MEDIUM, type = args.type)
            )
        }

        binding.cardHard.setOnClickListener {
            if (!canClick()) return@setOnClickListener
            findNavController().navigate(LevelScreenDirections.actionLevelScreenToGameScreen(
                level = LevelEnum.HARD, type = args.type)
            )
        }
    }

    private fun setImages(type: TypeEnum) {
        when(type){
            TypeEnum.FRUIT -> {
                binding.imageEasy.setImageResource(R.drawable.smiling_fruit)
                binding.imageNormal.setImageResource(R.drawable.normal_fruit)
                binding.imageHard.setImageResource(R.drawable.angry_fruit)
            }
            TypeEnum.ANIMAL ->{
                binding.imageEasy.setImageResource(R.drawable.animal_smiling)
                binding.imageNormal.setImageResource(R.drawable.animal_normal)
                binding.imageHard.setImageResource(R.drawable.animal_angry)
            }
        }
    }

    private fun makeCardsBlurry() {
        val  decorView = requireActivity().window.decorView;
        val rootView = decorView.findViewById<View>(R.id.root_level) as ViewGroup
        val windowBackground = decorView.background
        binding.blurEasy.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurEasy.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurEasy.clipToOutline = true;


        binding.blurNormal.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurNormal.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurNormal.clipToOutline = true;

        binding.blurHard.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurHard.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurHard.clipToOutline = true;

        binding.blurBack.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurBack.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurBack.clipToOutline = true;


    }
}