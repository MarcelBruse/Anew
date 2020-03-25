package de.anew.activities.tasks

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import de.anew.R
import de.anew.TasksApplication
import de.anew.activities.ActivityArgumentKeys.TASK_ID
import de.anew.activities.editor.EditorActivity

class TasksActivity : AppCompatActivity(), TaskItemClickListener, OnRefreshListener {

    private val tasksViewCacheSize = 20

    private val maxNumberOfTasks = 50

    private lateinit var tasksViewModel: TasksViewModel

    private lateinit var taskAdapter: TaskAdapter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_activity)
        setSupportActionBar(findViewById(R.id.tasks_toolbar))
        tasksViewModel = createTasksViewModel()
        taskAdapter = TaskAdapter(tasksViewModel, this)
        tasksViewModel.tasks.observe(this, Observer { taskAdapter.setTasks(it) })
        val tasksView = createTasksView()
        setSwipeRefreshListener()
        ItemTouchHelper(TaskTouchHelper(taskAdapter)).attachToRecyclerView(tasksView)
    }

    private fun createTasksViewModel(): TasksViewModel {
        val tasksApplication = application as TasksApplication
        val tasksRepository = tasksApplication.getModelComponent().getTasksRepository()
        val tasksViewModelFactory = TasksViewModelFactory(tasksRepository)
        return ViewModelProvider(this, tasksViewModelFactory).get(TasksViewModel::class.java)
    }

    private fun createTasksView(): RecyclerView {
        val linearLayoutManager = LinearLayoutManager(this)
        val tasksView = findViewById<View>(R.id.recycler_view) as RecyclerView
        tasksView.apply {
            layoutManager = linearLayoutManager
            adapter = taskAdapter
            setHasFixedSize(true)
            setItemViewCacheSize(tasksViewCacheSize)
        }
        return tasksView
    }

    private fun setSwipeRefreshListener() {
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener(this)
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
            R.id.new_menu_item -> startEditorWithNewTask()
            R.id.refresh_items -> taskAdapter.updateView {}
            R.id.third_party_licenses -> startOssLicensesMenuActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startEditorWithNewTask() {
        if (tasksViewModel.getNumberOfTasks() < maxNumberOfTasks) {
            startActivity(Intent(this, EditorActivity::class.java))
        } else {
            Toast.makeText(this, getString(R.string.max_number_of_tasks), Toast.LENGTH_SHORT).show()
        }
    }

    private fun startOssLicensesMenuActivity() {
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.third_party_licenses))
        startActivity(Intent(this, OssLicensesMenuActivity::class.java))
    }

    override fun onResume() {
        taskAdapter.updateView {}
        super.onResume()
    }

    override fun onRefresh() {
        taskAdapter.updateView {
            swipeRefreshLayout.isRefreshing = false
        }
    }

}
