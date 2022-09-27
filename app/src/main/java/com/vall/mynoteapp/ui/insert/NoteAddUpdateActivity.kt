package com.vall.mynoteapp.ui.insert

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vall.mynoteapp.R
import com.vall.mynoteapp.database.Note
import com.vall.mynoteapp.databinding.ActivityNoteAddUpdateBinding
import com.vall.mynoteapp.helper.DateHelper
import com.vall.mynoteapp.helper.ViewModelFactory

class NoteAddUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = "extra_note"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: Note? = null
    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private lateinit var binding: ActivityNoteAddUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAddUpdateViewModel = obtainViewModel(this@NoteAddUpdateActivity)

        binding.btnSubmit.setOnClickListener {
            val title = binding.edtTitle.text.toString().trim()
            val description = binding.edtDescription.text.toString().trim()

            when {
                title.isEmpty() -> binding.edtTitle.error = getString(R.string.empty)
                description.isEmpty() -> binding.edtDescription.error = getString(R.string.empty)
                else -> {
                    note.let {
                        it?.title = title
                        it?.description = description
                    }
                    if (isEdit) {
                        noteAddUpdateViewModel.update(note as Note)
                        showToast(getString(R.string.changed))
                    } else {
                        note.let {
                            it?.date = DateHelper.getCurrentDate()
                        }
                        noteAddUpdateViewModel.insert(note as Note)
                        showToast(getString(R.string.added))
                    }
                    finish()
                }
            }
        }

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            isEdit = true
        } else {
            note = Note()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note.let { note ->
                    binding.edtTitle.setText(note?.title)
                    binding.edtDescription.setText(note?.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnSubmit.text = btnTitle
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form,menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun showToast(massage: String) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(noteAddUpdateActivity: NoteAddUpdateActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(noteAddUpdateActivity.application)
        return ViewModelProvider(noteAddUpdateActivity, factory)[NoteAddUpdateViewModel::class.java]
    }
}