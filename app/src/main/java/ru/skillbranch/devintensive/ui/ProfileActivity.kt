package ru.skillbranch.devintensive.ui

import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_profile_constraint.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.viewmodels.ProfileViewModel

class ProfileActivity: AppCompatActivity() {
    companion object{
        const val IS_EDIT_MODE = "IS_EDIT_MODE"
    }
    private lateinit var viewModel:ProfileViewModel
    var isEditMode = false
    lateinit var viewFields: Map<String, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_constraint)
        initViews(savedInstanceState)
        initViewModel()
    }



    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putBoolean(IS_EDIT_MODE,isEditMode)
    }

    private fun initViewModel(){
        val viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getProfileData().observe(this, Observer { updateUI(it) })
    }

    private fun updateUI(profile: Profile) {
        profile.toMap().also{
            for((k,v) in viewFields){
                v.text = it[k].toString()
            }
        }
    }

    private fun initViews(savedInstanceState: Bundle?){
        viewFields = mapOf(
            "nickMame" to tv_nick_name,
            "rank" to tv_rank,
            "firstName" to et_first_name,
            "lastName" to et_last_name,
            "about" to et_about,
            "repository" to et_repository,
            "rating" to tv_rating,
            "respect" to tv_respect
        )
        isEditMode = savedInstanceState?.getBoolean(IS_EDIT_MODE, false) ?: false
        showCurrentMode(isEditMode)
        btn_edit.setOnClickListener{
            saveProfileInfo()
            isEditMode = !isEditMode
            showCurrentMode(isEditMode)
        }
    }

    private fun showCurrentMode(isEdtit:Boolean){
        val info = viewFields.filter{setOf("firstName","lastName","about","repository").contains(it.key)}
        for((_,v) in info){
            v as EditText
            v.isFocusable = isEdtit
            v.isFocusableInTouchMode = isEdtit
            v.isEnabled = isEdtit
            v.background.alpha = if(isEdtit) 255 else 0
        }
        ic_eye.visibility = if(isEdtit) View.GONE  else View.VISIBLE
        wr_about.isCounterEnabled = isEdtit
        with(btn_edit){
            val filter: ColorFilter? = if(isEdtit){
                PorterDuffColorFilter(resources.getColor(R.color.color_accent, theme),
                    PorterDuff.Mode.SRC_IN)
            }else{
                null
            }
            val icon = if(isEdtit){
                resources.getDrawable(R.drawable.ic_save_black_24dp, theme)
            }else{
                resources.getDrawable(R.drawable.ic_edit_black_24dp, theme)
            }

            background.colorFilter = filter
            setImageDrawable(icon)
        }
    }

    private fun saveProfileInfo(){
        Profile(
            firstName = et_first_name.text.toString(),
            lastName = et_last_name.text.toString(),
            about = et_about.text.toString(),
            repository = et_repository.text.toString()
        ).apply { viewModel.saveProfileData(this) }
    }
}
