package com.tmdb.movietray.movies

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.movietray.base.extension.buildDialog
import com.movietray.base.extension.negativeAction
import com.movietray.base.extension.positiveAction
import com.movietray.base.extension.title
import com.movietray.base.utils.dialog.AlertDialogView
import com.tmdb.movietray.R
import com.tmdb.movietray.databinding.ActivityMainBinding
import com.tmdb.movietray.databinding.NavHeaderMainBinding
import com.tmdb.movietray.movies.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Niharika.Arora
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeFragment.HomeCallback {

    private lateinit var navHeaderBinding: NavHeaderMainBinding
    private lateinit var binding: ActivityMainBinding
    private var alertDialogView: AlertDialogView? = null
    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        const val FADING_ANIMATION_DURATION = 200L
        const val ALPHA_TRANSPARENT = 0.0f
    }

    private var isEditing: Boolean = false
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_JetPackTmdb_NoActionBar);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val headerView = binding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderMainBinding.bind(headerView)
        setSupportActionBar(binding.appBarContainer.toolbar)


        homeViewModel.configureUserName()
        observeUserName()
        onProfileEditClick()
        configureNavController()
    }

    private fun observeUserName() {
        homeViewModel.userName.observe(this, {
            navHeaderBinding.profileName.setText(it)
        })
    }


    private fun onProfileEditClick() {
        navHeaderBinding.profileEdit.setOnClickListener {
            isEditing = !isEditing
            navHeaderBinding.profileName.isEnabled = !navHeaderBinding.profileName.isEnabled
            if (isEditing) {
                onEditingEnabled()
            } else {
                onEditingDisabled()
            }
        }
    }

    private fun onEditingDisabled() {
        navHeaderBinding.profileName.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.transparent
            )
        )
        binding.drawerLayout.closeDrawer(Gravity.LEFT)
        navHeaderBinding.profileEdit.setImageResource(R.drawable.ic_baseline_edit_24)
        storeUserName()
        Toast.makeText(this, resources.getString(R.string.changes_saved), Toast.LENGTH_LONG).show()
    }

    private fun storeUserName() {
        removeDialogPopup()
        homeViewModel.setUserName(navHeaderBinding.profileName.text.toString())
    }

    private fun onEditingEnabled() {
        val profileName = navHeaderBinding.profileName
        profileName.requestFocus()
        showKeyBoard(profileName)
        profileName.setSelection(profileName.text.toString().length)
        navHeaderBinding.profileEdit.setImageResource(R.drawable.ic_baseline_done_24)
    }

    private fun showKeyBoard(profileName: EditText) {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(profileName, InputMethodManager.SHOW_FORCED)
    }

    private fun configureNavController() {
        val navController = findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about
            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }

    private fun findNavController(): NavController {
        return (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController().navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressedFromHome() {
        configureDialog()
    }

    private fun configureDialog() {
        binding.drawerLayout.closeDrawer(Gravity.LEFT)
        createAlertDialog(getString(R.string.dialog_title),
            getString(R.string.dialog_negative_answer),
            getString(R.string.dialog_positive_answer),
            { finish() },
            { removeDialogPopup() }).let {
            alertDialogView = it
            binding.appBarContainer.contentMainContainer.rootView.addView(
                alertDialogView,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
        }
    }

    private fun createAlertDialog(
        titleText: String,
        negativeText: String,
        positiveText: String,
        positiveClickAction: () -> Unit,
        negativeClickAction: () -> Unit
    ) =
        buildDialog(this) {
            title(titleText)
            positiveAction(positiveText) { positiveClickAction }
            negativeAction(negativeText) { negativeClickAction }

        }

    private fun removeDialogPopup() {
        alertDialogView?.let {
            it.animate()
                .alpha(ALPHA_TRANSPARENT)
                .setDuration(FADING_ANIMATION_DURATION)
                .withEndAction {
                    binding.appBarContainer.contentMainContainer.rootView.removeView(it)
                }
                .start()
        }
    }
}