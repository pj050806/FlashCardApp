package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 */
class FrameFragment : Fragment() {

    companion object{
        lateinit var front :String
        lateinit var back :String

        fun  newInstance(front: String, back: String): FrameFragment {
            val frameFragment = FrameFragment()
//            val fragment = CardFragment()
            val args = Bundle()
            args.putString("frontSide", front)
            args.putString("backSide", back)
            this.front = front
            this.back = back
            frameFragment.arguments = args
//            frameFragment.childFragmentManager
//                .beginTransaction()
//                .add(R.id.frame, fragment)
//                .commit()
            return frameFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if(savedInstanceState == null) {
            front = arguments?.getString("frontSide") ?: "NOTFOUND"
            back = arguments?.getString("backSide") ?: "NOTFOUND"
            val cardFragment = CardFragment.newInstance(front, back)
            childFragmentManager
                .beginTransaction()
                .add(R.id.frame, cardFragment)
                .commit();
        } else {
            //front = savedInstanceState.getString("frontSide") ?: "EMPTY"
            //back = savedInstanceState.getString("backSide") ?: "EMPTY"
            childFragmentManager
                .beginTransaction()
                .add(R.id.frame, CardFragment.newInstance(front, back))
                .commit();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frame, container, false)
    }
}
