package com.example.scichartsexample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.scichart.charting.ClipMode
import com.scichart.charting.modifiers.AxisDragModifierBase
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.charting.visuals.annotations.HorizontalAnchorPoint
import com.scichart.charting.visuals.annotations.VerticalAnchorPoint
import com.scichart.drawing.common.PenStyle
import com.scichart.drawing.common.SolidPenStyle
import com.scichart.drawing.utility.ColorUtil
import com.scichart.extensions.builders.SciChartBuilder
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // run time configuration of scicharts with
        try {
            SciChartSurface.setRuntimeLicenseKey(
                    getString(R.string.sci_charts_key))
            Log.i("Sci Chart Success", " Success")
            Toast.makeText(this, "Configuration Successful", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Log.e("SciChart", "Error when setting the license", e)
        }



        val surface = SciChartSurface(this)
        val chartlayOut = findViewById<View>(R.id.chart_layout) as LinearLayout
        chartlayOut.addView(surface)

        SciChartBuilder.init(this)
        val sciChartBuilder = SciChartBuilder.instance()
        // CREATING X AXIS
        val xAxis = sciChartBuilder.newNumericAxis().withAxisTitle("X Axis Title").withVisibleRange(
            (-5).toDouble(),
            (15).toDouble()
        ).build()
        // Create a numeric Y axis
        val yAxis = sciChartBuilder.newNumericAxis().withAxisTitle("Y Axis Title").withVisibleRange(
            0.toDouble(),
            100.toDouble()
        ).build()

        //CREATING TEXT ANNOTATION
        var textAnnotation = sciChartBuilder.newTextAnnotation()
                .withX1(5.0)
                .withY1(55.0)
                .withText("Hello Charts")
                .withHorizontalAnchorPoint(HorizontalAnchorPoint.Center)
                .withVerticalAnchorPoint(VerticalAnchorPoint.Center)
                .withFontStyle(20.toFloat(), ColorUtil.Green)
                .build()

        // Create interactivity modifiers
        var chartModifiers = sciChartBuilder.newModifierGroup()
            .withPinchZoomModifier().build()
            .withZoomPanModifier().withReceiveHandledEvents(true).build()
            .withZoomExtentsModifier().withReceiveHandledEvents(true).build()
            .withXAxisDragModifier().withReceiveHandledEvents(true).withDragMode(
                AxisDragModifierBase.AxisDragMode.Scale).withClipModeX(ClipMode.StretchAtExtents).build()
            .withYAxisDragModifier().withReceiveHandledEvents(true).withDragMode(AxisDragModifierBase.AxisDragMode.Pan).build()
            .build()

        //CREATING LINE PLOT

        //val = sciChartBuilder.newXyDataSeries(Int::class.javaObjectType, Double::class.java).build()
        val lineData= sciChartBuilder.newXyDataSeries(
            Int::class.javaObjectType,
            Double::class.javaObjectType
        ).build()
        val scatterData = sciChartBuilder.newXyDataSeries(
            Int::class.javaObjectType,
            Double::class.javaObjectType
        ).build()
        for (i in 0..999)
        {

            lineData.append(i, Math.sin(i * 0.1))
            scatterData.append(i, Math.cos(i * 0.1))
        }

        val penStyle: PenStyle = SolidPenStyle(ColorUtil.Green, true, 5f, null)

        val lineSeries = sciChartBuilder.newLineSeries()
                .withDataSeries(lineData)
                .withStrokeStyle(ColorUtil.LightBlue, 2f, true)
                .build()

        val scatterSeries = sciChartBuilder.newScatterSeries()
                .withDataSeries(scatterData)
                //withPointMarker()
                .build()








        lineSeries.strokeStyle = penStyle
        surface.renderableSeries.add(lineSeries)
        surface.renderableSeries.add(scatterSeries)




        Collections.addAll(surface.getYAxes(), yAxis)
        Collections.addAll(surface.getXAxes(), xAxis)
       // Collections.addAll(surface.getAnnotations(), textAnnotation)
        Collections.addAll(surface.getChartModifiers(), chartModifiers)
        Collections.addAll(surface.getChartModifiers(), chartModifiers)


    }
}