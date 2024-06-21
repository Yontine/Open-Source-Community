<template>
  <el-tabs type="border-card">
    <el-tab-pane label="语言时间">
      <div v-if="TimeLanguageData.length === 0" class="loading-message">暂无数据</div>
      <div id="language-time-bar-chart" style="width: 100%; height: 600px;"></div>
      <div ref="TimeLanguageLineContainer" style="width: 100%; height: 600px;"></div>
    </el-tab-pane>

    <el-tab-pane label="语言项目">
      <div id="bar-chart" style="width: 1400px; height: 700px;"></div>
    </el-tab-pane>

    <el-tab-pane label="区域用户">
      <div>
        <h1>区域用户分析图</h1>
        <div id="pie-chart" style="width: 1200px; height: 600px;"></div>
      </div>
    </el-tab-pane>

    <el-tab-pane label="区域粉丝">
      <div ref="CountryFanChartContainer" style="width: 1200px; height: 700px;"></div>
    </el-tab-pane>

    <el-tab-pane label="时间段操作与提交">
      <div ref="TimeOperateCommitChartContainer" style="width: 1350px; height: 600px;"></div>
    </el-tab-pane>

    <el-tab-pane label="语言收藏">
      <h1>语言收藏量分析图</h1>
      <div id="star-language-chart" style="width: 1350px; height: 600px;"></div>
      <p :style="{ fontSize: '20px', color: '#333', fontWeight: 'bold' }">总收藏数: {{ totalStarredNumRef }}</p>
    </el-tab-pane>

  </el-tabs>
</template>

<script setup lang="ts">
import request from "@/utils/request";
import { ref, onMounted, watch } from "vue";
import * as echarts from 'echarts';

const pieChartInstance = ref<echarts.ECharts | null>(null);
const barChartInstance = ref<echarts.ECharts | null>(null);

const responseData2 = ref<any[]>([]);
const responseData3 = ref<any[]>([]);
const totalStarredNumRef = ref<number>(0);


//时间语言相关开始
const languageTimeBarChartInstance = ref<echarts.ECharts | null>(null);
const TimeLanguageData = ref<any[]>([]);
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

const renderLanguageTimeBarChart = () => {
  if (TimeLanguageData.value.length === 0) return;
  const combinedData = Object.entries(TimeLanguageData.value[0])
    .filter(([key]) => key !== 'time')
    .map(([language, value]) => ({ language, value: Number(value) }));

  // 按照数量从小到大排序
  combinedData.sort((a, b) => b.value - a.value);

  // 分离语言和数量到各自的数组
  const languages = combinedData.map(item => item.language);
  const data = combinedData.map(item => item.value);

  const option = {
    title: {
      text: `语言使用情况 (${TimeLanguageData.value[0].time.split(' ')[0]})`,
      left: 'center'
    },
    tooltip: {
      trigger: 'axis', // 或者 'item' 根据需要选择
      axisPointer: {
        type: 'shadow' // 'shadow'为阴影指示器
      }
    },
    xAxis: {
      type: 'category',
      data: languages,
      axisLabel: {
        rotate: 30, // 旋转标签
        interval: 0, // 显示所有标签
      }
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: data,
      type: 'bar',
      barWidth: '80%',
      barGap: '10%', // 设置条形图之间的间隙
      label: {
        show: true, // 显示标签
        position: 'top', // 标签位置
        formatter: '{c}' // 标签格式，{c}代表数据值
      }
    }]
  };

  const chartDom = document.getElementById('language-time-bar-chart');
  if (chartDom) {
    languageTimeBarChartInstance.value = echarts.init(chartDom);
    languageTimeBarChartInstance.value.setOption(option);
  }
};

const sortAndUpdateChart = (index: number) => {
  // 获取当前数据点并进行排序
  const currentData = TimeLanguageData.value[index];
  const sortedData = Object.entries(currentData)
    .filter(([key]) => key !== 'time') // 排除'time'
    .map(([language, value]) => ({ language, value: Number(value) })) // 转换为对象数组
    .sort((a, b) => b.value - a.value); // 排序

  // 提取排序后的语言和数量
  const languages = sortedData.map(item => item.language);
  const values = sortedData.map(item => item.value);

  // 更新图表
  languageTimeBarChartInstance.value?.setOption({
    title: {
      text: `语言使用情况 (${TimeLanguageData.value[index].time.split(' ')[0]})`,
      left: 'center'
    },
    xAxis: {
      data: languages
    },
    series: [{
      data: values
    }]
  });
};

let currentIndex = 0;
const startAutoPlay = () => {
  setInterval(() => {
    currentIndex = (currentIndex + 1) % TimeLanguageData.value.length;
    sortAndUpdateChart(currentIndex);
  }, 3000); //每三秒变化
};

const renderTimeLanguageLineChart = () => {
  const dates = TimeLanguageData.value.map(item => item.time.split(' ')[0]);
  // 提取所有语言
  const languages = Object.keys(TimeLanguageData.value[0]).filter(key => key !== 'time');

  // 为每种语言创建一个系列
  const series = languages.map(language => {
    return {
      name: language,
      type: 'line',
      data: TimeLanguageData.value.map(item => item[language]),
      smooth: true
    };
  });

  const option = {
    tooltip: {
      trigger: 'axis'
    },
    dataZoom: {
      type: 'inside'
    },
    legend: {
      data: languages
    },
    xAxis: {
      type: 'category',
      data: dates
    },
    yAxis: {
      type: 'value'
    },
    series: series
  };

  // 初始化并设置选项
  const lineChart = echarts.init(TimeLanguageLineContainer.value);
  lineChart.setOption(option);

}

onMounted(() => {
  getTimeLanguageData();
  startAutoPlay();

});

watch(TimeLanguageData, () => {
  renderLanguageTimeBarChart();
  renderTimeLanguageLineChart();
});

//时间语言相关结束

//时间操作数和时间提交数开始
const TimeOperateCommitChartContainer = ref(null);
const TimeOperateCommitData = ref<any[]>([])
const getTimeOperateCommitData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/TimeOperateCommit",
    });
    TimeOperateCommitData.value = response.data.data || [];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const renderTimeOperateCommitChart = () => {
  if (TimeOperateCommitData.value.length === 0) return;
  const TimeOperateCommitChart = echarts.init(TimeOperateCommitChartContainer.value)
  const dates = TimeOperateCommitData.value.map(item => item.time.split(' ')[0])
  const operates = TimeOperateCommitData.value.map((item => item.operate))
  const commits = TimeOperateCommitData.value.map((item => item.commit))

  const option = {
    legend: {
      data: ["operates", "commits"]
    },
    tooltip: {
      trigger: 'axis', // 或者可以是'item'，根据需要选择
      formatter: function (params: any) {
        // 定制你想显示的内容
        let result = params[0].name + '<br/>'; // 显示横坐标值
        params.forEach(function (item: any) {
          result += item.marker + " " + item.seriesName + ": " + item.value + '<br/>';
        });
        return result;
      }
    },
    xAxis: {
      type: 'category',
      data: dates
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      type: 'line',
      data: operates,
      smooth: true,
      name: 'operates'
    },
    {
      type: 'line',
      data: commits,
      smooth: true,
      name: 'commits',
      // 设置线条样式
      lineStyle: {
        color: '#ff0000',
        width: 2,
      },
      // 设置拐点样式
      itemStyle: {
        color: '#ff0000',
      }
    }


    ]
  }
  TimeOperateCommitChart.setOption(option)

};

onMounted(() => {
  getTimeOperateCommitData()
}
)

watch(TimeOperateCommitData, (newVal) => {
  if (newVal && newVal.length > 0)
    renderTimeOperateCommitChart();
}
)
//时间操作数结束


// 国家用户部分开始
const fetchData2 = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/country_user",
    });
    responseData2.value = response.data.data || [];
    // console.log(responseData2)
    renderPieChart();
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const renderPieChart = () => {
  const legendData = responseData2.value.map((data) => data.location);
  const seriesData = responseData2.value.map((data) => ({ name: data.location, value: data.users }));

  const option = {
    title: {
      left: 'center',
    },
    legend: {
      type: 'scroll',
      orient: 'vertical',
      right: 10,
      top: 20,
      bottom: 20,
      data: legendData,
    },
    series: [{
      name: '国家用户',
      type: 'pie',
      radius: '55%',
      center: ['60%', '70%'],
      data: seriesData,
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    }],
  };
  const chartDom = document.getElementById('pie-chart');
  if (chartDom) {
    pieChartInstance.value = echarts.init(chartDom);
    pieChartInstance.value.setOption(option);
  }
};
// 国家用户部分结束

// 语言项目部分开始
const fetchData3 = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/language_projectNum",
    });
    responseData3.value = response.data.data || [];
    renderBarChart();
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const renderBarChart = () => {
  const xAxisData = responseData3.value.map((data) => data.language);
  const seriesData = responseData3.value.map((data) => ({ name: data.language, value: data.project_num }));

  const option = {
    title: {
      text: '语言项目条形图',
      left: 'center',
    },
    xAxis: {
      data: xAxisData,
      axisLabel: {
        rotate: 45, // 旋转标签
        interval: 0, // 显示所有标签
      },
      axisTick: {
        show: false
      },
      axisLine: {
        show: false
      },
      z: 10
    },
    yAxis: {
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        color: '#999'
      }
    },
    dataZoom: [
      {
        type: 'inside'
      }
    ],
    series: [
      {
        type: 'bar',
        showBackground: true,
        barWidth: '80%',
        barGap: '10%', // 设置条形图之间的间隙
        label: {
          show: true, // 显示标签
          position: 'top', // 标签位置
          formatter: '{c}' // 标签格式，{c}代表数据值
        },
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#83bff6' },
            { offset: 0.5, color: '#188df0' },
            { offset: 1, color: '#188df0' }
          ])
        },
        emphasis: {
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              { offset: 0, color: '#2378f7' },
              { offset: 0.7, color: '#2378f7' },
              { offset: 1, color: '#83bff6' }
            ])
          }
        },
        data: seriesData
      }
    ]
  };

  barChartInstance.value = echarts.init(document.getElementById('bar-chart'))!;
  barChartInstance.value.setOption(option);
};

// 语言项目部分结束
onMounted(fetchData2);
onMounted(fetchData3);


// 语言收藏量部分开始
const languageStarChart = ref<echarts.ECharts | null>(null);
const StarLanguageData = ref<any[]>([]);
const getStarLanguageData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/language_star",
      // url: "http://127.0.0.1:4523/m1/3807087-0-default/language_star",
    });
    StarLanguageData.value = response.data.data || [];
    renderlanguageStarChart();
    // console.log(StarLanguageData.value);
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};
const renderlanguageStarChart = () => {
  const legendData = StarLanguageData.value.map((data) => data.language);
  const seriesData = StarLanguageData.value.map((data) => ({ name: data.language, value: data.star, rank: data.rank }));
  // 计算 seriesData 中 starred_num 的总和
  const totalStarredNum = seriesData.reduce((accumulator, currentValue) => accumulator + currentValue.value, 0);
  // 存储在 ref 中
  totalStarredNumRef.value = totalStarredNum;
  // console.log('Total Starred Num:', totalStarredNumRef.value);

  const option = {
    legend: {
      type: 'scroll',
      orient: 'vertical',
      right: 100,
      top: 20,
      bottom: 20,
      data: legendData,
    },
    series: [
      {
        name: '编程语言',
        type: 'pie',
        selectedMode: 'single',
        radius: [0, '30%'],
        label: {
          position: 'inner',
          fontSize: 14,
        },
        labelLine: {
          show: false,
        },
        data: seriesData,
      },
      {
        name: '编程语言',
        type: 'pie',
        radius: ['45%', '60%'],
        labelLine: {
          length: 50,
        },
        label: {
          formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}:}{c}  {per|{d}%}  ',
          backgroundColor: '#F6F8FC',
          borderColor: '#8C8D8E',
          borderWidth: 1,
          borderRadius: 4,
          rich: {
            a: {
              color: '#6E7079',
              lineHeight: 22,
              align: 'center',
            },
            hr: {
              borderColor: '#8C8D8E',
              width: '100%',
              borderWidth: 1,
              height: 0,
            },
            b: {
              color: '#4C5058',
              fontSize: 14,
              fontWeight: 'bold',
              lineHeight: 33,
            },
            per: {
              color: '#fff',
              backgroundColor: '#4C5058',
              padding: [3, 4],
              borderRadius: 4,
            },
          },
        },
        data: seriesData,
      },
    ],
    toolbox: {  // Add the toolbox property here
      feature: {
        saveAsImage: {},
        dataZoom: {},
        restore: {},
      },
    },
  };


  languageStarChart.value = echarts.init(document.getElementById('star-language-chart'))!;
  languageStarChart.value.setOption(option);
};

onMounted(getStarLanguageData);
// 语言收藏量部分结束


//国家粉丝
const CountryFanChartContainer = ref(null);
const CountryFanData = ref<any[]>([])
const getCountryFanData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/country_followers",
    });
    CountryFanData.value = response.data.data || [];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};
const renderCountryFanChart = () => {
  // 1. 初始化图表
  const CountryFanChart = echarts.init(CountryFanChartContainer.value);
  const legendData = CountryFanData.value.map(item => item.location)
  // 3. 设置Option
  const option = {
    title: {
      text: '区域粉丝分析图',
      left: 'left'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      type: 'scroll',
      orient: 'vertical',
      right: 10,
      top: 20,
      bottom: 20,
      data: legendData,
    },
    xAxis: { show: false }, // 隐藏x轴
    yAxis: { show: false },  // 隐藏y轴
    series: [
      {
        name: 'Fans',
        type: 'pie',
        radius: '50%',
        data: CountryFanData.value.map(item => ({
          value: item.followers,
          name: item.location
        })),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };

  // 4. 渲染图表
  CountryFanChart.setOption(option);
}
const renderCountryFanBarChart = () => {
  // 初始化图表
  const CountryFanBarChart = echarts.init(CountryFanChartContainer.value);


  // 准备排序后的数据
  const sortedData = CountryFanData.value
    .slice()
    .sort((a, b) => a.followers - b.followers);

  // 设置Option
  const option = {
    title: {
      text: '区域粉丝条形图',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },

    xAxis: {
      type: 'value',
      boundaryGap: [0, 0.01],
      show: true
    },
    yAxis: {
      type: 'category',
      data: sortedData.map(item => item.location),
      show: true
    },
    series: [
      {
        name: 'Fans',
        type: 'bar',
        data: sortedData.map(item => item.followers)
      }
    ]
  };

  // 渲染图表
  CountryFanBarChart.setOption(option);
};
let currentChart = 'pie';
const toggleChart = () => {
  if (currentChart === 'pie') {
    renderCountryFanBarChart(); // 切换到条形图
    currentChart = 'bar';
  } else {
    renderCountryFanChart(); // 切换到饼图
    currentChart = 'pie';
  }
};
// 设置定时器，例如每5秒切换一次
setInterval(toggleChart, 5000);

onMounted(getCountryFanData);
watch(CountryFanData, () => {
  renderCountryFanChart();
})








</script>

<style>
.el-tabs__item {
  font-size: 18px;
  font-weight: bold;
}

.employee-info {
  margin-top: 20px;
}

.employee-info p {
  margin-bottom: 8px;
}

.employee-info hr {
  margin: 16px 0;
  border: none;
  border-top: 1px solid #ccc;
}

.loading-message {
  font-size: 18px;
  color: #888;
  margin-top: 20px;
}
</style>
