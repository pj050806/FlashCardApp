package com.example.flashycards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashycards.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding
    get() = _binding!!

    private var allLabels: MutableList<String> = emptyList<String>().toMutableList()
    private val viewAdapter: RecyclerView.Adapter<*> = MyAdapter(allLabels)
    private val myObserver = Observer<List<String>> {
        println("observer triggered labellist: $it")
        allLabels.clear()
        allLabels.addAll(it)
        viewAdapter.notifyDataSetChanged()
        println("set labellist: $allLabels")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("Testwörter1")
            findNavController().navigate(action)
        }


        (activity as MainActivity).viewModel.allLabels.observe(viewLifecycleOwner, myObserver)
        val viewManager: RecyclerView.LayoutManager = GridLayoutManager(context,2)
        binding.recyclerviewCardpile.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as MainActivity).viewModel.allLabels.removeObserver(myObserver)
    }


    private class MyAdapter(private val myDataset: List<String>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        class MyViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
            return  MyViewHolder(textView)
        }

        override fun getItemCount(): Int = myDataset.size + 1

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            if(position < myDataset.size) {

                holder.textView.text = myDataset[position]

                holder.itemView.setOnClickListener {
                    val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(myDataset[position])
                    it.findNavController().navigate(action)
                }
            } else {
                holder.textView.text = "Add New Label..."

                holder.itemView.setOnClickListener{
                    println("TODO add new Label")
                }
            }
        }
    }
}
