package com.wojdor.hearthstonecards.app.classcards

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.app.base.BaseFragment
import com.wojdor.hearthstonecards.app.card.CardActivity
import com.wojdor.hearthstonecards.app.extension.inflate
import com.wojdor.hearthstonecards.app.extension.observeNonNull
import com.wojdor.hearthstonecards.domain.Card
import kotlinx.android.synthetic.main.fragment_class_cards.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClassCardsFragment : BaseFragment<ClassCardsViewModel>() {

    override val viewModel: ClassCardsViewModel by viewModel()

    private lateinit var classCardsAdapter: ClassCardsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_class_cards)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponents()
    }

    private fun initComponents() {
        classCardsAdapter = ClassCardsAdapter { view, card -> openCardActivity(view, card) }
        with(classCardsCardsRv) {
            layoutManager = GridLayoutManager(context, calculateNumberOfColumns())
            adapter = classCardsAdapter
        }
    }

    private fun openCardActivity(view: View, card: Card) {
        val bundle = ActivityOptions.makeSceneTransitionAnimation(activity, view,
                view.transitionName).toBundle()
        val intent = Intent(context, CardActivity::class.java).apply {
            putExtra(CardActivity.CARD_ID_EXTRA, card.cardId)
        }
        startActivity(intent, bundle)
    }

    private fun calculateNumberOfColumns(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val numberOfColumns = displayMetrics.widthPixels / COLUMN_WIDTH_DIVIDER
        return if (numberOfColumns < MIN_NUMBER_OF_COLUMNS) MIN_NUMBER_OF_COLUMNS else numberOfColumns
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let { bundle ->
            val className = bundle.getString(CLASS_NAME_EXTRA) ?: return
            viewModel.getCardsFromClass(className).observeNonNull(this) { classCardsAdapter.setItems(it) }
        }
    }

    companion object {
        private const val CLASS_NAME_EXTRA = "CLASS_NAME_EXTRA"
        private const val MIN_NUMBER_OF_COLUMNS = 2
        private const val COLUMN_WIDTH_DIVIDER = 300

        fun newInstance(className: String): ClassCardsFragment {
            return ClassCardsFragment().apply {
                arguments = Bundle().apply { putString(CLASS_NAME_EXTRA, className) }
            }
        }
    }
}
