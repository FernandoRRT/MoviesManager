package br.edu.ifsp.scl.sdm.moviesmanager.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.sdm.moviesmanager.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.moviesmanager.R

class   MainActivity : AppCompatActivity() {
    private val amb by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        setSupportActionBar(amb.mainTb)
        supportActionBar?.title = getString(R.string.app_name)
    }
}