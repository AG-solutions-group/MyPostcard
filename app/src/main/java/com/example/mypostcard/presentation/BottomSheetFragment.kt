package app.presentation

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.mypostcard.databinding.BottomsheetFragmentBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class BottomSheetFragment : DialogFragment() {

    // Lazy Inject ViewModel
     val myViewModel: ArchiveVM by sharedViewModel()


    private var _binding: BottomsheetFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = BottomsheetFragmentBinding.inflate(LayoutInflater.from(context))
        binding.imgCancel.setOnClickListener(){
            this.dismiss()
        }
        // observe the ViewModel data
        observe()

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .create()
    }

    fun observe (){
        // show the data on views
        myViewModel.orderListDetailsFromOverview.observe(this) {
            binding.txtProductType.text = it.type_name
            Glide.with(requireContext())
                .load(it.image)
                .centerCrop()
                .into(binding.imgProduct)
            binding.txtOrderStatus.text = it.status
            binding.txtOrderDate.text = it.creation_date.toString()
        }
        myViewModel.orderListDetails.observe(this) {
            binding.txtRecipientCompany.text = it.company
            binding.txtRecipientName.text = it.name
            binding.txtRecipientAddress.text = it.address
            binding.txtRecipientZip.text = it.zip
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}