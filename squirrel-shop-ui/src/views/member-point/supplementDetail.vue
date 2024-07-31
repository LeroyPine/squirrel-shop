<template>
  <div class="app-container">
    <el-form ref="supplementForm" :model="supplementForm" label-width="120px">
      <el-form-item label="用户ID" hidden>
        <el-input v-model="supplementForm.userId" disabled />
      </el-form-item>
      <el-form-item label="交易ID">
        <el-input v-model="supplementForm.id" disabled />
      </el-form-item>
      <el-form-item label="用户名称">
        <el-input v-model="supplementForm.userName" disabled />
      </el-form-item>
      <el-form-item label="交易金额">
        <el-input v-model="supplementForm.amount" disabled />
      </el-form-item>

      <el-form-item label="补充详情方式">
        <el-radio-group v-model="supplementForm.buyDetailType">
          <el-radio :label="1" @change="clearManualInput">选择商品</el-radio>
          <el-radio :label="2" @change="clearSelectedProducts">手动录入</el-radio>
        </el-radio-group>
      </el-form-item>

      <div v-if="supplementForm.buyDetailType === 1">
        <el-button type="primary" @click="showProductSelection">选择商品</el-button>
      </div>

      <div v-if="supplementForm.buyDetailType === 2">
        <el-form-item label="商品名称" :rules="[{ required: true, message: '商品名称不能为空', trigger: 'blur' }]">
          <el-input v-model="manualProduct.productName" placeholder="商品名称" />
        </el-form-item>
        <el-form-item label="商品数量" :rules="[{ required: true, message: '商品数量不能为空且必须大于0', trigger: 'blur' }]">
          <el-input-number v-model="manualProduct.productNum" :min="1" placeholder="数量不能为空" />
        </el-form-item>
        <el-form-item label="商品单价" :rules="[{ required: true, message: '商品单价不能为空且必须大于0', trigger: 'blur' }]">
          <el-input-number v-model="manualProduct.productMoney" :min="0.01" :step="0.01" placeholder="单价不能为空" />
        </el-form-item>
        <el-button type="primary" @click="addManualProduct">添加商品</el-button>
      </div>

      <el-table :data="supplementForm.productDetailDtoList" style="width: 100%; margin-top: 20px;">
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column label="商品数量">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.productNum" :min="1" @change="updateTotalAmount" />
          </template>
        </el-table-column>
        <el-table-column prop="productMoney" label="商品单价" />
        <el-table-column prop="totalMoney" label="总金额" :formatter="row => (row.productNum * row.productMoney).toFixed(2)" />
        <el-table-column label="操作">
          <template v-slot="scope">
            <el-button size="mini" type="danger" @click="removeProduct(scope.$index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="total-amount">
        总计金额: {{ totalAmount }}
      </div>

      <el-form-item>
        <el-button type="primary" @click="submitSupplementDetail">提交补充明细</el-button>
      </el-form-item>
    </el-form>

    <!-- 商品选择弹框 -->
    <el-dialog title="选择商品" :visible.sync="productDialogVisible" width="70%">
      <el-input v-model="productSearchQuery" placeholder="搜索商品名称" style="margin-bottom: 10px;" @keyup.enter="handleSearchInput" />
      <el-button style="margin-bottom: 10px;" @click="handleSearchInput">搜索</el-button>
      <el-table :data="productList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="productId" label="商品ID" />
        <el-table-column prop="productName" label="商品名称" />
        <el-table-column prop="productMoney" label="商品单价" />
        <el-table-column label="数量">
          <template slot-scope="scope">
            <el-input-number v-model="scope.row.productNum" :min="1" placeholder="数量不能为空" />
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="productTotal > 0" :total="productTotal" :page.sync="productQuery.page" :limit.sync="productQuery.limit" @pagination="getProductList" />
      <span slot="footer" class="dialog-footer">
        <el-button @click="productDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addSelectedProducts">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { buyProductDetail } from '@/api/member-points'
import { fetchProductList } from '@/api/product-info'
import Pagination from '@/components/Pagination' // 假设你的分页组件路径

export default {
  name: 'SupplementDetail',
  components: { Pagination },
  data() {
    return {
      supplementForm: {
        id: this.$route.query.transactionId,
        historyId: this.$route.query.transactionId,
        userId: this.$route.query.userId,
        userName: this.$route.query.userName,
        amount: this.$route.query.amount,
        buyDetailType: 1,
        productDetailDtoList: []
      },
      manualProduct: {
        productId: '',
        productName: '',
        productNum: 1,
        productMoney: null
      },
      productDialogVisible: false,
      productList: [],
      productTotal: 0,
      productQuery: {
        page: 1,
        limit: 10,
        productName: ''
      },
      selectedProducts: [],
      productSearchQuery: ''
    }
  },
  computed: {
    totalAmount() {
      return this.supplementForm.productDetailDtoList.reduce((sum, product) => {
        return sum + product.productNum * product.productMoney
      }, 0).toFixed(2)
    }
  },
  created() {
    // 初始化时获取商品列表
    this.getProductList()
  },
  methods: {
    getProductList() {
      const params = {
        ...this.productQuery,
        productName: this.productSearchQuery
      }
      fetchProductList(params).then(response => {
        this.productList = response.data.records
        this.productTotal = response.data.total
      })
    },
    handleSearchInput() {
      this.productQuery.page = 1
      this.getProductList()
    },
    showProductSelection() {
      this.productDialogVisible = true
    },
    handleSelectionChange(selection) {
      this.selectedProducts = selection
    },
    addSelectedProducts() {
      this.selectedProducts.forEach(product => {
        if (!product.productNum || product.productNum <= 0) {
          this.$message.error('商品数量不能为空且必须大于 0')
          return
        }
        const existingProduct = this.supplementForm.productDetailDtoList.find(p => p.productId === product.productId)
        if (existingProduct) {
          existingProduct.productNum += product.productNum
        } else {
          this.supplementForm.productDetailDtoList.push({
            productId: product.productId,
            productName: product.productName,
            productNum: product.productNum,
            productMoney: product.productMoney
          })
        }
      })
      this.productDialogVisible = false
      this.updateTotalAmount()
    },
    addManualProduct() {
      if (!this.manualProduct.productName) {
        this.$message.error('商品名称不能为空')
        return
      }
      if (!this.manualProduct.productNum || this.manualProduct.productNum <= 0) {
        this.$message.error('商品数量不能为空且必须大于 0')
        return
      }
      if (!this.manualProduct.productMoney || this.manualProduct.productMoney <= 0) {
        this.$message.error('商品单价不能为空且必须大于 0')
        return
      }
      // 生成临时的虚拟ID
      this.manualProduct.productId = 'manual_' + Date.now()
      const existingProduct = this.supplementForm.productDetailDtoList.find(p => p.productName === this.manualProduct.productName)
      if (existingProduct) {
        existingProduct.productNum += this.manualProduct.productNum
      } else {
        this.supplementForm.productDetailDtoList.push({
          ...this.manualProduct
        })
      }
      this.manualProduct = {
        productId: '',
        productName: '',
        productNum: 1,
        productMoney: null
      }
      this.updateTotalAmount()
    },
    clearManualInput() {
      this.manualProduct = {
        productId: '',
        productName: '',
        productNum: 1,
        productMoney: null
      }
    },
    clearSelectedProducts() {
      this.supplementForm.productDetailDtoList = []
      this.updateTotalAmount()
    },
    removeProduct(index) {
      this.supplementForm.productDetailDtoList.splice(index, 1)
      this.updateTotalAmount()
    },
    updateTotalAmount() {
      this.$forceUpdate()
    },
    submitSupplementDetail() {
      const totalAmount = parseFloat(this.totalAmount).toFixed(2)

      if (totalAmount !== parseFloat(this.supplementForm.amount).toFixed(2)) {
        this.$message.error('录入的商品金额与记录的交易金额不一致，无法提交')
        return
      }

      buyProductDetail(this.supplementForm).then(response => {
        this.$message.success('补充明细提交成功')
        this.$router.push({ name: 'MemberPointsDetailList' }) // 假设跳转到交易列表页面
      })
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.total-amount {
  margin-top: 20px;
  font-weight: bold;
  text-align: right;
}
</style>
