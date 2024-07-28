<template>
  <div class="app-container">
    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item label="用户名称">
        <el-input v-model="listQuery.userName" placeholder="用户名称" />
      </el-form-item>
      <el-form-item label="用户电话">
        <el-input v-model="listQuery.phone" placeholder="用户电话" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleFilter">筛选</el-button>
        <el-button @click="resetFilter">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="用户ID" width="80">
        <template v-slot="scope">
          <span>{{ scope.row.userId }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="50px" align="center" label="用户名称">
        <template v-slot="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="电话">
        <template v-slot="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="剩余积分">
        <template v-slot="scope">
          <span>{{ scope.row.points }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="80px" align="center" label="最近一次积分变更时间">
        <template v-slot="scope">
          <span>{{ scope.row.updateDate }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" min-width="80px">
        <template v-slot="scope">
          <el-button type="primary" size="small" icon="el-icon-s-goods" @click="handleShopping(scope.row)">
            购物
          </el-button>
          <el-button type="success" size="small" icon="el-icon-s-cooperation" @click="handleExchange(scope.row)">
            积分兑换
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 购物弹框 -->
    <el-dialog :title="`购物 - ${currentUser.userName}`" :visible.sync="shoppingDialogVisible">
      <el-form ref="shoppingForm" :model="shoppingForm" label-width="100px">
        <el-form-item label="用户名称">
          <el-input v-model="currentUser.userName" disabled />
        </el-form-item>
        <el-form-item label="剩余积分">
          <el-input v-model="currentUser.points" disabled />
        </el-form-item>
        <el-form-item label="消费金额" prop="amount" :rules="[{ required: true, message: '请输入消费金额', trigger: 'blur' }, { validator: validateAmount, trigger: 'blur' }]">
          <el-input v-model="shoppingForm.amount" type="number" />
        </el-form-item>
        <el-form-item label="预计增加积分">
          <el-input v-model="estimatedPoints" disabled />
        </el-form-item>
        <el-form-item class="price-tip">
          <span>满10元兑换1积分哦,不足不计分!</span>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="shoppingForm.remark" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitShopping">提交</el-button>
          <el-button @click="shoppingDialogVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 积分兑换侧拉框 -->
    <el-drawer :title="`积分兑换 - ${currentUser.userName}`" :visible.sync="exchangeDrawerVisible" direction="rtl" size="40%">
      <el-form ref="exchangeForm" :model="exchangeForm" label-width="100px">
        <el-form-item label="用户名称">
          <el-input v-model="currentUser.userName" disabled />
        </el-form-item>
        <el-form-item label="剩余积分">
          <el-input v-model="currentUser.points" disabled />
        </el-form-item>

        <el-form-item label="选择奖品" prop="prizeId" :rules="[{ required: true, message: '请选择奖品', trigger: 'change' }]">
          <el-select
            v-model="exchangeForm.prizeId"
            placeholder="选择奖品"
            filterable
            remote
            :remote-method="handlePrizeSearch"
            :loading="loadingPrizes"
            style="width: 400px;"
            @change="handlePrizeChange"
          >
            <el-option
              v-for="item in prizes"
              :key="item.prizeId"
              :label="`${item.prizeName} - 剩余数量: ${item.prizeNum}`"
              :value="item.prizeId"
              style="height: 90px"
            >
              <div class="custom-option">
                <img :src="item.prizeImage" alt="奖品图片" class="prize-image">
                <div class="option-content">
                  <span class="prize-name">{{ item.prizeName }}</span>
                  <span class="prize-quantity">剩余数量: {{ item.prizeNum }}</span>
                </div>
              </div>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="兑换数量" prop="prizeNum" :rules="[{ required: true, message: '请输入数量', trigger: 'blur' }, { validator: validateQuantity, trigger: 'blur' }]">
          <el-input v-model="exchangeForm.prizeNum" type="number" @input="updateRequiredPoints" />
        </el-form-item>
        <el-form-item label="需消耗积分">
          <el-input v-model="requiredPoints" disabled />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="exchangeForm.remark" type="textarea" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitExchange">提交</el-button>
          <el-button @click="exchangeDrawerVisible = false">取消</el-button>
        </el-form-item>
      </el-form>
      <el-pagination v-if="prizeTotal > 20" :current-page="prizeQuery.page" :page-size="prizeQuery.limit" layout="total, prev, pager, next" :total="prizeTotal" @current-change="handlePrizePageChange" />
    </el-drawer>

  </div>
</template>

<script>
import { fetchMemberPointsList, redeemPrizes, buyProduct } from '@/api/member-points'
import Pagination from '@/components/Pagination'
import { fetchPrizeList } from '@/api/prize-info'

export default {
  name: 'UserList',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        userName: '',
        phone: ''
      },
      shoppingDialogVisible: false,
      exchangeDrawerVisible: false,
      currentUser: {},
      shoppingForm: {
        userId: null,
        amount: null,
        remark: ''
      },
      estimatedPoints: 0,
      exchangeForm: {
        userId: null,
        prizeId: null,
        prizeNum: 1,
        remark: ''
      },
      requiredPoints: 0,
      prizes: [],
      prizeQuery: {
        page: 1,
        limit: 20,
        prizeName: ''
      },
      prizeTotal: 0,
      loadingPrizes: false,
      selectedPrizeId: null
    }
  },
  watch: {
    'shoppingForm.amount': 'updateEstimatedPoints',
    selectedPrizeId(newVal) {
      if (newVal) {
        this.exchangeForm.prizeId = newVal
        this.updateRequiredPoints()
      } else {
        this.exchangeForm.prizeId = null
        this.requiredPoints = 0
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchMemberPointsList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    resetFilter() {
      this.listQuery.userName = ''
      this.listQuery.phone = ''
      this.handleFilter()
    },
    handleShopping(user) {
      this.currentUser = { ...user }
      this.shoppingForm.userId = user.userId
      this.shoppingForm.amount = null
      this.shoppingForm.remark = ''
      this.estimatedPoints = 0
      this.shoppingDialogVisible = true
    },
    validateAmount(rule, value, callback) {
      if (value <= 0) {
        callback(new Error('消费金额必须大于0'))
      } else {
        callback()
      }
    },
    updateEstimatedPoints() {
      if (this.shoppingForm.amount > 0) {
        this.estimatedPoints = Math.floor(this.shoppingForm.amount / 10)
      } else {
        this.estimatedPoints = 0
      }
    },
    submitShopping() {
      this.$refs.shoppingForm.validate(valid => {
        if (valid) {
          // 提交购物数据
          console.log('提交购物数据', this.shoppingForm)
          buyProduct(this.shoppingForm).then(res => {
            this.$message.success('恭喜您,消费完成!')
            this.getList()
            this.shoppingDialogVisible = false
          })
        }
      })
    },
    handleExchange(user) {
      this.currentUser = { ...user }
      this.exchangeForm.userId = user.userId
      this.exchangeForm.prizeId = null
      this.exchangeForm.prizeNum = 1
      this.exchangeForm.remark = ''
      this.requiredPoints = 0
      this.fetchPrizeList()
      this.exchangeDrawerVisible = true
    },
    handlePrizeChange() {
      this.updateRequiredPoints()
    },
    updateRequiredPoints() {
      const selectedPrize = this.prizes.find(prize => prize.prizeId === this.exchangeForm.prizeId)
      if (selectedPrize && this.exchangeForm.prizeNum > 0) {
        this.requiredPoints = selectedPrize.points * this.exchangeForm.prizeNum
      } else {
        this.requiredPoints = 0
      }
      if (this.requiredPoints > this.currentUser.points) {
        this.$message.error('所需积分超过用户剩余积分')
        this.requiredPoints = 0 // 重置所需积分以防止提交时出错
      }
    },
    validateQuantity(rule, value, callback) {
      const selectedPrize = this.prizes.find(prize => prize.prizeId === this.exchangeForm.prizeId)
      if (value <= 0) {
        callback(new Error('数量必须大于0'))
      } else if (selectedPrize && value > selectedPrize.prizeNum) {
        callback(new Error('兑换数量不能高于剩余数量,请补充库存!'))
      } else {
        callback()
      }
    },
    fetchPrizeList() {
      this.loadingPrizes = true
      fetchPrizeList(this.prizeQuery).then(response => {
        this.prizes = response.data.records
        this.prizeTotal = response.data.total
        this.loadingPrizes = false
      })
    },
    submitExchange() {
      this.$refs.exchangeForm.validate(valid => {
        if (valid) {
          if (this.requiredPoints > this.currentUser.points) {
            this.$message.error('剩余积分不足')
          } else {
          // 提交积分兑换数据
            redeemPrizes(this.exchangeForm).then(response => {
              this.$message.success('恭喜您,兑换成功!')
              this.getList()
              this.exchangeDrawerVisible = false
            })
          }
        }
      })
    },
    handlePrizePageChange(page) {
      this.prizeQuery.page = page
      this.fetchPrizeList()
    },
    handlePrizeSearch(query) {
      this.prizeQuery.prizeName = query
      this.prizeQuery.page = 1
      this.fetchPrizeList()
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
.custom-option {
  display: flex;
  align-items: center;
  padding: 5px 0;
}
.prize-image {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}
.option-content {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.prize-name {
  font-weight: bold;
}
.prize-quantity {
  color: #666;
}
.price-tip {
  text-align: left;
  margin-bottom: 20px;
  font-size: 14px;
  color: #C03F08FF;
}
</style>

