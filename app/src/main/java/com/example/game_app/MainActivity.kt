package com.example.game_app


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var boxes: Array<Button>
    private lateinit var msgLayout: LinearLayout
    private lateinit var msgText: TextView
    private lateinit var btnNew: Button
    private lateinit var btnReset: Button

    private var turnO: Boolean = true  // true = O, false = X

    // Same win patterns as your JS
    private val winPatterns = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(0, 3, 6),
        intArrayOf(0, 4, 8),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(2, 4, 6),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        msgLayout = findViewById(R.id.msgLayout)
        msgText = findViewById(R.id.msgg)
        btnNew = findViewById(R.id.btnNew)
        btnReset = findViewById(R.id.btnReset)

        boxes = arrayOf(
            findViewById(R.id.btn0),
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8)
        )

        // Set click listeners for all boxes
        for (btn in boxes) {
            btn.setOnClickListener(this)
        }

        // New + Reset buttons
        btnNew.setOnClickListener { resetGame() }
        btnReset.setOnClickListener { resetGame() }

        resetGame()
    }

    override fun onClick(v: View?) {
        if (v !is Button) return

        // If already filled, ignore
        if (v.text.isNotEmpty()) return

        if (turnO) {
            v.text = "O"
        } else {
            v.text = "X"
        }
        v.isEnabled = false
        turnO = !turnO

        checkWinner()
    }

    private fun resetGame() {
        turnO = true
        enableBoxes()
        msgLayout.visibility = View.GONE
    }

    private fun enableBoxes() {
        for (btn in boxes) {
            btn.isEnabled = true
            btn.text = ""
        }
    }

    private fun disableBoxes() {
        for (btn in boxes) {
            btn.isEnabled = false
        }
    }

    private fun showWinner(winner: String) {
        // Keeping your original message style 🙂
        msgText.text = "Sheer hai tu bhai $winner"
        msgLayout.visibility = View.VISIBLE
        disableBoxes()
    }

    private fun checkWinner() {
        for (pattern in winPatterns) {
            val pos1 = boxes[pattern[0]].text.toString()
            val pos2 = boxes[pattern[1]].text.toString()
            val pos3 = boxes[pattern[2]].text.toString()

            if (pos1.isNotEmpty() && pos2.isNotEmpty() && pos3.isNotEmpty()) {
                if (pos1 == pos2 && pos2 == pos3) {
                    showWinner(pos1)
                    return
                }
            }
        }

    }
}
