<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <el-card>
        <div ref="ProtocolPieChartContainer" style="width: 600px; height: 400px;"></div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card>
        <div ref="LanguageBarChartContainer" style="width: 600px; height: 400px;"></div>
      </el-card>
    </el-col>
  </el-row>
  <el-row style="height: 20px;"></el-row> <!-- 作为空隙 -->
  <el-row>
    <el-col>
      <el-card>
        <el-table :data="ProjectData" height="400" style="width: 100%">
          <el-table-column prop="rank" label="Rank" width="180%" />
          <el-table-column label="Project" width="600%">
            <template v-slot="scope">
              <div class="project-name">
                <el-link class="project-name" :href="scope.row.url" target="_blank">
                  {{ scope.row.name }}
                </el-link>
              </div>
              <div class="project-description">{{ scope.row.description }}</div>
            </template>
          </el-table-column>
          <el-table-column width="180%" />
          <el-table-column label="Language" width="180%">
            <template v-slot="scope">
              <div class="project-watch-and-star">

                {{ scope.row.language }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="Star" width="180%">
            <template v-slot="scope">
              <div class="project-watch-and-star">
                <span class="material-icons">star</span>{{ scope.row.star }}
              </div>
            </template>
          </el-table-column>

        </el-table>
      </el-card>
    </el-col>

  </el-row>
</template>

<script setup lang="ts">
import request from "@/utils/request";
import { onMounted, onUnmounted, ref, watch } from "vue";
import * as echarts from 'echarts';
import 'material-icons/iconfont/material-icons.css';


const ProtocolData = ref<any>([])
const LanguageData = ref<any>([])


const ProjectData = ref<any>([])

const ProtocolPieChartContainer = ref(null)
const LanguageBarChartContainer = ref(null)

const getProtocolData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/protocol",
    });
    ProtocolData.value = response.data.data || [];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const getLanguageData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/language",
    });
    LanguageData.value = response.data.data || [];

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const getProjectData = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/project",
    });
    ProjectData.value = response.data.data || [];
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};


let LanguageBarChart = null; // ECharts实例
let animationTimer = null; // 定时器
let currentIndex = 0; // 当前展示的数据起始索引

onMounted(() => {
  startAnimation(); // 启动动画
}
)


onMounted(async () => {
  await getProtocolData();
  await getLanguageData();
  await getProjectData();
  LanguageBarChart = echarts.init(LanguageBarChartContainer.value); // 初始化ECharts实例
  const sortedData = [...LanguageData.value].sort((a, b) => a.rank - b.rank);

  animationTimer = setInterval(() => {
    // 计算结束索引
    let endIndex = currentIndex + 5;
    let displayData;

    if (endIndex <= sortedData.length) {
      // 正常情况下取currentIndex到endIndex的数据
      displayData = sortedData.slice(currentIndex, endIndex);
    } else {
      // 结束索引超出范围时，循环追加数据
      displayData = sortedData.slice(currentIndex, sortedData.length);
      let remain = 5 - displayData.length;
      displayData = displayData.concat(sortedData.slice(0, remain));
    }

    renderDisplayChart(displayData);

    // 更新索引
    currentIndex = (currentIndex + 1) % sortedData.length; // 循环滑动
  }, 1000); // 定时器间隔
});

// 清理定时器
onUnmounted(() => {
  if (animationTimer) clearInterval(animationTimer);
});



const colors = ['#5470C6', '#91CC75', '#FAC858', '#EE6666', '#73C0DE', '#3BA272', '#FC8452', '#9A60B4', '#ea7ccc',
'#5470C6', '#91CC75', '#FAC858', '#EE6666', '#73C0DE', '#3BA272', '#FC8452', '#9A60B4', '#ea7ccc',
'#5470C6', '#91CC75'];

const LanguageDataTest = [
  {
    "language": "JAVA",
    "project_num": 68,
    "user_num": 27,
    "rank": 1
  },
  {
    "language": "C++",
    "project_num": 43,
    "user_num": 46,
    "rank": 10
  },
  {
    "language": "Python",
    "project_num": 28,
    "user_num": 2,
    "rank": 2
  },
  {
    "language": "C#",
    "project_num": 28,
    "user_num": 288,
    "rank": 4
  },
  {
    "language": "PHP",
    "project_num": 28,
    "user_num": 235,
    "rank": 3
  },
  {
    "language": "HTML",
    "project_num": 28,
    "user_num": 42,
    "rank": 12
  },
  {
    "language": "JavaScript",
    "project_num": 28,
    "user_num": 62,
    "rank": 7
  },
  {
    "language": "BB",
    "project_num": 28,
    "user_num": 72,
    "rank": 5
  }
]



  const languageColorMap = LanguageDataTest.reduce((acc, cur, index) => {
  acc[cur.language] = colors[index % colors.length]; // 循环使用颜色数组
  return acc;
}, {});


// 渲染图表的函数
function renderDisplayChart(data: { language: string; project_num: number; rank: number }[]) {
  const languages = data.map(item => item.language);
  const project_num = data.map(item => item.project_num);

  // ECharts配置
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    xAxis: {
      type: 'category',
      data: languages,
      axisLabel: {
        rotate: 45, // 旋转标签
        interval: 0, // 显示所有标签
      }
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: project_num.map((project_num, index) => ({
        value: project_num,
        itemStyle: {
          color: languageColorMap[data[index].language] // 使用映射为每个条形指定颜色
        }
      })),
      type: 'bar',
      barWidth: '60%', // 控制条形宽度
      label: {
        show: true, // 显示标签
        position: 'top', // 标签位置
        formatter: '{c}' // 标签格式，{c}代表数据值
      }
    }]
  };

  // 设置选项
  LanguageBarChart.setOption(option);
}


const renderProtocolPieChart = () => {
  if (ProtocolData.value.length === 0) return;
  const ProtocolPieChart = echarts.init(ProtocolPieChartContainer.value)
  const seriesData = ProtocolData.value.map(item => {
    return {
      name: item.protocol, // protocol字段作为名称
      value: item.project_num // project_num字段作为值

    };
  });
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
    },
    xAxis: { show: false }, // 隐藏x轴
    yAxis: { show: false },  // 隐藏y轴
    series: [
      {
        name: 'Protocol Usage',
        type: 'pie',
        radius: '55%',
        data: seriesData,
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

  // 设置选项
  ProtocolPieChart.setOption(option);

}

const renderProtocolBarChart = () => {
  if (ProtocolData.value.length === 0) return;
  const ProtocolBarChart = echarts.init(ProtocolPieChartContainer.value); // 确保这里的容器是正确的

  // 准备数据，根据project_num排序
  const sortedData = ProtocolData.value.sort((a, b) => a.project_num - b.project_num);

  // 提取协议名和项目数作为图表的x和y数据
  const protocolNames = sortedData.map(item => item.protocol);
  const projectNums = sortedData.map(item => item.project_num);

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow' // 'shadow'为阴影效果
      },
      formatter: '{a} <br/>{b}: {c}'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      boundaryGap: [0, 0.01],
      show: true
    },
    yAxis: {
      type: 'category',
      data: protocolNames,
      show: true
    },
    series: [
      {
        type: 'bar',
        data: projectNums,
        label: {
          show: true,
          position: 'right'
        }
      }
    ]
  };

  // 使用刚指定的配置项和数据显示图表
  ProtocolBarChart.setOption(option);
};
let currentChartType = 'pie';
// 切换图表类型并重新渲染图表
const toggleChart = () => {
  if (currentChartType === 'pie') {
    renderProtocolBarChart(); // 如果当前是饼图，则渲染条形图
    currentChartType = 'bar';
  } else {
    renderProtocolPieChart(); // 如果当前是条形图，则渲染饼图
    currentChartType = 'pie';
  }
};

// 设置定时器，定期切换图表
const startAnimation = () => {
  setInterval(() => {
    toggleChart();
  }, 5000); // 每3秒切换一次图表
};


watch(ProtocolData, () => {
  renderProtocolPieChart()
}
)

</script>
<style>
/* 调整Element UI 表格内部文字 */
.el-table .cell {
  font-size: 20px;
  /* 字体大小 */
  font-family: "Arial Blod", sans-serif;
  /* 字体样式 */
  text-align: center;
  /* 水平对齐 */
  vertical-align: middle;
  /* 垂直对齐 */
  line-height: 100px;
  /* 行高 */
  color: #181818;
}

.el-table__header-wrapper .cell {
  font-size: 30px;
  /* 设置为你希望的大小 */
  text-align: center;
  /* 水平居中 */
  line-height: 60px;
  /* 行高 */
  font-family: "Cooper Black";
  color: rgba(0, 0, 0, 0.59);
}

.project-name {
  /* 为项目名称定制的样式 */
  text-align: left;
  font-weight: bold;
  color: rgb(1, 102, 215);
  line-height: 1.2;
  font-size: 20px;
  /* 或者其他你希望的值 */
}

.project-description {
  /* 为项目描述定制的样式 */
  text-align: left;
  color: rgba(119, 119, 119, 0.86);
  font-size: 14px;
  line-height: 1.2;
  /* 或者其他你希望的值 */
}

.project-watch-and-star .material-icons,
.project-watch-and-star {
  font-size: 20px;
  /* 设置为你希望的大小 */
  vertical-align: middle;
  /* 设置垂直居中对齐 */
  margin-right: 5px;
  /* 调整图标和文本之间的间距 */
  color: #606266;
}
</style>
