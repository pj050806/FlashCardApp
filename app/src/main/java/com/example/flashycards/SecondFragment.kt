package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ViewModel.CardViewModel
import com.example.database.FlashCard
import com.example.flashycards.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var cardViewModel: CardViewModel

    private lateinit var viewPager2: ViewPager2
    private lateinit var screenSlidePageAdapter: ScreenSlidePageAdapter

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        cardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)
        cardViewModel.allCards.observe(viewLifecycleOwner, Observer { cards ->  cards?.let {
            println("observer triggerd $it")
            screenSlidePageAdapter.setCards(it)
        } })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)}

        screenSlidePageAdapter = activity?.let { ScreenSlidePageAdapter(it) }!!
        viewPager2 = binding.cardViewpager
        viewPager2.adapter = screenSlidePageAdapter

//        if(savedInstanceState == null) {
//            activity?.supportFragmentManager
//                ?.beginTransaction()
//                ?.add(R.id.card_fragment_container, CardFragment())
//                ?.commit();
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cardViewModel.allCards.removeObservers(viewLifecycleOwner)
    }

    private inner class ScreenSlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private var cards = emptyList<FlashCard>()

        override fun getItemCount(): Int {
            return cards.size
        }

        override fun createFragment(position: Int): Fragment {
            val card = cards[position]
            return FrameFragment.newInstance(card.frontSide, card.backSide)
        }

        internal fun setCards(cards: List<FlashCard>) {
            this.cards = cards
            notifyDataSetChanged()
        }
    }
}
