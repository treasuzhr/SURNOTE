package com.app.surnote.v1

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent // Tambahkan import ini
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val todoList = mutableListOf<TodoItem>()
    private lateinit var adapter: TodoAdapter
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set tanggal default = hari ini
        val today = Calendar.getInstance()
        selectedDate = "${today.get(Calendar.DAY_OF_MONTH)}/${today.get(Calendar.MONTH) + 1}/${today.get(Calendar.YEAR)}"

        // 1. Setup Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_calendar -> {
                    Toast.makeText(this, "Calendar Terpilih", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_notebook -> {
                    // --- BAGIAN YANG DIUPDATE ---
                    Toast.makeText(this, "Notebook Terpilih", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, NoteListActivity::class.java)
                    startActivity(intent)
                    // ----------------------------
                    true
                }
                R.id.nav_quick -> {
                    Toast.makeText(this, "Quick Terpilih", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    Toast.makeText(this, "Profil Terpilih", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // 2. Setup RecyclerView
        adapter = TodoAdapter(todoList)
        val rvTodoList = findViewById<RecyclerView>(R.id.rvTodoList)
        rvTodoList.layoutManager = LinearLayoutManager(this)
        rvTodoList.adapter = adapter

        // 3. Setup Kalender — filter list saat tanggal dipilih
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView?.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            adapter.filter(selectedDate)
        }

        // Filter awal untuk hari ini
        adapter.filter(selectedDate)

        // 4. Tombol Tambah To-Do
        val btnAddTodo = findViewById<MaterialButton>(R.id.btnAddTodo)
        btnAddTodo.setOnClickListener {
            showAddTodoDialog()
        }
    }

    private fun showAddTodoDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_todo, null)
        val etTaskName = dialogView.findViewById<EditText>(R.id.etTaskName)
        val etTaskTime = dialogView.findViewById<EditText>(R.id.etTaskTime)

        // Klik field jam → muncul TimePicker
        etTaskTime.isFocusable = false
        etTaskTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(this, { _, hour, minute ->
                etTaskTime.setText(String.format("%02d:%02d", hour, minute))
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Tambah") { _, _ ->
                val name = etTaskName.text.toString()
                val time = etTaskTime.text.toString()
                if (name.isNotEmpty()) {
                    todoList.add(TodoItem(name, time, selectedDate))
                    adapter.filter(selectedDate)
                } else {
                    Toast.makeText(this, "Nama tugas tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}