package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.Utils
import kotlin.math.roundToInt

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr:Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs,defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH =2

        private const val DEFAULT_SIZE = 40
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var initials:String? = null
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val initialsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()

    private var isAvatarMode = true

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            borderColor = a.getColor(
                R.styleable.CircleImageView_cv_borderColor,
                DEFAULT_BORDER_COLOR
            )
            borderWidth = a.getDimension(
                R.styleable.CircleImageView_cv_borderWidth,
                 DEFAULT_BORDER_WIDTH.toFloat()
            ).roundToInt()
            initials = a.getString(R.styleable.CircleImageView_cv_initials) ?: "??"
            a.recycle()
        }
        Log.d("M_CircleImageView","$borderWidth")
        setup()
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("M_CircleImageView","""
            onMeasure
            width: ${MeasureSpec.toString(widthMeasureSpec)}
            height: ${MeasureSpec.toString(heightMeasureSpec)}""".trimIndent())

        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
        Log.d("M_CircleImageView","onMeasure after set size $measuredWidth $measuredHeight")
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Log.d("M_CircleImageView","onSizeChanged")
        if (w==0) return
        with(viewRect){
            left = 0
            top = 0
            right = w
            bottom = h
        }
        prepareShader(w,h)
    }
    @Dimension
    fun getBorderWidth() : Int{
        return borderWidth.toInt()
    }

       fun setBorderWidth(@Dimension dp:Int) {
           borderWidth =  dp
           this.invalidate()
    }

    fun getBorderColor(): Int{
        return borderColor
    }

    fun setBorderColor(hex:String){
        borderColor = Color.parseColor(hex)
        this.invalidate()
    }


    fun setBorderColor(@ColorRes colorId: Int){
        borderColor = ContextCompat.getColor(App.applicationContext(), colorId)
        this.invalidate()
    }

    fun updateAvatar(){

    }

    fun setInitials(init: String?){
        if (init!=null && init.isNotEmpty()){
        initials = init
        isAvatarMode = false}
    }

    fun getInitials():String?{
        return initials
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable != null && isAvatarMode){
            drawAvatar(canvas)
        }else{
            drawInitials(canvas)
        }
        //resize rect
        val half = (borderWidth/2).toInt()
        viewRect.inset(half,half)
        canvas.drawOval(viewRect.toRectF(),borderPaint)
    }
    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        if (isAvatarMode) prepareShader(width,height)

    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        if (isAvatarMode) prepareShader(width,height)

    }

    override fun setImageResource(resId: Int) {
        super.setImageResource(resId)
        if (isAvatarMode) prepareShader(width,height)

    }
    private fun setup() {
        with(borderPaint){
            style = Paint.Style.STROKE
            strokeWidth = borderWidth.toFloat()
            color = borderColor
        }
    }
    private fun prepareShader(w: Int, h: Int){
        //prepare buffer this
        if (w ==0 || drawable == null) return
        val srcBm = drawable.toBitmap(w,h, Bitmap.Config.ARGB_8888)
        avatarPaint.shader =  BitmapShader(srcBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

    }

    private fun resolveDefaultSize(spec:Int):Int{
        return when (MeasureSpec.getMode(spec)){
            MeasureSpec.UNSPECIFIED -> Utils.dpToPix(context, DEFAULT_SIZE).toInt() //resolve default size
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec) // from spec
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec) // from spec
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun drawAvatar(canvas: Canvas){
        canvas.drawOval(viewRect.toRectF(),avatarPaint)
    }
    private fun drawInitials(canvas: Canvas){
        val value = TypedValue()
        App.applicationContext().theme.resolveAttribute(R.attr.colorAccent, value,true)
        initialsPaint.color = value.data
        canvas.drawOval(viewRect.toRectF(), initialsPaint)
        with(initialsPaint){
            color =Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = height * 0.33f
        }
        val offsetY = (initialsPaint.descent() + initialsPaint.ascent())/2
        canvas.drawText(initials,viewRect.exactCenterX(),viewRect.exactCenterY()-offsetY, initialsPaint)
    }
}