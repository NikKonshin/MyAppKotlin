package com.example.myappkotlin.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FABScroll(private val context: Context, private val attrs: AttributeSet) :
    AppBarLayout.ScrollingViewBehavior(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout, child: View, dependency: View
    ): Boolean {
        return dependency is FloatingActionButton
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View,
        target: View, axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int,
        dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray
    ) {
        super.onNestedScroll(
            coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
        if (dyConsumed > 0) {
            val layoutParams = child.layoutParams as CoordinatorLayout.LayoutParams
            val fabBottomMargin = layoutParams.bottomMargin
            child.animate().translationY((child.height + fabBottomMargin).toFloat())
                .setInterpolator(
                    LinearInterpolator()
                ).start()
        } else if (dyConsumed < 0) {
            child.animate().translationY(0F).setInterpolator(LinearInterpolator()).start()
        }
    }
}



