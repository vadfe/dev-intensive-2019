package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import ru.skillbranch.devintensive.R
import android.graphics.RectF
import android.graphics.Path
import android.util.Log

import android.graphics.Paint


class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet? = null,
    defStyleAttr:Int = 0
    ):ImageView(context,attrs, defStyleAttr) {
    companion object{
        private const val DEFAULT_ASPECT_RATIO = 1.78f
    }

    private var aspectRatio = DEFAULT_ASPECT_RATIO
    private val radius = 58.0f
    private var path: Path? = null
    private var rect: RectF? = null

    init {
        if(attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView)
            aspectRatio = a.getFloat(R.styleable.AspectRatioImageView_aspectRatio, DEFAULT_ASPECT_RATIO)
            a.recycle()
        }
        path = Path()

    }

    fun getBorderWidth():Int = 0
    fun setBorderWidth(dp:Int) = 0
    fun getBorderColor():Int = 0
    fun setBorderColor(hex:String) = 0
    fun setBorderColor(@ColorRes colorId: Int) = 0

    override fun onDraw(canvas: Canvas?) {


        super.onDraw(canvas)
        //rect = RectF(0f, 0f, this.width.toFloat(), this.height.toFloat())
        //path?.addRoundRect(rect, this.width.toFloat()/2, this.width.toFloat()/2, Path.Direction.CW)
        //canvas?.clipPath(path)
        val mBitmapPaint = Paint()
        mBitmapPaint.setAntiAlias(true)
        canvas?.drawCircle(this.width.toFloat()/2,this.height.toFloat()/2, this.width.toFloat()/2, mBitmapPaint);
        Log.d("M_CircleImageView", "onDraw "+this.width+" "+this.height)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("M_CircleImageView", "onMeasure ")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //val newHeight = (measuredWidth/aspectRatio).toInt()
        //setMeasuredDimension(measuredWidth, newHeight)
    }
}