package de.anew.activities.tasks

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.anew.R
import de.anew.TasksApplication
import de.anew.activities.ActivityArgumentKeys.TASK_ID
import de.anew.activities.editor.EditorActivity

class TasksActivity : AppCompatActivity(), TaskItemClickListener {

    private lateinit var tasksViewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_activity)
        setSupportActionBar(findViewById(R.id.tasks_toolbar))
        tasksViewModel = createTasksViewModel()
        val taskAdapter = TaskAdapter(tasksViewModel, this)
        tasksViewModel.tasks.observe(this, Observer { taskAdapter.notifyDataSetChanged() })
        val tasksView = createTasksView(taskAdapter)
        ItemTouchHelper(TaskTouchHelper(taskAdapter)).attachToRecyclerView(tasksView)
    }

    private fun createTasksViewModel(): TasksViewModel {
        val tasksApplication = application as TasksApplication
        val tasksRepository = tasksApplication.getModelComponent().getTasksRepository()
        val tasksViewModelFactory = TasksViewModelFactory(tasksRepository)
        return ViewModelProvider(this, tasksViewModelFactory).get(TasksViewModel::class.java)
    }

    private fun createTasksView(taskAdapter: TaskAdapter): RecyclerView {
        val linearLayoutManager = LinearLayoutManager(this)
        val tasksView = findViewById<View>(R.id.recycler_view) as RecyclerView
        tasksView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = taskAdapter
        }
        return tasksView
    }

    override fun taskItemClickedAt(position: Int) {
        tasksViewModel.tasks.value?.elementAt(position)?.let {
            val intent = Intent(this, EditorActivity::class.java).apply {
                putExtra(TASK_ID, it.id)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tasks_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.new_menu_item -> startActivity(Intent(this, EditorActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}