package uz.polat.xotira.presentation

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import eightbitlab.com.blurview.RenderScriptBlur
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import uz.polat.xotira.data.CardData
import uz.polat.xotira.R
import uz.polat.xotira.data.LevelEnum
import uz.polat.xotira.data.TypeEnum
import uz.polat.xotira.databinding.ScreenGameBinding
import uz.polat.xotira.domain.Repository
import uz.polat.xotira.domain.locale.LocalStorage
import uz.polat.xotira.util.closeCard
import uz.polat.xotira.util.hideCard
import uz.polat.xotira.util.openCard
import java.util.concurrent.TimeUnit

class GameScreen : Fragment(R.layout.screen_game) {
    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private lateinit var dialog: AlertDialog
    private val repository = Repository.getInstance()
    private val args: GameScreenArgs by navArgs()
    private val localStorage by lazy { LocalStorage(requireContext())}
    private lateinit var countDownTimer: CountDownTimer

    private var level = LevelEnum.EASY
    private var _h = 0
    private var _w = 0
    private val images = ArrayList<ImageView>()
    private var firstOpen = -1
    private var secondOpen = -1
    private var attempts = 0
    private var totalTime = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        level = args.level
        totalTime = when(level){
            LevelEnum.EASY -> 60_000L
            LevelEnum.MEDIUM -> 90_000L
            LevelEnum.HARD -> 120_000L
        }
        val allImages = repository.getCardDataByLevel(args.type, args.level)
        Log.d("TAG", "onViewCreated: allImages:$allImages")
        Log.d("TAG", "onViewCreated: level:${args.level.horCount} and ver:${args.level.verCount}")
        makeCardsBlurry()

        binding.container.post {
            _w = binding.container.width / level.verCount
//            _h = binding.container.height / level.horCount

            Log.d("TAG", "onViewCreated: _h:$_w and horcount:${level.verCount}")
            val toAddEveryImage = (binding.container.height-_w*level.horCount)/2


            for (i in 0 until level.verCount) {
                for (j in 0 until level.horCount) {
                    val imageView = ShapeableImageView(requireContext())
                    binding.container.addView(imageView)
                    Log.d("TAG", "onViewCreated: child count: ${binding.container.childCount}")
                    val lp = imageView.layoutParams as ConstraintLayout.LayoutParams
                    lp.apply {
                        width = _w
                        height = _w
                        Log.d("TAG", "onViewCreated: $_w $_h")
                    }
                    imageView.x = i * _w * 1f
                    imageView.y = j * _w * 1f + toAddEveryImage
                    imageView.layoutParams = lp
                    imageView.tag = allImages[i * level.horCount + j]
                    imageView.setImageResource(R.drawable.bg_question)
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    imageView.visibility = View.VISIBLE
                    imageView.setPadding(4,4,4,4)

                    imageView.shapeAppearanceModel = imageView.shapeAppearanceModel
                        .toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, 16f)
                        .build()

                    images.add(imageView).also { Log.d("TAG", "onViewCreated: kirdi i:$i and j: $j") }

                    setClickEventToImages()
                    Log.d("TTT", "image.size = ${images.size}")
                }
            }
        }

        binding.cardBack.setOnClickListener {
            findNavController().popBackStack()
//            letsParty()
        }

        Log.d("TAG", "onViewCreated: totalTime:$totalTime")
        countDownTimer = object :CountDownTimer(totalTime,1_000){
            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished <= 10_000){
                    binding.time.setTextColor(Color.RED)
                }
                binding.time.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                showDialog("Vaqt tugadi",Color.RED)
            }
        }.start()
    }

    private fun setClickEventToImages() {
        images.forEachIndexed { index, view ->
            view.setOnClickListener {
                Log.d("TAG", "setClickEventToImages: ${it.tag as CardData}")
                if (firstOpen == -1 && index != firstOpen) {
                    firstOpen = index
                    view.openCard()
                } else if (secondOpen == -1 && index != secondOpen && index != firstOpen) {
                    secondOpen = index
                    view.openCard {
                        check()
                    }

                    attempts += 1
                    binding.attempts.text = attempts.toString()
                }
            }
        }
    }

    private fun check() {
        if ((images[firstOpen].tag as CardData).id == (images[secondOpen].tag as CardData).id) {
            images[firstOpen].hideCard()
            images[secondOpen].hideCard {
                firstOpen = -1
                secondOpen = -1
            }
        } else {
            images[firstOpen].closeCard()
            images[secondOpen].closeCard {
                firstOpen = -1
                secondOpen = -1
            }
        }
        var size = images.size
        for (image in images) {
            if (image.visibility == View.GONE) {
                size--
            }
        }
        if (size == 2) {

            letsParty()

            showDialog("Siz yuttingiz",Color.GREEN)

            countDownTimer.cancel()
            when(args.type){
                TypeEnum.FRUIT -> {
                    when(args.level){
                        LevelEnum.EASY -> localStorage.fruit_easy = true
                        LevelEnum.MEDIUM -> localStorage.fruit_normal = true
                        LevelEnum.HARD -> localStorage.fruit_hard = true
                    }
                }
                TypeEnum.ANIMAL -> {
                    when(args.level){
                        LevelEnum.EASY -> localStorage.animal_easy = true
                        LevelEnum.MEDIUM -> localStorage.animal_normal = true
                        LevelEnum.HARD -> localStorage.animal_hard = true
                    }
                }
            }
        }
    }

    private fun letsParty() {
        val party = Party(
            speed = 0f,
            maxSpeed = 20f,
            damping = 0.95f,
            spread = 0,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 1000, TimeUnit.MILLISECONDS).max(1000),
            position = Position.Relative(0.0, 0.0)
        )
        binding.konfettiView.start(party)
    }

    private fun showDialog(s: String,color:Int) {
        dialog = AlertDialog.Builder(requireContext()).create()
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_win, null, false)
        view.findViewById<TextView>(R.id.dialog_attempts).apply {
            text = "Urinishlar: $attempts"
            setTextColor(color)
        }
        view.findViewById<TextView>(R.id.dialog_text).apply {
            text = s
            setTextColor(color)
        }
        view.findViewById<AppCompatButton>(R.id.ok_btn).setOnClickListener {
            findNavController().popBackStack()
            dialog.hide()
        }
        dialog.setCancelable(false)
        dialog.setView(view)
        dialog.show()
    }

    private fun makeCardsBlurry() {
        val  decorView = requireActivity().window.decorView;
        val rootView = decorView.findViewById<View>(R.id.root_game) as ViewGroup
        val windowBackground = decorView.background
        binding.blurBack.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurBack.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurBack.clipToOutline = true;


        binding.blurAttempts.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurAttempts.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurAttempts.clipToOutline = true;

         binding.blurTime.setupWith(rootView, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(5F)

        binding.blurTime.outlineProvider = ViewOutlineProvider.BACKGROUND;
        binding.blurTime.clipToOutline = true;


    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}