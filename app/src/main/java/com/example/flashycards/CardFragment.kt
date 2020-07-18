package com.example.flashycards

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.flashycards.databinding.FragmentCardBinding

/**
 * A simple [Fragment] subclass.
 */
class CardFragment : Fragment() {
    private lateinit var cardView: CardView

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun  newInstance(front: String, back: String, color: Int): CardFragment {
            val fragment = CardFragment()
            val args = Bundle()
            args.putString("frontSide", front)
            args.putString("backSide", back)
            args.putInt("color", color)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = binding.card
        cardView.setOnClickListener {onCardClicked()}
        cardView.setOnLongClickListener {onCardLongClicked()}
        binding.textView.text = arguments?.getString("frontSide")
        binding.card.setCardBackgroundColor(arguments?.getInt("color") ?: Color.WHITE)
    }

    private fun onCardLongClicked(): Boolean {
        println("card was long clicked")
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Delete Card?")
            .setMessage("Do you want to delete the card?")
            .setPositiveButton("Delete!")  { _,_ ->
                println("delete card)")
                (activity as MainActivity).viewModel.deleteCard(arguments?.getString("frontSide") ?: "", arguments?.getString("backSide") ?: "")
            }
            .setNegativeButton("DON'T Delete") { dialog, _ ->
                dialog.cancel()
            }
            .create().show()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun flipCard() {
        val cardback = newInstance(
            arguments?.getString("backSide") ?: "",
            arguments?.getString("frontSide") ?: "",
            arguments?.getInt("color") ?: Color.WHITE
        )
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_right_in,R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,R.animator.card_flip_left_out)
            .replace(R.id.frame, cardback)
            .commit()
    }

    private fun onCardClicked () {
        flipCard()
        println("Hey that's it")
    }
}
