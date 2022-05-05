package com.dasonick.whack_a_mole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import com.dasonick.whack_a_mole.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var score: Int = 0
    private lateinit var binding: ActivityMainBinding
    private var holeMoles: ArrayList<HoleMoleState> = ArrayList()
    private var canCatch: Boolean = false
    private var dispose: Disposable? = null

    private enum class HoleMoleState {
        IN_HOLE, OUT_HOLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        for (i in 0..9) holeMoles.add(HoleMoleState.IN_HOLE)

        setOnClickListeners()

        activateTimer()

        goOutHole()
    }

    private fun activateTimer(){
        val self = this;
        object : CountDownTimer(30000, 1000){

            override fun onTick(millisUntilFinished: Long) {
                binding.time.text = "Time: "+(millisUntilFinished/1000)
                Log.wtf("wtf", millisUntilFinished.toString())
            }

            override fun onFinish() {
                val intent = Intent(self, ResultActivity::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    private fun setOnClickListeners() {
        binding.holeMoleItem1.imageView.setOnClickListener {holeMoleItemClicked(0)}
        binding.holeMoleItem2.imageView.setOnClickListener {holeMoleItemClicked(1)}
        binding.holeMoleItem3.imageView.setOnClickListener {holeMoleItemClicked(2)}

        binding.holeMoleItem4.imageView.setOnClickListener {holeMoleItemClicked(3)}
        binding.holeMoleItem5.imageView.setOnClickListener {holeMoleItemClicked(4)}
        binding.holeMoleItem6.imageView.setOnClickListener {holeMoleItemClicked(5)}

        binding.holeMoleItem7.imageView.setOnClickListener {holeMoleItemClicked(6)}
        binding.holeMoleItem8.imageView.setOnClickListener {holeMoleItemClicked(7)}
        binding.holeMoleItem9.imageView.setOnClickListener {holeMoleItemClicked(8)}
    }

    private fun holeMoleItemClicked(i : Int){
        if (holeMoles[i] == HoleMoleState.OUT_HOLE && canCatch){
            printInHole(i)
            canCatch = false
            updateScore()
            dispose?.dispose()
            goOutHole()
        }
    }

    private fun goOutHole(){
        val i = Random.nextInt(0, 9)

        holeMoles[i] = HoleMoleState.OUT_HOLE
        printOutHole(i)
        canCatch = true

        dispose = Completable.timer(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                holeMoles[i] = HoleMoleState.IN_HOLE
                printInHole(i)
                canCatch = false
                goOutHole()
            }
    }

    private fun printOutHole(i: Int){
        when(i){
            0 -> binding.holeMoleItem1.imageView.setImageResource(R.drawable.hole_mole)
            1 -> binding.holeMoleItem2.imageView.setImageResource(R.drawable.hole_mole)
            2 -> binding.holeMoleItem3.imageView.setImageResource(R.drawable.hole_mole)

            3 -> binding.holeMoleItem4.imageView.setImageResource(R.drawable.hole_mole)
            4 -> binding.holeMoleItem5.imageView.setImageResource(R.drawable.hole_mole)
            5 -> binding.holeMoleItem6.imageView.setImageResource(R.drawable.hole_mole)

            6 -> binding.holeMoleItem7.imageView.setImageResource(R.drawable.hole_mole)
            7 -> binding.holeMoleItem8.imageView.setImageResource(R.drawable.hole_mole)
            8 -> binding.holeMoleItem9.imageView.setImageResource(R.drawable.hole_mole)
        }
    }

    private fun printInHole(i: Int){
        when(i){
            0 -> binding.holeMoleItem1.imageView.setImageResource(R.mipmap.hole)
            1 -> binding.holeMoleItem2.imageView.setImageResource(R.mipmap.hole)
            2 -> binding.holeMoleItem3.imageView.setImageResource(R.mipmap.hole)

            3 -> binding.holeMoleItem4.imageView.setImageResource(R.mipmap.hole)
            4 -> binding.holeMoleItem5.imageView.setImageResource(R.mipmap.hole)
            5 -> binding.holeMoleItem6.imageView.setImageResource(R.mipmap.hole)

            6 -> binding.holeMoleItem7.imageView.setImageResource(R.mipmap.hole)
            7 -> binding.holeMoleItem8.imageView.setImageResource(R.mipmap.hole)
            8 -> binding.holeMoleItem9.imageView.setImageResource(R.mipmap.hole)
        }
    }

    private fun updateScore(){
        score += 1
        binding.score.text = "Score: " + score
    }
}