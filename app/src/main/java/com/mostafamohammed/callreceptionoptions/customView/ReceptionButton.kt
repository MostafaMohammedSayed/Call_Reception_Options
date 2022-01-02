package com.mostafamohammed.callreceptionoptions.customView

import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.mostafamohammed.callreceptionoptions.R


class ReceptionButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeCap = Paint.Cap.ROUND
    }


    private val defaultButtonColor = ContextCompat.getColor(context, R.color.button)
    private val defaultFirstRingColor = ContextCompat.getColor(context, R.color.firstRing)
    private val defaultSecondRingColor = ContextCompat.getColor(context, R.color.secondRing)
    private val defaultThirdRingColor = ContextCompat.getColor(context, R.color.thirdRing)
    private val defaultFourthRingColor = ContextCompat.getColor(context, R.color.fourthRing)
    private val defaultFifthRingColor = ContextCompat.getColor(context, R.color.white)

    private var buttonColor = defaultButtonColor
    private var firstRingColor = defaultFirstRingColor
    private var secondRingColor = defaultSecondRingColor
    private var thirdRingColor = defaultThirdRingColor
    private var fourthRingColor = defaultFourthRingColor
    private var fifthRingColor = defaultFifthRingColor

    private val ringingButtonDrawableId = R.drawable.ic_baseline_local_phone_24
    private var buttonDrawableId = ringingButtonDrawableId

    private val ringingButtonDrawable =
        ContextCompat.getDrawable(context, ringingButtonDrawableId)
    private val ringingButtonDrawableHalfWidth = ringingButtonDrawable!!.intrinsicWidth * 0.5f
    private val ringingButtonDrawableHalfHeight = ringingButtonDrawable!!.intrinsicHeight * 0.5f
    private val ringingButtonDrawableBitmap = getBitmapFromVectorDrawable(ringingButtonDrawable!!)

    private val answerButtonDrawableId = R.drawable.ic_baseline_phone_in_talk_24
    private val answerButtonDrawable =
        ContextCompat.getDrawable(context, answerButtonDrawableId)
    private val answerButtonDrawableHalfWidth = answerButtonDrawable!!.intrinsicWidth * 0.5f
    private val answerButtonDrawableHalfHeight = answerButtonDrawable!!.intrinsicHeight * 0.5f
    private val answerButtonDrawableBitmap = getBitmapFromVectorDrawable(answerButtonDrawable!!)

    private val declineButtonDrawableId = R.drawable.ic_baseline_phone_missed_24
    private val declineButtonDrawable =
        ContextCompat.getDrawable(context, declineButtonDrawableId)
    private val declineButtonDrawableHalfWidth = declineButtonDrawable!!.intrinsicWidth * 0.5f
    private val declineButtonDrawableHalfHeight = declineButtonDrawable!!.intrinsicHeight * 0.5f
    private val declineButtonDrawableBitmap = getBitmapFromVectorDrawable(declineButtonDrawable!!)

    private val messageButtonDrawableId = R.drawable.ic_baseline_message_24
    private val messageButtonDrawable =
        ContextCompat.getDrawable(context, messageButtonDrawableId)
    private val messageButtonDrawableHalfWidth = messageButtonDrawable!!.intrinsicWidth * 0.5f
    private val messageButtonDrawableHalfHeight = messageButtonDrawable!!.intrinsicHeight * 0.5f
    private val messageButtonDrawableBitmap = getBitmapFromVectorDrawable(messageButtonDrawable!!)

    private val silentButtonDrawableId = R.drawable.ic_baseline_volume_mute_24
    private val silentButtonDrawable =
        ContextCompat.getDrawable(context, silentButtonDrawableId)
    private val silentButtonDrawableHalfWidth = silentButtonDrawable!!.intrinsicWidth * 0.5f
    private val silentButtonDrawableHalfHeight = silentButtonDrawable!!.intrinsicHeight * 0.5f
    private val silentButtonDrawableBitmap = getBitmapFromVectorDrawable(silentButtonDrawable!!)

    private val defaultRingWidth = 20.0f
    private var ringWidth = defaultRingWidth

    private val defaultViewSize = 400
    private var viewSize = defaultViewSize
    private val defaultViewSizeDivisor = 3
    private var viewSizeDivisor = defaultViewSizeDivisor
    private val buttonRadius = viewSize / 2f
    private val centerOfButtonCircle = viewSize / 2f

    private val defaultXPositionFraction = 0.5f
    private var xPositionFraction = defaultXPositionFraction
    private val defaultYPositionFraction = 0.75f
    private var yPositionFraction = defaultYPositionFraction

    private var currentRadiusLength = 0

    private val defaultAnimationDuration = 2000
    private val animationDuration = defaultAnimationDuration

    private val valuesHolder = PropertyValuesHolder.ofFloat("length", 0f, buttonRadius)
    private val animator = ValueAnimator().apply {
        setValues(valuesHolder)
        duration = animationDuration.toLong()
        repeatCount = ValueAnimator.INFINITE
        addUpdateListener { valueAnimator ->
            val percentage = valueAnimator.getAnimatedValue(LENGTH_VALUE_HOLDER) as Float
            currentRadiusLength = percentage.toInt()
            invalidate()
        }
    }

    private val ringingButtonState = 0L
    private val answerButtonState = 1L
    private val declineButtonState = 2L
    private val makeSilentButtonState = 3L
    private val messageButtonState = 4L
    private var buttonState = ringingButtonState

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawButtonBackground(canvas)
        drawButtonRings(canvas)
        canvas.save()
        setButtonPosition()
        drawButtonDrawable(canvas)
        canvas.restore()
    }

    private fun drawButtonBackground(canvas: Canvas) {
        paint.color = buttonColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(
            centerOfButtonCircle,
            centerOfButtonCircle,
            buttonRadius / 2,
            paint
        )
    }

    private fun drawButtonRings(canvas: Canvas) {
        paint.color = fifthRingColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = ringWidth

        canvas.drawCircle(
            centerOfButtonCircle,
            centerOfButtonCircle,
            currentRadiusLength - ringWidth,
            paint
        )

        paint.color = fourthRingColor
        canvas.drawCircle(
            centerOfButtonCircle,
            centerOfButtonCircle,
            currentRadiusLength - (2 * ringWidth),
            paint
        )

        paint.color = thirdRingColor
        canvas.drawCircle(
            centerOfButtonCircle,
            centerOfButtonCircle,
            currentRadiusLength - (3 * ringWidth),
            paint
        )

        paint.color = secondRingColor
        canvas.drawCircle(
            centerOfButtonCircle,
            centerOfButtonCircle,
            currentRadiusLength - (4 * ringWidth),
            paint
        )

        paint.color = firstRingColor
        canvas.drawCircle(
            centerOfButtonCircle,
            centerOfButtonCircle,
            currentRadiusLength - (5 * ringWidth),
            paint
        )

    }

    private fun setButtonPosition() {
        translationX = (measuredWidth * xPositionFraction) - buttonRadius
        translationY = (measuredHeight * yPositionFraction) - buttonRadius
    }

    private fun drawButtonDrawable(canvas: Canvas) {
        if (buttonState == ringingButtonState) {
            canvas.drawBitmap(
                ringingButtonDrawableBitmap,
                centerOfButtonCircle - ringingButtonDrawableHalfWidth,
                centerOfButtonCircle - ringingButtonDrawableHalfHeight,
                paint
            )
        } else if (buttonState == answerButtonState) {
            canvas.drawBitmap(
                answerButtonDrawableBitmap,
                centerOfButtonCircle - answerButtonDrawableHalfWidth,
                centerOfButtonCircle - answerButtonDrawableHalfHeight,
                paint
            )
        } else if (buttonState == declineButtonState) {
            canvas.drawBitmap(
                declineButtonDrawableBitmap,
                centerOfButtonCircle - declineButtonDrawableHalfWidth,
                centerOfButtonCircle - declineButtonDrawableHalfHeight,
                paint
            )
        }else if(buttonState == messageButtonState){
            canvas.drawBitmap(
                messageButtonDrawableBitmap,
                centerOfButtonCircle - messageButtonDrawableHalfWidth,
                centerOfButtonCircle - messageButtonDrawableHalfHeight,
                paint
            )
        }else if(buttonState == makeSilentButtonState){
            canvas.drawBitmap(
                silentButtonDrawableBitmap,
                centerOfButtonCircle - silentButtonDrawableHalfWidth,
                centerOfButtonCircle - silentButtonDrawableHalfHeight,
                paint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        viewSize = (measuredWidth / viewSizeDivisor).coerceAtMost(measuredHeight / viewSizeDivisor)

        setMeasuredDimension(viewSize, viewSize)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    private fun getBitmapFromVectorDrawable(drawable: Drawable): Bitmap {

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun animateProgress() {
        animator.start()
    }

    fun stopAnimation() {
        animator.cancel()
    }

    init {
        setupAttributes(attrs)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.ReceptionButton,
            0, 0
        )

        buttonState =
            typedArray.getInt(R.styleable.ReceptionButton_state, ringingButtonState.toInt())
                .toLong()
        buttonColor =
            typedArray.getColor(R.styleable.ReceptionButton_buttonColor, defaultButtonColor)
        firstRingColor =
            typedArray.getColor(R.styleable.ReceptionButton_firstRingColor, defaultFirstRingColor)
        secondRingColor =
            typedArray.getColor(R.styleable.ReceptionButton_secondRingColor, defaultSecondRingColor)
        thirdRingColor =
            typedArray.getColor(R.styleable.ReceptionButton_thirdRingColor, defaultThirdRingColor)
        fourthRingColor =
            typedArray.getColor(R.styleable.ReceptionButton_fourthRingColor, defaultFourthRingColor)
        fifthRingColor =
            typedArray.getColor(R.styleable.ReceptionButton_fifthRingColor, defaultFifthRingColor)
        ringWidth = typedArray.getDimension(R.styleable.ReceptionButton_ringWidth, defaultRingWidth)
        viewSizeDivisor =
            typedArray.getInt(R.styleable.ReceptionButton_viewSizeFraction, defaultViewSizeDivisor)
        xPositionFraction = typedArray.getFloat(
            R.styleable.ReceptionButton_xPositionFraction,
            defaultXPositionFraction
        )
        yPositionFraction = typedArray.getFloat(
            R.styleable.ReceptionButton_yPositionFraction,
            defaultYPositionFraction
        )

        typedArray.recycle()
    }

    companion object {
        const val LENGTH_VALUE_HOLDER = "length"
    }
}