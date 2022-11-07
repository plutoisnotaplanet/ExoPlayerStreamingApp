package com.plutoisnotaplanet.exoplayerstreamingapp.presentation.home_scope.player.quality_dialog

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.plutoisnotaplanet.exoplayerstreamingapp.R
import com.plutoisnotaplanet.exoplayerstreamingapp.databinding.LayoutQualityDialogBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class QualityDialog : DialogFragment(R.layout.layout_quality_dialog) {

    companion object {
        const val TAG = "QualityDialog"
        private const val QUALITY_INTEGER_LIST = "quality_integer_list"

        @JvmStatic
        fun newInstance(list: List<QualityItem>): QualityDialog {
            return QualityDialog().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(QUALITY_INTEGER_LIST, ArrayList(list))
                }
            }
        }
    }

    var onSelectedQuality: ((QualityItem) -> Unit)? = null

    private var _binding: LayoutQualityDialogBinding? = null
    private val binding: LayoutQualityDialogBinding
        get() = _binding!!

    private var qualityAdapter: QualityDialogAdapter? = null
    private val qualityList: List<QualityItem> by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(QUALITY_INTEGER_LIST, QualityItem::class.java) ?: emptyList()
        } else {
            arguments?.getParcelableArrayList(QUALITY_INTEGER_LIST) ?: emptyList()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.let { window ->
            window.setBackgroundDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.quality_dialog_shape
                )
            )
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            val wlp: WindowManager.LayoutParams = window.attributes
            window.setGravity(Gravity.BOTTOM)
            wlp.gravity = Gravity.END
            window.attributes = wlp
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LayoutQualityDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        qualityAdapter?.submitList(qualityList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        qualityAdapter = null
        _binding = null
    }

    private fun setupAdapter() {
        qualityAdapter = QualityDialogAdapter(requireContext()) { quality ->
            onSelectedQuality?.invoke(quality)
            lifecycleScope.launch {
                delay(300)
                dismiss()
            }
        }
        binding.rvQuality.adapter = qualityAdapter
    }
}
