<template>
  <div class="dashboard-editor-container">
    <panel-group />

    <div class="chart-wrapper">
      <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
        <el-col :span="24">
          <div class="title">消费排行榜(前10名)</div>
          <el-table :data="topConsumers" style="width: 100%">
            <el-table-column prop="userName" label="用户名称" />
            <el-table-column prop="phone" label="用户电话" />
            <el-table-column prop="address" label="用户地址" />
            <el-table-column prop="money" label="消费金额" />
          </el-table>
        </el-col>
      </el-row>
    </div>

    <div class="motivation-wrapper">
      <el-row :gutter="50">
        <el-col :span="24">
          <div class="motivation-background">
            <div class="motivation-text">{{ randomQuote }}</div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import PanelGroup from './components/PanelGroup'
import { fetchUserRank } from '@/api/statistics' // 假设你的API路径

export default {
  name: 'DashboardAdmin',
  components: {
    PanelGroup
  },
  data() {
    return {
      topConsumers: [],
      quotes: [
        '相信自己，一切皆有可能。',
        '每一个不曾起舞的日子，都是对生命的辜负。',
        '你现在的努力，是为了以后有更多的选择。',
        '困难只是穿上工作服的机遇。',
        '成功源于不懈的努力。',
        '逆风的方向，更适合飞翔。',
        '每一天都是新的开始。',
        '没有伞的孩子必须努力奔跑。',
        '只要你勇敢地说出再见，生活一定会赐予你一个新的开始。',
        '生活本身就是一场冒险，勇敢者将赢得胜利。'
      ],
      randomQuote: ''
    }
  },
  created() {
    this.fetchTopConsumers()
    this.getRandomQuote()
  },
  methods: {
    fetchTopConsumers() {
      fetchUserRank().then(response => {
        this.topConsumers = response.data
      })
    },
    getRandomQuote() {
      const randomIndex = Math.floor(Math.random() * this.quotes.length)
      this.randomQuote = this.quotes[randomIndex]
      console.log('Random Quote:', this.randomQuote) // 调试信息
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fff;
    padding: 16px;
    margin-bottom: 32px;
    border-radius: 8px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }

  .title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 16px;
    text-align: center;
  }

  .motivation-wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 32px;
  }

  .motivation-background {
    width: 100%;
    max-width: 800px;
    padding: 24px;
    background-color: #f5f5f5;
    border-radius: 8px;
    text-align: center;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  }

  .motivation-text {
    font-size: 18px;
    font-weight: bold;
    color: #333;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
