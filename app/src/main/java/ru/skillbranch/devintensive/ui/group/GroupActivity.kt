package ru.skillbranch.devintensive.ui.group

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_group.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_profile_constraint.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.ui.adapters.UserAdapter
import ru.skillbranch.devintensive.viewmodels.GroupViewModel

class GroupActivity : AppCompatActivity() {

    private lateinit var usersAdapter: UserAdapter
    private lateinit var viewModel: GroupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        initToolBar()
        initViews()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchWiew = searchItem?.actionView as SearchView
        searchWiew.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.handleSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.handleSearchQuery(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun initToolBar() {
        setSupportActionBar(toolbar)
    }

    private fun initViews() {
        usersAdapter = UserAdapter { viewModel.handleSelectedItem(it.id) }
        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        with(rv_user_list){
            adapter = usersAdapter
            layoutManager = LinearLayoutManager(this@GroupActivity)
            addItemDecoration(divider)
        }
        fab_ga.setOnClickListener{
            viewModel.handleCreateGroup()
            finish()
            overridePendingTransition(R.anim.idle, R.anim.bottom_down)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GroupViewModel::class.java)
        viewModel.getUserData().observe(this, Observer { usersAdapter.updateData(it) })
        viewModel.getSelectedData().observe(this, Observer {
            updateChips(it)
            toogleFab(it.size>1)
        })
    }

    private fun toogleFab(isShow: Boolean) {
        if(isShow) fab_ga.show()
        else fab_ga.hide()
    }

    private fun addChipToGroup(user:UserItem){
        val chip = Chip(this).apply{
            text = user.fullName
            chipIcon = resources.getDrawable(R.drawable.avatar_default, theme)
            isCloseIconVisible = true
            tag = user.id
            isClickable = true
            closeIconTint = ColorStateList.valueOf(Color.WHITE)
            chipBackgroundColor = ColorStateList.valueOf(getColor(R.color.color_primary_light))
            setTextColor(Color.WHITE)
        }
        chip.setOnCloseIconClickListener{viewModel.handleRemoveChip(it.tag.toString())}
        chip_group.addView(chip)
    }

    private fun updateChips(listUsers: List<UserItem>){
        chip_group.visibility = if(listUsers.isEmpty()) View.GONE else View.VISIBLE
        val users = listUsers
                .associate { user -> user.id to user }
                .toMutableMap()
        val views = chip_group.children.associate { view -> view.tag to view}

        for((k,v) in views){
            if(!users.containsKey(k)) chip_group.removeView(v)
            else users.remove(k)
        }
        users.forEach{(_,v) -> addChipToGroup(v)}
    }

}
