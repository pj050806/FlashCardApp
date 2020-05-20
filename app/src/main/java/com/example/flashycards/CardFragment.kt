package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.flashycards.databinding.FragmentCardBinding

/**
 * A simple [Fragment] subclass.
 */
class CardFragment : Fragment() {
    private lateinit var cardView: CardView;

    private var _binding: FragmentCardBinding? = null;
    private val binding get() = _binding!!;

    companion object {
        fun  newInstance(front: String, back: String): CardFragment {
            val fragment = CardFragment()
            val args = Bundle()
            args.putString("frontSide", front)
            args.putString("backSide", back)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBinding.inflate(inflater, container, false);
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = binding.card;
        cardView.setOnClickListener {onCardClicked()}
        binding.textView.setText(arguments?.getString("frontSide"))
    }

    override fun onDestroyView() {
        super.onDestroyView();
        _binding = null;
    }

    private fun flipCard() {
        val cardback = newInstance(arguments?.getString("backSide") ?: "","Back")
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_right_in,R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,R.animator.card_flip_left_out)
            .replace(R.id.frame, cardback)
            .addToBackStack(null)
            .commit();
    }

    fun onCardClicked () {
        flipCard();
        println("Hey that's it")
    }
}
