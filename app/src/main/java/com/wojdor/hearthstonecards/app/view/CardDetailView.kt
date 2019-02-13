package com.wojdor.hearthstonecards.app.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.app.extension.copyOnLongClick
import com.wojdor.hearthstonecards.app.extension.inflate
import kotlinx.android.synthetic.main.layout_card_detail_view.view.*

class CardDetailView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    var label: CharSequence?
        get() = cardDetailLabelTv.text.toString()
        set(value) {
            value.let { cardDetailLabelTv.text = it }
            setupCopyText()
        }

    var text: CharSequence?
        get() = cardDetailTextTv.text.toString()
        set(value) {
            value.let { cardDetailTextTv.text = it }
            setupCopyText()
        }

    init {
        inflate(R.layout.layout_card_detail_view, true)
        context.theme.obtainStyledAttributes(
                attributeSet, R.styleable.CardDetailView, DEFAULT_STYLE_ATTR, DEFAULT_STYLE_RES).apply {
            try {
                label = getString(R.styleable.CardDetailView_label)
                text = getString(R.styleable.CardDetailView_text)
            } finally {
                recycle()
            }
        }
    }

    private fun setupCopyText() {
        cardDetailContentLl.copyOnLongClick(label, text)
    }

    companion object {
        private const val DEFAULT_STYLE_ATTR = 0
        private const val DEFAULT_STYLE_RES = 0
    }
}
