package lydia.yuan.qrcodeviews.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import lydia.yuan.qrcodeviews.R
import lydia.yuan.qrcodeviews.databinding.FragmentCodeImageGeneratorBinding


class CodeImageGenerator : Fragment() {

    private var _binding: FragmentCodeImageGeneratorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeImageGeneratorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        with(binding) {
            buttonGenerateQRCode.isEnabled = false

            editTextEncodedData.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Not needed
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    // Not needed
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    buttonGenerateQRCode.isEnabled = s?.isNotBlank() ?: false
                }
            })

            buttonGenerateQRCode.setOnClickListener {
                generateQRCodeAndUpdatePrevLabel()

            }
        }
        return root
    }

    private fun generateQRCodeAndUpdatePrevLabel() {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val qrCodeText = binding.editTextEncodedData.text.toString()
            val bitmap: Bitmap =
                barcodeEncoder.encodeBitmap(qrCodeText, BarcodeFormat.QR_CODE, 400, 400)
            binding.imageViewQRCode.setImageBitmap(bitmap)
            binding.textViewQRCodeData.text = getString(R.string.current_encoded_data, qrCodeText)
        } catch (e: WriterException) {
            e.printStackTrace()
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}