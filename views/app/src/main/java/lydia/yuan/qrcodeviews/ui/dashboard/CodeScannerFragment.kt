package lydia.yuan.qrcodeviews.ui.dashboard

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import lydia.yuan.qrcodeviews.databinding.FragmentCodeScannerBinding


class CodeScannerFragment : Fragment() {

    private lateinit var barcodeLauncher: ActivityResultLauncher<ScanOptions>

    private var _binding: FragmentCodeScannerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        barcodeLauncher = registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                ResultDialogFragment.newInstance(result.contents).show(parentFragmentManager, "RESULT_DIALOG")
            }
        }

        with(binding) {
            buttonScanCode.setOnClickListener {
                val options = ScanOptions()
                options.setOrientationLocked(false)
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                barcodeLauncher.launch(options)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}