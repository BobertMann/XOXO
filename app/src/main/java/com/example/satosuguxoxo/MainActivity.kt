package com.example.satosuguxoxo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.satosuguxoxo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn{
        SATORU,SUGURU
    }
    private var firstTurn = Turn.SATORU
    private var currentTurn = Turn.SATORU
    private var count = 0
    private var board = mutableListOf<ImageButton>()
    private var score_Satoru = 0
    private var score_Suguru = 0
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard(){
        count = 0
        board.add(binding.a1)
        board.add(binding.a2)
        board.add(binding.a3)
        board.add(binding.b1)
        board.add(binding.b2)
        board.add(binding.b3)
        board.add(binding.c1)
        board.add(binding.c2)
        board.add(binding.c3)
    }

    fun boardButtonOnClick(view: View) {
        var ttext = "satoru"
        //binding.turnTV.text = ttext
        if (view !is ImageButton) {
            //if (view is Button)
            //binding.turnTV.text = ttext
            return
        }
        addToBoard(view)
    }
    private fun match(button: ImageButton, s:String): Boolean = button.tag == s
    private fun checkVictory(s: String): Boolean{

        //check horizontal
        if (match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if (match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if (match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        //check vertical
        if (match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if (match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if (match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        //check diagonal
        if (match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if (match(binding.c1,s) && match(binding.b2,s) && match(binding.a3,s))
            return true
        return false
    }

    private fun addToBoard(button: ImageButton){
        if (button.tag != null) {
            //button.tag = "ss"
            //var textt: String =button.tag.toString()
            //binding.turnTV.text = textt
            return;
        }
        if (currentTurn == Turn.SATORU){
            button.tag = "satoru"
            button.setImageResource(R.drawable.satoruuu)
            currentTurn = Turn.SUGURU
            if (checkVictory("satoru")){
                score_Satoru++
                finishGameVictory(Turn.SATORU)
                return
            }
        }
        else{
            button.tag = "suguru"
            button.setImageResource(R.drawable.suguruuu)
            currentTurn = Turn.SATORU
            if (checkVictory("suguru")){
                score_Suguru++
                finishGameVictory(Turn.SUGURU)
                return
            }
        }
        count++
        if (count == 9)
            finishGameDraw()
        setTurnLabel()
    }
    private fun setTurnLabel(){
        var turnText = ""
        if (currentTurn == Turn.SATORU){
            turnText = "Turn Satoru"
        }
        else{
            turnText = "Turn Suguru"
        }
        binding.turnTV.text = turnText
    }
    private fun resetBoard(){
        count = 0
        for (button in board) {
            button.tag = null
            button.setImageResource(R.drawable.blank)
        }
        if (firstTurn == Turn.SUGURU)
            firstTurn = Turn.SATORU
        else if (firstTurn == Turn.SATORU)
            firstTurn = Turn.SUGURU
        currentTurn = firstTurn
        setTurnLabel();
    }
    private fun finishGameVictory(player: Turn){
        val winner: String
        val loser: String
        if (player == Turn.SUGURU){
            winner = "Suguru"
            loser = "Satoru"
        }
        else{
            winner = "Satoru"
            loser = "Suguru"
        }
        AlertDialog.Builder(this)
            .setTitle("$winner pegs $loser")
            .setMessage("\nSatoru: $score_Satoru\n\nSuguru: $score_Suguru")
            .setPositiveButton("New Game!"){
                _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }
    private fun finishGameDraw(){
        AlertDialog.Builder(this)
            .setTitle("Draw! Nobody gets pegged!!!:D\n but noone gets to peg :(")
            .setMessage("\nSatoru: $score_Satoru\n\nSuguru: $score_Suguru")
            .setPositiveButton("New Game!"){
                _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }
}