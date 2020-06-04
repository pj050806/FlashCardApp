package com.example.flashycards

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

    private fun newCard() {
        val viewModel = ViewModelProvider(this)[CardViewModel::class.java]
        val newCard = FlashCard("inserted1_front","iserted1_back","Testwörter1")
        viewModel.insert(newCard)
    }

    private fun newCardAlert() {


        val builder: AlertDialog.Builder? = this.let { AlertDialog.Builder(it) }
        val addCardLayout = layoutInflater.inflate(R.layout.add_card, null)
        val bind = AddCardBinding.bind(addCardLayout)

        builder?.setMessage("Enter new card!")
            ?.setTitle("New Card")
            ?.setPositiveButton("Add") { dialog, which ->
                println("Dialog works!!!")
                println(bind.editTextCardFront.text)
                println(bind.editTextCardBack.text)
                println(bind.editTextLabel.text)
            }?.setNegativeButton("Cancel") {dialog, _ ->  dialog.cancel()}
            ?.setView(addCardLayout)
            ?.create()?.show()
    }
}
