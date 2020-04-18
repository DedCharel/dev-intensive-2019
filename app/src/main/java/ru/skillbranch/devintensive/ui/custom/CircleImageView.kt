package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr:Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs,defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = 1
        private const val DEFAULT_BORDER_WIDTH =2
    }

    private var cv_borderColor = DEFAULT_BORDER_COLOR
    private var cv_borderWidth = DEFAULT_BORDER_WIDTH
    var paint : Paint? = null

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            cv_borderColor = a.getColor(
                R.styleable.CircleImageView_cv_borderColor,
                DEFAULT_BORDER_COLOR
            )
            cv_borderWidth = a.getDimensionPixelSize(
                R.styleable.CircleImageView_cv_borderWidth,
                DEFAULT_BORDER_WIDTH
            )
            a.recycle()
        }
    }

    @Dimension
    fun getBorderWidth() : Int{
        return cv_borderWidth.toInt()
    }

       fun setBorderWidth(@Dimension dp:Int) {
        cv_borderWidth = dp
    }

    fun getBorderColor(): Int{
        return cv_borderColor
    }

    fun setBorderColor(hex:String){

    }
    fun setBorderColor(@ColorRes colorId: Int){
        cv_borderColor = colorId
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.apply {

       //     drawCircle(width.toFloat(),height.toFloat(),getBorderWidth().toFloat(),paint)
        }
    }
}