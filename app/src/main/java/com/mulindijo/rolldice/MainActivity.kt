package com.mulindijo.rolldice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity() {
    private var gamePoints:Int = 100
    private var randomNumber:Int = 0
    private var humanScore:Int = 0
    private var botScore:Int = 0
    private var isGameOn:Boolean = true
    private var gameWinner:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dice_image.isEnabled = false

        startGame()

        dice_image.setOnClickListener {
            dice_image.isEnabled = false
            val randomNumber = rollDice()
            val totScore = humanScore + randomNumber
            if (totScore < gamePoints) {
                humanScore += randomNumber
                tvPlayerGamePoint.text = humanScore.toString()
            } else showWinner("Player", tvPlayerGamePoint)
            phoneToPlay()

        }
    }

    private fun showWinner(winner: String, textView: TextView) {
        dice_image.isEnabled = false
        isGameOn = false
        gameWinner = resources.getText(R.string.game_over_msg).toString() + winner
        game_winner.text = gameWinner
        textView.text = gamePoints.toString()
        stopGame()
    }

    private fun startGame() {
        btnStartGame.setOnClickListener {
            newGame()
            pauseGame()
        }
    }

    private fun pauseGame() {
        btnPauseGame.setOnClickListener {
            dice_image.isEnabled = false
            btnPauseGame.visibility = View.GONE
            btnStartGame.visibility = View.GONE
            oo.visibility = View.VISIBLE
            restartGame()
            continueGame()
        }
    }

    private fun stopGame() {
        dice_image.isEnabled = false
        btnPauseGame.visibility = View.GONE
        btnStartGame.visibility = View.VISIBLE
    }

    private fun restartGame() {
        btnRestartGame.setOnClickListener {
            newGame()
        }
    }

    private fun continueGame() {
        btnContinueGame.setOnClickListener {
            dice_image.isEnabled = true
            btnPauseGame.visibility = View.VISIBLE
            oo.visibility = View.GONE
        }
    }

    private fun newGame() {
        dice_image.isEnabled = true
        isGameOn = true
        dice_image.setImageResource(R.drawable.ic_dice1)
        randomNumber = 0
        humanScore = 0
        tvPlayerGamePoint.text = humanScore.toString()
        botScore = 0
        tvBotGamePoint.text = botScore.toString()
        btnStartGame.visibility = View.GONE
        btnPauseGame.visibility = View.VISIBLE
        game_winner.text = ""
    }

    private fun phoneToPlay() {
        if (isGameOn) {
            Timer().schedule(2000) {
                runOnUiThread {
                    val randomNumber = rollDice()
                    val totScore = botScore + randomNumber
                    if (totScore < gamePoints) {
                        botScore += randomNumber
                        tvBotGamePoint.text = botScore.toString()
                        dice_image.isEnabled = true
                    } else showWinner("Bot", tvBotGamePoint)
                }
            }
        }
    }

    private fun rollDice(): Int {
        randomNumber = (1..6).random()

        when (randomNumber) {
            1 -> {
                dice_image.setImageResource(R.drawable.ic_dice1)
            }

            2 -> {
                dice_image.setImageResource(R.drawable.ic_dice2)
            }

            3 -> {
                dice_image.setImageResource(R.drawable.ic_dice3)
            }

            4 -> {
                dice_image.setImageResource(R.drawable.ic_dice4)
            }

            5 -> {
                dice_image.setImageResource(R.drawable.ic_dice5)
            }

            6 -> {
                dice_image.setImageResource(R.drawable.ic_dice6)
            }
        }
        return randomNumber
    }
}