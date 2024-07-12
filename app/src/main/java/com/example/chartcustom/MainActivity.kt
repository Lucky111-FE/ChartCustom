package com.example.chartcustom

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

private const val TAG = "MainActivity_싸피"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lineChart = findViewById<LineChart>(R.id.lineChart)

        // 고도 데이터를 설정합니다. (예시 데이터)
        val elevationData = listOf(
            100.0f, 200.0f, 150.0f, 300.0f, 250.0f, 400.0f, 350.0f
        )

        // 색상을 나누어 줄 범위 설정
        val segmentRanges = listOf(
            Pair(0, 2), // 첫 번째 구간 (0-2 인덱스)
            Pair(2, 4), // 두 번째 구간 (2-4 인덱스)
            Pair(4, 6)  // 세 번째 구간 (4-6 인덱스)
        )

        // 각 구간에 대해 LineDataSet 생성
        val dataSets = segmentRanges.mapIndexed { index, range ->
            // slice는 리스트의 indices(특정 범위)의 값을 잘라서 새로운 리스트를 추출하는 것 ex : elevationData.slice(0..2)는 elevationData의 0에서 2까지의 data를 추출해서 새로운 리스트를 반환하는 것
            // mapIndexed는 리스트의 각 element와 index를 받아서 새로운 요소로 변환된 것들의 리스트를 반환하는 람다 함수
            val entries = elevationData.slice(range.first..range.second).mapIndexed { subIndex, elevation ->
                Log.d(TAG, "onCreate: subIndex : $subIndex elevation : $elevation range.first : ${range.first} range.second : ${range.second}")
                Entry((range.first + subIndex).toFloat(), elevation)
            }
            val dataSet = LineDataSet(entries, "Segment $index")
            dataSet.color = Color.BLUE
            dataSet.valueTextColor = Color.BLACK
            dataSet.setDrawFilled(true)
            when (index) {
                0 -> dataSet.fillColor = Color.CYAN
                1 -> dataSet.fillColor = Color.MAGENTA
                2 -> dataSet.fillColor = Color.YELLOW
            }
            dataSet.fillAlpha = 100
            dataSet
        }

        // LineData를 생성하고 LineChart에 설정합니다.
        val lineData = LineData(dataSets)
        lineChart.data = lineData

        // Description을 비웁니다.
        val description = Description()
        description.text = ""
        lineChart.description = description

        // 차트를 갱신합니다.
        lineChart.invalidate()
    }
}