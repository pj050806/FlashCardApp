package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.flashycards.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val NUM_PAGES = 5;

    private lateinit var viewPager2: ViewPager2;

    private var _binding: FragmentSecondBinding? = null;
    private val binding get() = _binding!!;

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentSecondBinding.inflate(inflater, container, false);


        val view = binding.root;
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)}

        viewPager2 = binding.cardFragmentContainer
        viewPager2.adapter = activity?.let { ScreenSlidePageAdapter(it) }

//        if(savedInstanceState == null) {
//            activity?.supportFragmentManager
//                ?.beginTransaction()
//                ?.add(R.id.card_fragment_container, CardFragment())
//                ?.commit();
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView();
        _binding = null;
    }

    private inner class ScreenSlidePageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return NUM_PAGES
        }

        override fun createFragment(position: Int): Fragment {
            return CardFragment();
        }
    }

}
