package com.example.flashycards

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ViewModel.CardViewModel
import com.example.database.FlashCard
import com.example.flashycards.databinding.AddCardBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            newCardAlert()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun insertNewCard(front: String, back: String, label: String) {
        val viewModel = ViewModelProvider(this)[CardViewModel::class.java]
        val newCard = FlashCard(front,back,label)
        viewModel.insert(newCard)
    }

    private fun newCardAlert() {

        val builder = AlertDialog.Builder(this)
        val addCardLayout = layoutInflater.inflate(R.layout.add_card, null)
        val bind = AddCardBinding.bind(addCardLayout)

        val viewModel = ViewModelProvider(this)[CardViewModel::class.java]


        viewModel.allLabels.observe(this, Observer { labelList ->
                bind.spinnerLabel.adapter= ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, labelList)
        })


        builder.setMessage("Enter new card!")
            .setTitle("New Card")
            .setPositiveButton("Add") { dialog, which ->
                println("Dialog works!!!")
                println(bind.editTextCardFront.text)
                println(bind.editTextCardBack.text)
                println(bind.spinnerLabel.selectedItem)
                insertNewCard(bind.editTextCardFront.text.toString(),
                    bind.editTextCardBack.text.toString(),
                    bind.spinnerLabel.selectedItem.toString())
            }.setNegativeButton("Cancel") {
                    dialog, _ ->  dialog.cancel()
                    viewModel.allLabels.removeObservers(this)
                }
            .setView(addCardLayout)
            .create().show()
    }
}
