package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import ru.skillbranch.devintensive.R
import android.util.Log
import android.graphics.*
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.BitmapShader
import android.graphics.RectF
import android.R.attr.bitmap




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
    private var mBitmap:Bitmap? = null
    private  var mShaderMatrix:Matrix = Matrix()
    private  var mBitmapWidth:Float = 0.0f
    private  var mBitmapHeight:Float = 0.0f
    private lateinit var mDrawableRect :RectF
    private lateinit var mBitmapShader:BitmapShader
    private var mBitmapPaint:Paint = Paint()

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

    private fun setup(){
        mDrawableRect = calculateBounds()
        mBitmapShader = BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        mBitmapPaint = Paint()
        mBitmapPaint.setShader(mBitmapShader)
        mBitmapHeight = mBitmap?.height?.toFloat() ?: 0F
        mBitmapWidth = mBitmap?.width?.toFloat() ?: 0F
        updateShaderMatrix()
    }

    override fun onDraw(canvas: Canvas?) {
        //super.onDraw(canvas)
        Log.d("M_CircleImageView", "onDraw width="+ this.width + " height="+this.height )

        draweRoundBmp(canvas)
    }

    private fun draweRoundBmp(canvas: Canvas?){
        initializeBitmap()
        if(mBitmap != null) {
            val halfWidth = this.width / 2
            val halfHeight = this.height / 2
            val radius = Math.max(halfWidth, halfHeight)
            val rect = Rect(0, 0, this.width, this.height)
            canvas?.drawCircle(rect.centerX().toFloat(), rect.centerY().toFloat(), radius.toFloat(), mBitmapPaint)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderWidth
            paint.color = borderColor
            canvas?.drawCircle(halfWidth.toFloat(), halfHeight.toFloat(), radius.toFloat() - borderWidth / 2, paint)
        }else{
            val halfWidth = this.width / 2
            val halfHeight = this.height / 2

            var text = "JD"
            var fontPaint = Paint()
            fontPaint.color = Color.WHITE
            fontPaint.textSize  = 100F
            fontPaint.style = Paint.Style.STROKE
            var width_txt = fontPaint.measureText(text)
            canvas?.drawARGB(80, 102, 204, 255);
            canvas?.drawText(text, halfWidth.toFloat()-width_txt/2, halfHeight.toFloat(), fontPaint)


            val radius = Math.max(halfWidth, halfHeight)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = borderWidth
            paint.color = borderColor
            canvas?.drawCircle(halfWidth.toFloat(), halfHeight.toFloat(), radius.toFloat() - borderWidth / 2, paint)
        }
    }

    private fun updateShaderMatrix(){
        mShaderMatrix.set(null)
        var scale = 0.5F
        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
             scale = mDrawableRect.height() / mBitmapHeight
        } else {
             scale = mDrawableRect.width() / mBitmapWidth
        }

        mShaderMatrix.setScale(scale, scale)
        mBitmapShader.setLocalMatrix(mShaderMatrix)
    }

    private fun initializeBitmap() {
        mBitmap = getBitmapFromDrawable(getDrawable())
        setup()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        try {
            val bitmap: Bitmap

            if (drawable is ColorDrawable) {
                bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888)
            } else {
                bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap)
            drawable?.setBounds(0, 0, canvas.width, canvas.height)
            drawable?.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    private fun calculateBounds(): RectF {
        val availableWidth = this.width - this.paddingLeft - this.paddingRight
        val availableHeight = this.height - this.paddingTop - this.paddingBottom

        val sideLength = Math.min(availableWidth, availableHeight)

        val left = this.paddingLeft + (availableWidth - sideLength) / 2f
        val top = this.paddingTop + (availableHeight - sideLength) / 2f

        return RectF(left, top, left + sideLength, top + sideLength)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d("M_CircleImageView", "onMeasure width="+measuredWidth+" height="+measuredHeight)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }
}