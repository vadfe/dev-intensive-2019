package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import ru.skillbranch.devintensive.R
import android.util.Log

import android.graphics.Shader.TileMode

import android.graphics.*
import android.R.color
import android.graphics.RectF
//import java.awt.AlphaComposite.SRC_IN
import android.graphics.PorterDuffXfermode
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.R.id.mask
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.PorterDuff
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.R.id.mask
import android.graphics.BitmapFactory
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth




class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet? = null,
    defStyleAttr:Int = 0
    ):ImageView(context,attrs, defStyleAttr) {
    companion object{
        //private const val DEFAULT_ASPECT_RATIO = 1.78f
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2F
    }

    //private var aspectRatio = DEFAULT_ASPECT_RATIO
    private var borderColor  = DEFAULT_BORDER_COLOR
    private var borderWidth  = DEFAULT_BORDER_WIDTH

    init {
        if(attrs != null){
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH)
            a.recycle()
        }
    }

    fun getBorderWidth():Int = borderWidth.toInt()
    fun setBorderWidth(dp:Int) {
        borderWidth = dp.toFloat()
    }
    fun getBorderColor():Int = borderColor
    fun setBorderColor(hex:String) {
        borderColor = Color.parseColor(hex)
    }
    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = colorId

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val halfWidth = this.width / 2
        val halfHeight = this.height / 2
        val radius = Math.max(halfWidth, halfHeight)
        val path = Path()
        path.addCircle(halfWidth.toFloat(), halfHeight.toFloat(), radius.toFloat(), Path.Direction.CCW)
        val rect = Rect(0, 0, this.width, this.height)
        val rectF = RectF(rect)
        val  paint =  Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.WHITE
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        canvas?.drawPath(path, paint)
        paint.xfermode = null
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth
        paint.color = borderColor
        canvas!!.drawCircle(halfWidth.toFloat(),halfHeight.toFloat(),radius.toFloat()-borderWidth/2,paint)


    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("M_CircleImageView", "onMeasure ")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}