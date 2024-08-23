package com.esmanureral.kronometre

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.esmanureral.kronometre.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
       // setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var zamanidurdur:Long=0
        binding.btnStart.setOnClickListener { //kronometredeki start butonuna basıldığında
            binding.kronometre.base=SystemClock.elapsedRealtime()+zamanidurdur//kronometre durduğu andan itibaren geçen süreyi hesaba katar.
            binding.kronometre.start()
            binding.btnStart.visibility=View.GONE//start butonu basıldıktan sonra gizlenmesini sağlar.
            binding.btnPause.visibility=View.VISIBLE//artık Pause butonu görünür halde.
            binding.imageView.setImageDrawable(getDrawable(R.drawable.pause))//kırmızı resimli hali gelicek..
        }


        binding.btnPause.setOnClickListener { //kronometredeki Pause butonuna basıldığında binding.kronometre.start()
           zamanidurdur=binding.kronometre.base-SystemClock.elapsedRealtime()//kronometreyi durdurduğunuz andaki geçen süreyi hesaplar
            binding.kronometre.stop()
            binding.btnPause.visibility=View.GONE//Pause butonu basıldıktan sonra gizlenmesini sağlar.
            binding.btnStart.visibility=View.VISIBLE//artık Start butonu görünür halde.
            binding.imageView.setImageDrawable(getDrawable(R.drawable.start))
        }

        binding.btnReset.setOnClickListener {
            binding.kronometre.base=SystemClock.elapsedRealtime()
            binding.kronometre.stop()
            zamanidurdur=0
            binding.btnPause.visibility=View.GONE//Pause butonu basıldıktan sonra gizlenmesini sağlar.
            binding.btnStart.visibility=View.VISIBLE//artık Start butonu görünür halde.
            binding.imageView.setImageDrawable(getDrawable(R.drawable.start))
        }
    }
}