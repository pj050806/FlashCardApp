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
    }

    override fun onDestroyView() {
        super.onDestroyView();
        _binding = null;
    }

    private fun flipCard() {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_right_in,R.animator.card_flip_right_out,
                R.animator.card_flip_left_in,R.animator.card_flip_left_out)
            .replace(R.id.frame, CardBackFragment())
            .addToBackStack(null)
            .commit();
    }

    fun onCardClicked () {
        flipCard();
        println("Hey that's it")
    }
}
