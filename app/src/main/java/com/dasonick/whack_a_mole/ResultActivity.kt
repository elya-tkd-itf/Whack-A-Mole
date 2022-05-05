package com.dasonick.whack_a_mole

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dasonick.whack_a_mole.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score : Int = intent.extras?.get("score") as Int
        binding.resultScore.text = "Score: $score"

        binding.playAgain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.toMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val settings: SharedPreferences =
            getSharedPreferences("STORAGE_NAME", Context.MODE_PRIVATE)

        var record = settings.getInt("record", 0)
        if (record < score){
            record = score
            val editor = settings.edit()
            editor.putInt("record", record).apply()
        }
        binding.resultRecord.text = "Record: $record"
    }
}