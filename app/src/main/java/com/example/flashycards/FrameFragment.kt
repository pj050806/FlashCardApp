package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.flashycards.databinding.FragmentFrameBinding

/**
 * A simple [Fragment] subclass.
 */
class FrameFragment : Fragment() {

    private var _binding: FragmentFrameBinding? = null
    private val binding get() = _binding!!

    companion object{
        lateinit var frame: FrameLayout
        lateinit var front: String
        lateinit var back: String

        fun  newInstance(front: String, back: String): FrameFragment {
            val frameFragment = FrameFragment()
            val args = Bundle()
            args.putString("frontSide", front)
            args.putString("backSide", back)
            this.front = front
            this.back = back

            frameFragment.arguments = args
            return frameFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFrameBinding.inflate(inflater, container, false)

        if(savedInstanceState == null) {
            front = arguments?.getString("frontSide") ?: "NOTFOUND"
            back = arguments?.getString("backSide") ?: "NOTFOUND"
            val cardFragment = CardFragment.newInstance(front, back)
            childFragmentManager
                .beginTransaction()
                .add(R.id.frame, cardFragment)
                .commit()
        } else {
            childFragmentManager
                .beginTransaction()
                .replace(R.id.frame, CardFragment.newInstance(front, back))
                .commit()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frame = binding.frame
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
