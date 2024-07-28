<template>
  <div class="app-container">

    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item>
        <el-button type="primary" @click="handleFilter">筛选</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="配置ID" width="80">
        <template v-slot="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="配置名称">
        <template v-slot="scope">
          <span>{{ scope.row.configName }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="120px" align="center" label="配置规则">
        <template v-slot="scope">
          <span>{{ scope.row.configRule }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="120px" align="center" label="配置类型">
        <template v-slot="scope">
          <span>{{ scope.row.configType |statusFilter }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="最后修改时间">
        <template v-slot="scope">
          <span>{{ scope.row.updateDate }}</span>
        </template>
      </el-table-column>

    </el-table>

  </div>
</template>

<script>
import { fetchMemberConfigList } from '@/api/member-points'

export default {
  name: 'MemberPointsList',
  filters: {
    statusFilter(status) {
      const statusMap = {
        1: '积分兑换率（每单位积分的兑换金额）'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      }
    }
  },
  computed: {

  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchMemberConfigList(this.listQuery).then(response => {
        this.list = response.data
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
