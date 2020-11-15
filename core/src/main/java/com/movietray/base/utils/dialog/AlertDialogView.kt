package com.movietray.base.utils.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.tmdb.core.databinding.AlertDialogViewBinding

/**
 * @author Niharika.Arora
 */
class AlertDialogView : FrameLayout {
    private lateinit var binding: AlertDialogViewBinding
    private lateinit var title: String
    private lateinit var negativeText: String
    private lateinit var positiveText: String
    private lateinit var onNegativeClickAction: () -> Unit
    private lateinit var onPositiveClickAction: () -> Unit

    constructor(context: Context, builder: AlertDialogBuilder) : super(context, null) {
        title = builder.titleText
        negativeText = builder.negativeText
        positiveText = builder.positiveText
        onNegativeClickAction = builder.onNegativeClickAction
        onPositiveClickAction = builder.onPositiveClickAction

        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        inflateLayout(context!!)
        configureText()
        setClickListeners()
    }

    private fun inflateLayout(context: Context): AlertDialogViewBinding {
        binding = AlertDialogViewBinding.inflate(LayoutInflater.from(context), this, true)
        return binding
    }

    private fun configureText() {
        binding.titleView.text = title
        binding.negativeView.text = negativeText
        binding.positiveView.text = positiveText
    }

    private fun setClickListeners() {
        binding.negativeView.setOnClickListener { onNegativeClickAction.invoke() }
        binding.positiveView.setOnClickListener { onPositiveClickAction.invoke() }
    }

    class AlertDialogBuilder {
        var context: Context? = null
        var titleText: String = ""
        var negativeText: String = ""
        var positiveText: String = ""
        var onNegativeClickAction: () -> Unit = {}
        var onPositiveClickAction: () -> Unit = {}

        fun build() = AlertDialogView(context!!, this)
    }
}