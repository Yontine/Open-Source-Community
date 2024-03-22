<template>
  <div class="github-stats">

    <div class="stats-container">
      <div class="stat-box">
        <p>总用户数量</p>
        <div class="count-box"> 2000万+ </div>
      </div>
      <div class="stat-box">
        <p>总项目数量</p>
        <div class="count-box"> 3亿+ </div>
      </div>
      <div class="stat-box">
        <p>总语言数量</p>
        <div class="count-box">580+</div>
      </div>
      <div class="stat-box">
        <p>总收藏数量</p>
        <div class="count-box">4亿+</div>
      </div>
    </div>
  </div>

  <div class="github-clock">
    <div class="demo-progress">
      <el-progress :percentage="100" status="success" :indeterminate="true" :duration="5" />
      <el-progress :percentage="100" status="success" :indeterminate="true" :duration="1" />
      <el-progress :percentage="100" :stroke-width="15" status="success" striped striped-flow :duration="10" />
    </div>

    <div class="clock-container">
      <h1>GitHub 社区已运行</h1>
      <br><br>
      <div class="clock">
        <div>{{ years }}<span>Years</span></div>
        <div>{{ months }}<span>Months</span></div>
        <div>{{ days }}<span>Days</span></div>
        <div>{{ hours }}<span>Hours</span></div>
        <div>{{ minutes }}<span>Minutes</span></div>
        <div>{{ seconds }}<span>Seconds</span></div>
      </div>
    </div>

    <div class="demo-progress">
      <el-progress type="dashboard" :percentage="percentage2" :color="colors" />
    </div>
  </div>

  <div class="chart-container1">
    <div id="line-chart" style="width: 100%; height: 400px;"></div>
    <div id="pie-chart" style="width: 100%; height: 400px;"></div>
  </div>

  <el-timeline>
    <el-timeline-item timestamp="2023/12/23" placement="top">
      <el-card>
        <h4>2023年12月23日，GitHub宣布正式上线了新的订阅、讨论组、文本渲染、在线文件编辑器等功能。</h4>
        <p>2023年12月23日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2022" placement="top">
      <el-card>
        <h4>2022年，全球新增代码仓库超过8570万个，同比增长了20%；开源贡献有4.13亿次，累计贡献超过35亿次</h4>
        <p>2022年</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2021/6/23" placement="top">
      <el-card>
        <h4>GitHub Copilot发布 - GitHub Copilot是由GitHub和OpenAI合作推出的人工智能代码助手，引起了广泛关注。</h4>
        <p>2021年6月23日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2020/4/7" placement="top">
      <el-card>
        <h4>GitHub宣布支持私有仓库免费使用 - GitHub宣布，从这一天开始，所有用户均可免费使用GitHub私有仓库。</h4>
        <p>2020年4月7日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2019/7/8" placement="top">
      <el-card>
        <h4>GitHub宣布存储核心开源代码在北极 - GitHub宣布将其存储核心开源代码的目标地点设置在挪威北极地区的Svalbard群岛。</h4>
        <p>2019年7月8日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2018" placement="top">
      <el-card>
        <h4>GitHub在中国用户急剧增加 - GitHub在2018年，中国用户数量迅速增加，中国开发者对GitHub的贡献和参与程度明显提升。</h4>
        <p>2018年</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2016/11/13" placement="top">
      <el-card>
        <h4>GitHub推出GitHub Actions - GitHub Actions正式推出，为开发者提供了自动化工作流的工具。</h4>
        <p>2016年11月13日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2018/6/4" placement="top">
      <el-card>
        <h4>GitHub被微软收购 - 微软以75亿美元的价格收购了GitHub，标志着开源社区与技术巨头之间的结合。</h4>
        <p>2018年6月4日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2012/6/6" placement="top">
      <el-card>
        <h4>GitHub进入中国 - GitHub于2012年在中国建立了自己的官方网站(github.com.cn)，以提供更好的服务和支持中国开发者。</h4>
        <p>2012年6月6日</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2008/4/10" placement="top">
      <el-card>
        <h4>GitHub正式成立 - Tom Preston-Werner、Chris Wanstrath和PJ Hyett创立GitHub，成为开发者分享和协作的主要平台。</h4>
        <p>2008年4月10日</p>
      </el-card>
    </el-timeline-item>
  </el-timeline>
</template>


<script setup lang="ts">
import axios from 'axios';
import request from "@/utils/request";
import { ref, onMounted } from 'vue';
import { Minus, Plus } from '@element-plus/icons-vue'
import * as echarts from 'echarts';

// 显示时间人数开始
// const userCount = ref(0);
// const projectCount = ref(0);
// const languageCount = ref(0);
// const starredCount = ref(0);

const establishmentDate = new Date('2008-04-10T21:00:00Z');
const currentTime = ref(new Date());

const years = ref(0);
const months = ref(0);
const days = ref(0);
const hours = ref(0);
const minutes = ref(0);
const seconds = ref(0);

const calculateDuration = () => {
  const timeDifference = currentTime.value - establishmentDate;

  years.value = Math.floor(timeDifference / (365 * 24 * 60 * 60 * 1000));
  const remainingTime = timeDifference % (365 * 24 * 60 * 60 * 1000);

  months.value = Math.floor(remainingTime / (30 * 24 * 60 * 60 * 1000));
  days.value = Math.floor((remainingTime % (30 * 24 * 60 * 60 * 1000)) / (24 * 60 * 60 * 1000));
  hours.value = Math.floor((remainingTime % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000));
  minutes.value = Math.floor((remainingTime % (60 * 60 * 1000)) / (60 * 1000));
  seconds.value = Math.floor((remainingTime % (60 * 1000)) / 1000);
};

// onMounted(async () => {
//   try {
//     const userResponse = await axios.get('https://api.github.com/users');
//     userCount.value = userResponse.data.length;

//     const repoResponse = await axios.get('https://api.github.com/repositories');
//     projectCount.value = repoResponse.data.length;

//     const languageResponse = await axios.get('https://api.github.com/languages');
//     languageCount.value = Object.keys(languageResponse.data).length;

//     const starredResponse = await axios.get('https://api.github.com/user/starred');
//     starredCount.value = starredResponse.data.length;
//   } catch (error) {
//     console.error('Error fetching GitHub data:', error);
//   }
// });

onMounted(() => {
  setInterval(() => {
    currentTime.value = new Date();
    calculateDuration();
  }, 1000);

  calculateDuration();
});

// 显示时间人数结束

// 显示仪表盘开始
const percentage = ref(10)
const percentage2 = ref(0)

const colors = [
  { color: '#f56c6c', percentage: 20 },
  { color: '#e6a23c', percentage: 40 },
  { color: '#5cb87a', percentage: 60 },
  { color: '#1989fa', percentage: 80 },
  { color: '#6f7ad3', percentage: 100 },
]


onMounted(() => {
  setInterval(() => {
    percentage2.value = (percentage2.value % 100) + 10
  }, 500)
})

// 显示仪表盘结束

// 趋势图表
const pieChartInstance = ref<echarts.ECharts | null>(null);
const responseData1 = ref<any[]>([]);
const lineChartInstance = ref<echarts.ECharts | null>(null);
const responseData2 = ref<any[]>([]);

const fetchData1 = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/total_num",
    });
    responseData1.value = response.data.data || [];
    // console.log(responseData1)
    renderLineChart();
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const renderLineChart = () => {
  const legendData = responseData1.value.map((data) => data.time);
  const seriesData = responseData1.value.map((data) => ({ name: data.time, value: data.num }));
  const option = {
    xAxis: {
      type: 'category',
      data: legendData
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: seriesData,
        type: 'line',
        smooth: true
      }],


  };

  const chartDom = document.getElementById('line-chart');
  if (chartDom) {
    lineChartInstance.value = echarts.init(chartDom);
    lineChartInstance.value.setOption(option);
  }
};

const fetchData2 = async () => {
  try {
    const response = await request({
      method: "GET",
      url: "http://localhost:8080/active",
    });
    responseData2.value = response.data.data || [];
    // console.log(responseData1)
    renderPieChart();
  } catch (error) {
    console.error('Error fetching data:', error);
  }
};

const renderPieChart = () => {
  const legendData = responseData2.value.map((data) => data.language);
  const seriesData = responseData2.value.map((data) => ({ name: data.language, value: data.num }));

  const option = {
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
onMounted(fetchData1);
onMounted(fetchData2);
// 趋势图表结束
</script>

<style scoped>
.github-stats {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.stats-container {
  display: flex;
  justify-content: space-around;
  width: 100%;
}

.stat-box {
  flex: 1;
  margin: 20px;
  padding: 15px;
  border: 1px solid #ccc;
  border-radius: 8px;
  text-align: center;
}

.count-box {
  font-size: 24px;
  font-weight: bold;
  margin-top: 10px;
}

.github-clock {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  text-align: center;
}

.clock-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.clock {
  display: flex;
  flex-direction: row;
  justify-content: center;
  font-size: 18px;
  font-weight: bold;
}

.clock div {
  margin-right: 10px;
}

.clock span {
  font-size: 14px;
  font-weight: normal;
  color: #555;
}

.demo-progress .el-progress--line {
  margin-bottom: 15px;
  width: 150px;
  margin-left: 0;
}

.demo-progress .el-progress--circle {
  margin-right: 15px;
}

.chart-container1 {
  display: flex;
  flex-direction: row;
  align-items: center;
  text-align: center;
  justify-content: space-between;
}
</style>