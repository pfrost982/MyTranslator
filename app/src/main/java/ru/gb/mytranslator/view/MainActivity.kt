package ru.gb.mytranslator.view

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnticipateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import geekbrains.ru.translator.R
import geekbrains.ru.translator.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.getOrCreateScope
import org.koin.core.scope.Scope
import ru.gb.data.AppState
import ru.gb.data.DataModel
import ru.gb.mytranslator.view_model.MainViewModel

class MainActivity : AppCompatActivity(), KoinScopeComponent {

    private lateinit var binding: ActivityMainBinding

    override val scope: Scope by getOrCreateScope()
    val model: MainViewModel by viewModel()

    private var adapter: MainAdapter? = null

    private val onItemClick = { data: DataModel -> showDescription(data) }

    private fun showDescription(data: DataModel) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container,
                DescriptionFragment(data)
            )
            .commit()
    }

    private val onSearchClick = { searchWord: String -> model.getData(searchWord, true) }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                val toast = { text: String ->
                    Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
                }
                model.getHistory(toast)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= 31) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->

                val rotate = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.ROTATION_X,
                    0f,
                    360f
                )
                rotate.duration = 2000
                rotate.doOnEnd {
                    val slideLeft = ObjectAnimator.ofFloat(
                        splashScreenView,
                        View.TRANSLATION_X,
                        0f,
                        -splashScreenView.height.toFloat()
                    )
                    slideLeft.interpolator = AnticipateInterpolator()
                    slideLeft.duration = 2000
                    slideLeft.doOnEnd { splashScreenView.remove() }
                    slideLeft.start()
                }
                rotate.start()
            }
        }

        model.subscribe().observe(this@MainActivity) { renderData(it) }

        binding.searchFab.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClick)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                if (data == null || data.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                } else {
                    showViewSuccess()
                    if (adapter == null) {
                        binding.mainActivityRecyclerview.layoutManager =
                            LinearLayoutManager(applicationContext)
                        binding.mainActivityRecyclerview.adapter =
                            MainAdapter(onItemClick, data)
                    } else {
                        adapter?.setData(data)
                    }
                }
            }
            is AppState.Loading -> {
                showViewLoading()
                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = VISIBLE
                    binding.progressBarRound.visibility = GONE
                    binding.progressBarHorizontal.progress = appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = GONE
                    binding.progressBarRound.visibility = VISIBLE
                }
            }
            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener {
            model.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = VISIBLE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = VISIBLE
        binding.errorLinearLayout.visibility = GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = GONE
        binding.loadingFrameLayout.visibility = GONE
        binding.errorLinearLayout.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
    }
}
