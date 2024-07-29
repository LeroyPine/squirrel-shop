<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item label="用户名称">
        <el-input v-model="listQuery.userName" placeholder="用户名称" />
      </el-form-item>
      <el-form-item label="用户电话">
        <el-input v-model="listQuery.phone" placeholder="用户电话" />
      </el-form-item>
      <el-form-item label="积分变更类型">
        <el-select v-model="listQuery.changeType" placeholder="变更类型">
          <el-option label="信息修改" value="1" />
          <el-option label="购买商品" value="2" />
          <el-option label="积分兑换" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleFilter">筛选</el-button>
        <el-button @click="resetFilter">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">

      <el-table-column align="center" label="交易ID" width="80">
        <template v-slot="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="用户ID" width="80">
        <template v-slot="scope">
          <span>{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="用户名称" min-width="80px">
        <template v-slot="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="积分变更类型" min-width="80px">
        <template v-slot="scope">
          <span>{{ scope.row.changeType | changeTypeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="变更前积分" width="100">
        <template v-slot="scope">
          <span>{{ scope.row.beforePoints }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="变更后积分" width="100">
        <template v-slot="scope">
          <span>{{ scope.row.afterPoints }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="交易描述" min-width="230px">
        <template v-slot="scope">
          <span>{{ scope.row.changeDesc }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="备注" min-width="50px">
        <template v-slot="scope">
          <span>{{ scope.row.remark }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="创建时间" min-width="120px">
        <template v-slot="scope">
          <span>{{ scope.row.createDate }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="100px">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="handleViewDetail(scope.row)">
            查看明细
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 明细弹框 -->
    <el-dialog :title="`交易明细 - ${currentTransaction.userName} - ${currentTransaction.typename}`" :visible.sync="detailDialogVisible" width="50%">
      <el-table v-if="currentTransaction.detailList && currentTransaction.detailList.length" :data="currentTransaction.detailList" border fit highlight-current-row style="width: 100%">
        <el-table-column align="center" label="交易ID" width="80">
          <template v-slot="scope">
            <span>{{ scope.row.historyId }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="商品ID" width="80">
          <template v-slot="scope">
            <span>{{ scope.row.productId }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="商品名称" min-width="120px">
          <template v-slot="scope">
            <span>{{ scope.row.productName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="商品数量" width="100">
          <template v-slot="scope">
            <span>{{ scope.row.productNum }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="currentTransaction.changeType ===3" align="center" label="商品分数" width="100">
          <template v-slot="scope">
            <span>{{ scope.row.productPoints }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="currentTransaction.changeType ===2" align="center" label="商品金额" width="100">
          <template v-slot="scope">
            <span>{{ scope.row.productMoney }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="创建时间" min-width="150px">
          <template v-slot="scope">
            <span>{{ scope.row.createDate }}</span>
          </template>
        </el-table-column>
      </el-table>
      <div v-else-if="currentTransaction.changeType === 1">
        <span>信息修改无需录入明细</span>
      </div>
      <div v-else>
        <el-button type="primary" @click="openSupplementDetail('choose')">选择商品</el-button>
        <el-button type="primary" @click="openSupplementDetail('manual')">手动录入</el-button>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchHistoryList } from '@/api/member-points' // 假设你的 API 文件为 '@/api/points'
import Pagination from '@/components/Pagination'

export default {
  name: 'PointsTransactionList',
  components: { Pagination },
  filters: {
    changeTypeFilter(status) {
      const changeTypeMap = {
        1: '信息修改',
        2: '购买商品',
        3: '积分兑换'
      }
      return changeTypeMap[status]
    }
  },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        userName: '',
        userId: '',
        changeType: null
      },
      detailDialogVisible: false,
      currentTransaction: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchHistoryList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetFilter() {
      this.listQuery.userName = ''
      this.listQuery.userId = ''
      this.handleFilter()
    },
    handleViewDetail(transaction) {
      transaction.typename = this.changeTypeFilter(transaction.changeType)
      this.currentTransaction = transaction
      this.detailDialogVisible = true
    },
    openSupplementDetail(type) {
      const { id, userId, amount, userName, ...rest } = this.currentTransaction
      this.$router.push({
        name: 'supplementDetail',
        query: {
          transactionId: id,
          userId,
          type,
          amount,
          userName,
          transaction: JSON.stringify(rest)
        }
      })
    },
    changeTypeFilter(status) {
      const changeTypeMap = {
        1: '信息修改',
        2: '购买商品',
        3: '积分兑换'
      }
      return changeTypeMap[status]
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>
