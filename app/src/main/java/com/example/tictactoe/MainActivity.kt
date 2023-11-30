package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn{
        NOUGHT,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var crossesScore = 0
    private var noughtsScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        setContentView(R.layout.activity_main_menu)

        // Set click listeners for the buttons in the main menu
        val startGameButton = findViewById<Button>(R.id.Iniciarjogo)
        val closeGameButton = findViewById<Button>(R.id.Fecharjogo)

        startGameButton.setOnClickListener {
            // Switch to the game layout when the "Start Game" button is clicked
            setContentView(binding.root)
            initBoard()
        }

        closeGameButton.setOnClickListener {
            // Close the app when the "Close Game" button is clicked
            finish()
        }
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View){
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT)){
            noughtsScore++
            result("Bola Ganhou!")
        }

        if(checkForVictory(CROSS)){
            crossesScore++
            result("Cruz Ganhou!")
        }

        if(fullBoard()){
            result("Empate")
            return resetBoard()
        }
    }

    private fun checkForVictory(s: String): Boolean {

        //Vitória Horizontal
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        //Vitória Vertical
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        //Vitória Diagonal
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String) {

        val message = "\n0: $noughtsScore\n\nX: $crossesScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for(buttom in boardList){
            buttom.text = ""
        }
        if(firstTurn == Turn.NOUGHT)
            firstTurn = Turn.CROSS
        else if(firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for(buttom in boardList){
            if(buttom.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return
        if(currentTurn == Turn.NOUGHT){
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        }
        else if(currentTurn == Turn.CROSS){
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.CROSS)
            turnText = "Vez do $CROSS"
        else if(currentTurn == Turn.NOUGHT)
            turnText = "Vez do $NOUGHT"

        binding.turnTV.text = turnText
    }

    companion object{
        const val NOUGHT = "0"
        const val CROSS = "X"
    }
}