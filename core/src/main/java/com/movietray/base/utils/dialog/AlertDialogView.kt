package com.movietray.base.utils.dialog

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.tmdb.core.R
import kotlinx.android.synthetic.main.alert_dialog_view.view.*

/**
 * @author Niharika.Arora
 */
class AlertDialogView : LinearLayout {
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

    private fun inflateLayout(context: Context) =
        LayoutInflater.from(context).inflate(R.layout.alert_dialog_view, this, true)

    private fun configureText() {
        title_view.text = title
        negative_view.text = negativeText
        positive_view.text = positiveText
    }

    private fun setClickListeners() {
        negative_view.setOnClickListener { onNegativeClickAction.invoke() }
        positive_view.setOnClickListener { onPositiveClickAction.invoke() }
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