<template>
  <div class="app-container">
    <!-- 列表和操作按钮 -->
    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item label="商品名称">
        <el-input v-model="listQuery.productName" placeholder="商品名称" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleFilter">筛选</el-button>
        <el-button @click="resetFilter">重置</el-button>
        <el-button type="success" @click="handleAddProduct">新增商品</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="商品ID" width="150px">
        <template v-slot="scope">
          <span>{{ scope.row.productId }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="商品名称">
        <template v-slot="scope">
          <span>{{ scope.row.productName }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="150px" align="center" label="商品图片">
        <template v-slot="scope">
          <img :src="scope.row.productImage" alt="商品图片" style="width: 100px; height: 100px; object-fit: cover;">
        </template>
      </el-table-column>

      <el-table-column min-width="100px" align="center" label="商品介绍">
        <template v-slot="scope">
          <span>{{ scope.row.productDesc }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="商品数量">
        <template v-slot="scope">
          <span>{{ scope.row.productNum }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="商品金额">
        <template v-slot="scope">
          <span>{{ scope.row.productMoney }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" align="center" label="修改时间">
        <template v-slot="scope">
          <span>{{ scope.row.updateDate }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="180">
        <template v-slot="scope">
          <el-button type="primary" size="small" @click="handleEditProduct(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <!-- 弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form ref="productForm" :model="productForm" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="productForm.productName" />
        </el-form-item>

        <el-form-item label="商品图片" prop="productImage">
          <div class="upload-container">
            <el-upload
              ref="upload"
              class="upload-demo"
              drag
              :before-upload="beforeImageUpload"
              :show-file-list="false"
              :http-request="customRequest"
              name="file"
              action=""
            >
              <el-button size="small" type="primary">点击上传</el-button>
              <div class="upload-text">拖拽文件到这里，或点击按钮上传</div>
            </el-upload>

            <!-- 图片预览和删除 -->
            <div v-if="productForm.productImage" class="preview-container">
              <img :src="productForm.productImage" alt="商品图片" class="uploaded-image">
              <div class="delete-overlay" @click="removeImage">×</div>
            </div>
          </div>
        </el-form-item>

        <el-form-item label="商品介绍" prop="productDesc">
          <el-input v-model="productForm.productDesc" />
        </el-form-item>
        <el-form-item label="商品数量" prop="productNum">
          <el-input-number v-model="productForm.productNum" type="number" />
        </el-form-item>
        <el-form-item label="商品金额" prop="productMoney">
          <el-input-number v-model="productForm.productMoney" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { fetchProductList, saveProductInfo, updateProductInfo } from '@/api/product-info' // 替换为你的 API
import { uploadFile } from '@/api/upload' // 替换为你的 API
import Pagination from '@/components/Pagination'

export default {
  name: 'ProductManagement',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        productName: ''
      },
      dialogVisible: false,
      isEditing: false,
      productForm: {
        productId: null,
        productName: '',
        productImage: '',
        productDesc: '',
        productNum: 0,
        productMoney: 0
      },
      rules: {
        productName: [
          { required: true, message: '请输入商品名称', trigger: 'blur' }
        ],
        productImage: [
          { required: false, message: '请上传商品图片', trigger: 'change' }
        ],
        productDesc: [
          { required: true, message: '请输入商品介绍', trigger: 'blur' }
        ],
        productNum: [
          { required: true, type: 'number', message: '请输入商品数量', trigger: 'change' }
        ],
        productMoney: [
          { required: true, type: 'number', message: '请输入商品金额', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.isEditing ? '编辑商品' : '新增商品'
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchProductList(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.getList()
    },
    resetFilter() {
      this.listQuery.productName = ''
      this.getList()
    },
    handleAddProduct() {
      this.isEditing = false
      this.dialogVisible = true
      this.resetForm()
    },
    handleEditProduct(product) {
      this.isEditing = true
      this.dialogVisible = true
      this.productForm = { ...product }
    },
    beforeImageUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('上传图片只能是 JPG 或 PNG 格式!')
      }
      const isLt5M = file.size / (1024 * 1024) < 5
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过 5MB!')
      }
      return isJpgOrPng && isLt5M
    },
    handleImageSuccess(response, file, fileList) {
      this.productForm.productImage = response.url // 假设接口返回的图片 URL
    },
    handleSubmit() {
      this.$refs.productForm.validate((valid) => {
        if (valid) {
          if (this.isEditing) {
            updateProductInfo(this.productForm).then(() => {
              this.dialogVisible = false
              this.getList()
              this.$message.success('编辑成功')
            })
          } else {
            saveProductInfo(this.productForm).then(() => {
              this.dialogVisible = false
              this.getList()
              this.$message.success('新增成功')
            })
          }
        } else {
          console.log('表单验证失败')
          return false
        }
      })
    },
    handleReset() {
      this.$refs.productForm.resetFields()
    },
    resetForm() {
      this.productForm = {
        productId: null,
        productName: '',
        productImage: '',
        productDesc: '',
        productNum: 0,
        productMoney: 0
      }
    },
    customRequest({ file, onSuccess, onError }) {
      const formData = new FormData()
      formData.append('file', file)
      uploadFile(formData).then(response => {
        this.productForm.productImage = response.data.fullFileName
        onSuccess(response.data, file) // 调用成功回调
      }).catch(error => {
        onError(error) // 调用失败回调
      })
    },
    removeImage() {
      this.productForm.productImage = ''
      this.$refs.upload.clearFiles() // 清除上传文件
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
.upload-container {
  display: flex;
  flex-direction: column;
}

.upload-text {
  margin-top: 10px;
  color: #c0c4cc;
  font-size: 14px;
}

.preview-container {
  margin-top: 10px;
  position: relative;
  min-width: 100px; /* Same as upload-demo width */
  text-align: center;
}

.uploaded-image {
  max-width: 100%;
  max-height: 150px; /* Adjusted to fit within the container */
  object-fit: contain; /* Maintain aspect ratio */
  border-radius: 4px;
}

.delete-overlay {
  position: absolute;
  top: 5px;
  right: 5px;
  width: 24px;
  height: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.5);
  color: #fff;
  font-size: 16px;
  cursor: pointer;
  border-radius: 50%;
  z-index: 10;
  opacity: 0;
  transition: opacity 0.3s;
}

.preview-container:hover .delete-overlay {
  opacity: 1;
}
</style>
