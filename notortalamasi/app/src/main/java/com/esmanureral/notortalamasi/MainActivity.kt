package com.esmanureral.notortalamasi

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.esmanureral.notortalamasi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        binding.btnsonuc.setOnClickListener {
            if (binding.editTextNumber.text.isNotEmpty()&& binding.editTextNumber2.text.isNotEmpty()) {
                var sinav1 = binding.editTextNumber.text.toString().toInt()
                var sinav2 = binding.editTextNumber2.text.toString().toInt()
                var ortalama: Double = (sinav1 + sinav2) / 2.toDouble()
                if(ortalama>=50){
                    binding.textView.text=ortalama.toString()+" "+"Geçtiniz"
                    binding.textView.setTextColor(getColor(R.color.green))
                }
                else{
                    binding.textView.text=ortalama.toString()+" "+"Kaldınız"
                    binding.textView.setTextColor(getColor(R.color.red))
                }

            }
            else{
                binding.textView.text="Sınav notunu giriniz!"
                binding.textView.setTextColor(getColor(R.color.red))
            }
        }


    }
}