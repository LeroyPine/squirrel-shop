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
        <el-button type="success" @click="handleAddUser">新增用户</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%">
      <el-table-column align="center" label="用户ID" width="80">
        <template v-slot="scope">
          <span>{{ scope.row.userId }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="用户名称">
        <template v-slot="scope">
          <span>{{ scope.row.userName }}</span>
        </template>
      </el-table-column>

      <el-table-column width="150px" align="center" label="电话">
        <template v-slot="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>

      <el-table-column width="120px" align="center" label="用户积分">
        <template v-slot="scope">
          <span>{{ scope.row.points }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="20px" label="用户地址">
        <template v-slot="scope">
          <span>{{ scope.row.address }}</span>
        </template>
      </el-table-column>

      <el-table-column width="180px" label="最后修改时间">
        <template v-slot="scope">
          <span>{{ scope.row.updateDate }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="120">
        <template v-slot="scope">
          <el-button type="primary" size="small" icon="el-icon-edit" @click="handleEditUser(scope.row)">
            编辑
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form ref="userForm" :model="userForm" :rules="rules" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="userForm.userName" />
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="userForm.phone" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="userForm.address" />
        </el-form-item>
        <el-form-item label="积分" prop="points">
          <el-input v-model="userForm.points" type="number" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">提交</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import { fetchUserList, saveUserInfo, updateUserInfo } from '@/api/user-info'
import { isValidPhoneNumber } from '@/utils/validate'
import Pagination from '@/components/Pagination'
import { error } from 'autoprefixer/lib/utils' // Secondary package based on el-pagination

export default {
  name: 'UserList',
  components: { Pagination },
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'info',
        deleted: 'danger'
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
      },
      dialogVisible: false,
      isEditing: false,
      userForm: {
        userId: null,
        userName: '',
        phone: '',
        address: '',
        points: 0
      }, rules: {
        userName: [
          { required: true, message: '请输入用户名称', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { validator: this.validatePhone, trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入地址', trigger: 'blur' }
        ],
        points: [
          { required: true, message: '请输入积分', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.isEditing ? '编辑用户' : '新增用户'
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      fetchUserList(this.listQuery).then(response => {
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
    validatePhone(rule, value, callback) {
      if (!isValidPhoneNumber(value)) {
        callback(new Error('请输入有效的电话号码'))
      } else {
        callback()
      }
    },
    resetForm() {
      this.userForm = {
        userId: null,
        userName: '',
        phone: '',
        address: '',
        points: 0
      }
    },
    handleAddUser() {
      this.isEditing = false
      this.dialogVisible = true
      this.resetForm()
    },
    handleEditUser(user) {
      this.isEditing = true
      this.dialogVisible = true
      this.userForm = { ...user }
    },
    handleSubmit() {
      this.$refs.userForm.validate(async(valid) => {
        if (valid) {
          if (this.isEditing) {
            console.log('编辑用户信息：', this.userForm)
            await updateUserInfo(this.userForm)
          } else {
            console.log('新增用户信息：', this.userForm)
            await saveUserInfo(this.userForm)
          }
          this.dialogVisible = false
          this.getList()
        } else {
          console.log('表单验证失败')
          return false
        }
      })
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
