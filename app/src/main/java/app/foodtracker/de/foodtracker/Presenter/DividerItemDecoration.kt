package app.foodtracker.de.foodtracker.Presenter

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.graphics.drawable.Drawable
import android.view.View
import android.opengl.ETC1.getWidth
import android.util.Log


/**
 * Created by normen on 20.01.18.
 */
class DividerItemDecoration(divider: Drawable): RecyclerView.ItemDecoration() {

    private lateinit var  mDivider: Drawable

    init {
        mDivider = divider
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if(parent?.getChildAdapterPosition(view) == 0){
            return
        }
        outRect?.top = mDivider.intrinsicHeight
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {

        if(parent == null) {
            Log.d("DividerItem","parent is null")
            return
        }
        val dividerRight = parent!!.getWidth() - parent.getPaddingRight()

        val dividerLeft = parent.getPaddingLeft()

        val childCount = parent.getChildCount()
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + mDivider.intrinsicHeight

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            mDivider.draw(c)
        }


    }



}