package com.example.tracktor.common.composable

import android.graphics.Typeface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shape.roundedCornerShape
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.composed.plus
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entryOf
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChartComposable(dataMap: Map<String, Long>) {

    val xValuesToDates = dataMap.keys.associateBy { LocalDate.parse(it).toEpochDay().toFloat() }
    val chartEntryModel =
        xValuesToDates.keys.zip(dataMap.values) { x, y -> entryOf(x, y) }.let { entryModelOf(it) }
    val horizontalAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
            val date = LocalDate.ofEpochDay(value.toLong())
            date.format(DateTimeFormatter.ofPattern("dd MMM"))
        }

    val chartEntryModelProducer = chartEntryModel + chartEntryModel

    val defaultColumns = currentChartStyle.columnChart.columns
    val defaultLines = currentChartStyle.lineChart.lines
    val pointConnector = DefaultPointConnector(cubicStrength = 0f)

    val lineChart = lineChart(
        remember(defaultLines) {
            defaultLines.map { defaultLine ->
                defaultLine.copy(
                    lineBackgroundShader = null,
                    pointConnector = pointConnector,
                    lineColor = Color.Green.hashCode()
                )
            }
        },
    )

    val columnChart = columnChart(
        remember(defaultColumns) {
            defaultColumns.map { defaultColumn ->
                LineComponent(
                    Color.LightGray.hashCode(),
                    defaultColumn.thicknessDp,
                    Shapes.roundedCornerShape(2.dp),
                )
            }
        },
    )

    // Pass the updated chartEntryModel to the Chart component
    Chart(
        modifier = Modifier.padding(8.dp),
        chart = (remember(columnChart, lineChart) { columnChart + lineChart }),
        model = chartEntryModelProducer,
        startAxis = startAxis(
            maxLabelCount = 5,
            title = "Item Picked",
            titleComponent = textComponent(
                background = shapeComponent(Shapes.pillShape, Color(0xff9db591)),
                color = Color.White,
                padding = dimensionsOf(8.dp, 2.dp),
                margins = dimensionsOf(top = 4.dp),
                typeface = Typeface.MONOSPACE,
            ),
        ),
        bottomAxis = bottomAxis(
            valueFormatter = horizontalAxisValueFormatter,
            titleComponent = textComponent(
                background = shapeComponent(Shapes.pillShape, Color(0xff9db591)),
                color = Color.White,
                padding = dimensionsOf(8.dp, 2.dp),
                margins = dimensionsOf(top = 4.dp),
                typeface = Typeface.MONOSPACE,
            ),
            title = "Date"
        ),
    )
}
