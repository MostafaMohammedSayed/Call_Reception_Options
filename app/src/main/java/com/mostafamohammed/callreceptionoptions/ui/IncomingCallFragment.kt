package com.mostafamohammed.callreceptionoptions.ui

import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mostafamohammed.callreceptionoptions.R
import kotlinx.android.synthetic.main.fragment_incoming_call.*


class IncomingCallFragment : Fragment() {
    private lateinit var dragListener: View.OnDragListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpDragListener()
        return inflater.inflate(R.layout.fragment_incoming_call, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewsOnDragListener()
        setUpCenterButtonView()
    }

    private fun setUpCenterButtonView() {
        centerButton.startRingAnimation()
       centerButton.setOnTouchListener { view, _ ->
            centerButton.apply {
                stopRingAnimation()
                startDrag()
            }
            view.performClick()
        }
    }

    private fun startDrag(): Boolean {
        centerButton?.let {centerButton->
            val tag = centerButton.tag as? CharSequence
            val item = ClipData.Item(tag)
            val data = ClipData(tag, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
            val shadow = View.DragShadowBuilder(centerButton)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                centerButton.startDragAndDrop(data, shadow, centerButton, 0)
            }else{
                @Suppress("DEPRECATION")
                centerButton.startDrag(data, shadow, centerButton, 0)
            }
            return true
        } ?: return false
    }

    private fun setUpDragListener() {
        dragListener = View.OnDragListener { view, dragEvent ->
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    centerButton.visibility = View.INVISIBLE
                    messageButton.fadeView()
                    muteButton.fadeView()
                    answerButton.fadeView()
                    rejectButton.fadeView()
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    view.alpha = 1f
                    view.invalidate()
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    centerButton.startRingAnimation()
                    view.alpha = 0.4f
                    view.invalidate()
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    centerButton.visibility = View.VISIBLE
                    centerButton.startRingAnimation()
                    view.alpha = 1f
                    view.invalidate()
                }
                DragEvent.ACTION_DROP -> {
                    view.alpha = 1f
                    view.invalidate()
                    view.performClick()
                }
            }
            return@OnDragListener true
        }
    }

    private fun setViewsOnDragListener(){
        answerButton.setOnDragListener(dragListener)
        rejectButton.setOnDragListener(dragListener)
        messageButton.setOnDragListener(dragListener)
        muteButton.setOnDragListener(dragListener)
    }
}