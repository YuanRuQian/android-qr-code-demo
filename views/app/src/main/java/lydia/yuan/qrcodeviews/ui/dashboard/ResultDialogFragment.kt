package lydia.yuan.qrcodeviews.ui.dashboard

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ResultDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(word: String): ResultDialogFragment {
            val fragment = ResultDialogFragment()
            val args = Bundle()
            args.putString("RESULT", word)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val result = arguments?.getString("RESULT")

        return activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage("QR Code Detected: $result")
                .setPositiveButton("OK") { dialog, id ->
                    // START THE GAME!
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
