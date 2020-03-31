package ru.skillbranch.devintensive.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.ui.custom.AvatarImageView
import ru.skillbranch.devintensive.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private val stId = 10
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initViews()
        initViewModel()

        btn_border.setOnClickListener {
            aiv.setBorderWidth((2..10).random())
        }

        btn_color.setOnClickListener {
            aiv.setBorderColor((AvatarImageView.bgColors).random())
        }
    }

    private fun initToolbar() {
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initViews() {
    }

    private fun initViewModel() {
    }
}
