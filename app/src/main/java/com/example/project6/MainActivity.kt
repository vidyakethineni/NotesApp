package com.example.project6

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val viewModel: NotesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<MaterialToolbar>(R.id.notes_toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.title = when (destination.id) {
                R.id.notesFragment -> "Notes"
                R.id.editNoteFragment -> "Notes"
                R.id.signInFragment -> "Notes"
                R.id.signUpFragment -> "Notes"
                else -> "Notes"
            }
            updateOptionsMenu()

            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toolbar.navigationIcon = null

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.notes_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val addNoteItem = menu.findItem(R.id.notesToolbarAddNote)
        val signOutItem = menu.findItem(R.id.notesToolbarSignOut)
        val deleteItem = menu.findItem(R.id.noteItemToolbarDeleteNote)

        val currentDestination = navController.currentDestination?.id
        when (currentDestination) {
            R.id.notesFragment -> {
                addNoteItem.isVisible = true
                signOutItem.isVisible = true
                deleteItem.isVisible = false
            }
            R.id.editNoteFragment -> {
                addNoteItem.isVisible = false
                signOutItem.isVisible = false
                deleteItem.isVisible = true
            }
            else -> {
                addNoteItem.isVisible = false
                signOutItem.isVisible = false
                deleteItem.isVisible = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.notesToolbarAddNote -> {
                viewModel.onNewNoteClicked()
                true
            }
            R.id.notesToolbarSignOut -> {
                viewModel.signOut()
                true
            }
            R.id.noteItemToolbarDeleteNote -> {
                val currentNoteId = viewModel.noteId
                ConfirmDeleteDialogFragment(currentNoteId,::yesPressed).show(supportFragmentManager,
                    ConfirmDeleteDialogFragment.TAG)
                true
            } else -> {
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
            }
        }
    }

    private fun yesPressed(noteId: String) {
        viewModel.deleteNote(noteId)
        navController.navigateUp()
    }

    fun updateOptionsMenu() {
        invalidateOptionsMenu()
    }
}