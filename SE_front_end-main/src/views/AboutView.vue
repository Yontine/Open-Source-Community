<template>
  <el-tabs type="border-card">
    <h1>语言预测变化</h1>
    <div ref="TimeLanguageLineContainer" style="width: 100%; height: 600px;"></div>
  </el-tabs>
</template>

<script setup lang="ts">
import request from "@/utils/request";
import { ref, onMounted } from "vue";
import * as echarts from 'echarts';

const TimeLanguageData = ref<any[]>([]);
const PredictData = ref<any[]>([]);
const TimeLanguageLineContainer = ref(null)
const getTimeLanguageData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/language_time",
    });
    TimeLanguageData.value = response.data.data || [];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};
const getPredictData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/language_predict",
    });
    PredictData.value = response.data.data || [];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};


const renderTimeLanguageLineChart = () => {

  const splitIndex = Math.floor(TimeLanguageData.value.length);
  const TimeLanguageChart = echarts.init(TimeLanguageLineContainer.value);
  TimeLanguageData.value.push(...PredictData.value);
  const dates = TimeLanguageData.value.map(item => item.time.split(' ')[0]);
  const languages = Object.keys(TimeLanguageData.value[0]).filter(key => key !== 'time');

  // 为每种语言创建两个系列，一个实线一个虚线
  const series = languages.flatMap(language => {
    const fullData = TimeLanguageData.value.map(item => item[language]);
    const solidData = fullData.slice(0, splitIndex); // 实线部分数据
    const dottedData = fullData.slice(splitIndex - 1); // 虚线部分数据

    return [{
      name: language,
      type: 'line',
      data: solidData,
      smooth: true,
      symbol: 'circle',
      symbolSize: 6,
      lineStyle: {
        width: 2,
        type: 'solid',
      },
    }, {
      name: language + '-predicted',
      type: 'line',
      data: Array(splitIndex - 1).fill(null).concat(dottedData), // 前半部分用 null 填充，以维持x轴对齐
      smooth: true,
      symbol: 'circle',
      symbolSize: 6,
      lineStyle: {
        width: 2,
        type: 'dotted',
      },
    }];
  });

  const option = {
    animationDuration: 10000,
    tooltip: {
      trigger: 'axis', // 或者可以是'item'，根据需要选择
      formatter: function (params: any) {
        // 定制你想显示的内容
        let result = params[0].name + '<br/>'; // 显示横坐标值
        params.forEach(function (item: any) {
          if (!(item.seriesName.includes('predicted') && item.dataIndex < splitIndex)) {
            result += item.marker + " " + item.seriesName + ": " + item.value + '<br/>';
          }
        });
        return result;
      }
    },
    dataZoom: {
      type: 'inside'
    },

    toolbox: {  // Add the toolbox property here
      feature: {
        saveAsImage: {},
        dataZoom: {},
        restore: {},
      },
    },
    xAxis: {
      type: 'category',
      data: dates,
      name: '时间',
      axisLabel: {
        rotate: 45, // 设置横轴刻度标签旋转角度
        interval: Math.ceil(dates.length / 10), // 设置横轴刻度标签显示间隔
      },
    },
    yAxis: {
      type: 'value'
    },
    series: series
  };

  TimeLanguageChart.setOption(option);
}


onMounted(async () => {
  // 假设getTimeLanguageData和getPredictData是异步操作，并返回Promise
  await getTimeLanguageData(); // 等待getTimeLanguageData完成
  await getPredictData(); // 等待getPredictData完成
  // 在两个数据获取函数都完成后渲染图表
  renderTimeLanguageLineChart();
});


</script>

<style>
.chart-container {
  width: 600px;
  height: 700px;
  margin: 20px auto;
}
</style>
