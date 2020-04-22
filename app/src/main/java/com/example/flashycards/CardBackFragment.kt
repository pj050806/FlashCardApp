package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.example.flashycards.databinding.FragmentCardBackBinding

/**
 * A simple [Fragment] subclass.
 */
class CardBackFragment : Fragment() {
    private lateinit var cardView: CardView;
    private lateinit var cardFragment: Fragment;
    private var _binding: FragmentCardBackBinding? = null;
    private val binding get() = _binding!!;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardBackBinding.inflate(inflater, container, false);
        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardView = binding.card
        cardView.setOnClickListener {onCardClicked()}
        cardFragment = cardView.findFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView();
        _binding = null;
    }

    private fun flipCard() {
        parentFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                R.animator.card_flip_left_in, R.animator.card_flip_left_out,
                R.animator.card_flip_right_in, R.animator.card_flip_right_out)
            .replace(R.id.card_fragment_container, CardFragment())
            .addToBackStack(null)
            .commit();
    }

    fun onCardClicked () {
        flipCard();
        System.out.println("Hey that's it")
    }
}
