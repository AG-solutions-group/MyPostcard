package app.presentation


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import app.PostcardOverviewAdapter
import com.example.mypostcard.databinding.ActivityArchiveBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArchiveActivity : AppCompatActivity(), PostcardOverviewAdapter.OnClickListener {

    lateinit var binding: ActivityArchiveBinding
    private lateinit var postcardOverviewAdapter: PostcardOverviewAdapter

    // Lazy Inject ViewModel
    private val myViewModel by viewModel<ArchiveVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observe() // observe data we get back from API via ViewModel
        initialize()
        setupRecyclerView()
        loadData ()
    }

    override fun onBackPressed() {
        finish ()
    }

    private fun observe() {
        myViewModel.orderListOverview.observe(this) {
            postcardOverviewAdapter.orders = it
        }
        myViewModel.failure.observe(this) {
            if (it != "") {
                Snackbar.make(binding.snackbar, it.toString(), Snackbar.LENGTH_LONG).show()
                myViewModel.resetFailure()
            }
        }
    }

    private fun initialize() {
        binding.backBtn.setOnClickListener(){
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.apply {
                setMessage("Do you really want to leave this minimalistic approach of an archive already? :( ?")
                setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                setNegativeButton("No") { _, _ ->
                }
            }.create().show()
        }
    }

    private fun setupRecyclerView() = binding.recyclerOverview.apply {

        // set up recycler
        postcardOverviewAdapter = PostcardOverviewAdapter(this@ArchiveActivity)
        adapter = postcardOverviewAdapter
        layoutManager = LinearLayoutManager(this@ArchiveActivity)

    }

    private fun loadData (){

        // TODO show progress bar while loading
        // load overview data
        myViewModel.loadOverviewDataUseCase()
    }

    override fun onClick(position: Int) {
        // load details data
        myViewModel.loadDetailsDataUseCase(postcardOverviewAdapter.orders[position].id, position)
        // show dialog fragment
        var dialogFragment = BottomSheetFragment()
        dialogFragment.show(supportFragmentManager, "DetailsFragment")
    }
}