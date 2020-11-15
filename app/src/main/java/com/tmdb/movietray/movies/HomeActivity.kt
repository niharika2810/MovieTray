package com.tmdb.movietray.movies

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.movietray.base.data.local.DataStoreProvider
import com.movietray.base.extension.buildDialog
import com.movietray.base.extension.negativeAction
import com.movietray.base.extension.positiveAction
import com.movietray.base.extension.title
import com.movietray.base.utils.dialog.AlertDialogView
import com.tmdb.movietray.R
import com.tmdb.movietray.movies.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

/**
 * @author Niharika.Arora
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeFragment.HomeCallback {


    private var alertDialogView: AlertDialogView? = null


    companion object {
        const val FADING_ANIMATION_DURATION = 200L
        const val ALPHA_TRANSPARENT = 0.0f
    }

    private lateinit var dataStoreProvider: DataStoreProvider
    private var isEditing: Boolean = false
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_JetPackTmdb_NoActionBar);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val (profileEdit, profileName) = initNavViews()

        dataStoreProvider = DataStoreProvider(this)

        configureUserName(profileName)
        onProfileEditClick(profileEdit, profileName)
        configureNavController()
    }

    private fun initNavViews(): Pair<AppCompatImageView, AppCompatEditText> {
        val profileEdit = nav_view.getHeaderView(0).profile_edit
        val profileName = nav_view.getHeaderView(0).profile_name
        return Pair(profileEdit, profileName)
    }

    private fun configureUserName(profileName: AppCompatEditText) {
        dataStoreProvider.getValue(DataStoreProvider.KEY_NAME).asLiveData()
            .observe(this, { it ->
                if (it.isNullOrEmpty()) {
                    profileName.setText("Hello User")
                } else {
                    profileName.setText(it)
                }
            })
    }

    private fun onProfileEditClick(
        profileEdit: ImageView,
        profileName: EditText
    ) {
        profileEdit.setOnClickListener {
            isEditing = !isEditing
            profileName.isEnabled = !profileName.isEnabled
            if (isEditing) {
                onEditingEnabled(profileName, profileEdit)
            } else {
                onEditingDisabled(profileName, profileEdit)
            }
        }
    }

    private fun onEditingDisabled(
        profileName: EditText,
        profileEdit: ImageView
    ) {
        profileName.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
        drawer_layout.closeDrawer(Gravity.LEFT)
        profileEdit.setImageResource(R.drawable.ic_baseline_edit_24)
        Toast.makeText(this, resources.getString(R.string.changes_saved), Toast.LENGTH_LONG).show()
    }

    private fun onEditingEnabled(
        profileName: EditText,
        profileEdit: ImageView
    ) {
        profileName.requestFocus()
        showKeyBoard(profileName)
        profileName.setSelection(profileName.text.toString().length)
        profileEdit.setImageResource(R.drawable.ic_baseline_done_24)
    }

    private fun showKeyBoard(profileName: EditText) {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(profileName, InputMethodManager.SHOW_FORCED)
    }

    private fun configureNavController() {
        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_about
            ), drawer_layout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressedFromHome() {
        configureDialog()
    }

    private fun configureDialog() {
        createAlertDialog(getString(R.string.dialog_title),
            getString(R.string.dialog_negative_answer),
            getString(R.string.dialog_positive_answer),
            { finish() },
            { removeDialogPopup() }).let {
            alertDialogView = it
            rootView.addView(
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
                    rootView.removeView(it)
                }
                .start()
        }
    }
}