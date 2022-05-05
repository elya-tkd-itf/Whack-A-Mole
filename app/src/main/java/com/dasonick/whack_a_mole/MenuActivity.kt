package com.dasonick.whack_a_mole

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dasonick.whack_a_mole.databinding.ActivityMainBinding
import com.dasonick.whack_a_mole.databinding.ActivityMenuBinding
import com.dasonick.whack_a_mole.databinding.ActivityResultBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val settings: SharedPreferences =
            getSharedPreferences("STORAGE_NAME", Context.MODE_PRIVATE)
        val record = settings.getInt("record", 0)
        binding.record.text = "Record: $record"

        binding.play.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}