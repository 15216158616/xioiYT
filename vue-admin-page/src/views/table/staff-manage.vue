<template>
  <div class="app-container">
      <el-form :model="queryForm" size="mini" label-width="40px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="姓名">
              <el-input  v-model="queryForm.name" placeholder="请输入" clearable></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="类型">
              <el-select v-model="queryForm.type" placeholder="请选择"  style=" width: 100%">
                <el-option v-for="item in $store.state.staffTypeList"
                           :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div style="margin-bottom: 15px">
        <el-button type="primary" size="mini" :loading="loading" @click="queryStaffList(1)">查询</el-button>
        <el-button type="primary" size="mini" :loading="loading" style="margin-left: 5px;" @click="resetQueryStaffList">刷新</el-button>
        <el-button type="primary" size="mini" :loading="loading" style="margin-left: 5px;" @click="showAdd = true">新增</el-button>
        <el-button type="danger" size="mini" :loading="loading" style="margin-left: 5px;" @click="deleteStaff">删除</el-button>
      </div>

    <div>
      <el-table :data="userList" :height="contentHeight" v-loading="loading" ref="multipleTable"
                stripe border size="mini" :header-cell-style="{background:'#C2E0FF',color:'#124E76'}">
        <el-table-column type="index"></el-table-column>
        <el-table-column type="selection" width="55px"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="gender_desc" label="性别"></el-table-column>
        <el-table-column prop="phone" label="联系电话"></el-table-column>
        <el-table-column prop="type_desc" label="员工类型"></el-table-column>
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <el-button @click="editModel(scope.row)" type="text" size="mini">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!--分页-->
    <div style="margin-top: 10px">
      <el-pagination
        @size-change="sizeDetailChange"
        @current-change="currentDetailChange"
        :current-page.sync="detail_paging.page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="detail_paging.size"
        :total="detail_paging.total_elements"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </div>


    <!--删除提示-->
    <el-dialog title="提示" :visible.sync="showDelete" :close-on-click-modal="false" width = 30%>
      <span>你确定要删除数据吗?</span>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="loading" type="primary" size="mini" @click="deleteData">确定</el-button>
        <el-button size="mini" @click="showDelete = false">取消</el-button>
      </div>
    </el-dialog>

    <!--新增-->
    <el-dialog title="新增员工" :visible.sync="showAdd" :close-on-click-modal="false" width = 50%>
      <staff-add v-if="showAdd" @closeDialog="closeDialog" @successDialog="successDialog"></staff-add>
    </el-dialog>

    <!--编辑-->
    <el-dialog title="编辑员工" :visible.sync="showEdit" :close-on-click-modal="false" width = 50%>
      <staff-edit v-if="showEdit" @closeDialog="closeDialog" :staff="staff" @successDialog="successDialog"></staff-edit>
    </el-dialog>

  </div>
</template>

<script>

  import staffAdd from '@/views/table/staff-add'
  import staffEdit from '@/views/table/staff-edit'

  export default {

    components:{
        staffAdd,
        staffEdit,
    },

    data(){
      return {
        detail_paging: {  //分页
          page: 0,
          size: 10,
          total_elements: 0
        },
        loading:false,
        contentHeight: document.documentElement.clientHeight - 300,
        userList:[],
        queryForm:{
          name:'',
          type:''
        },
        staff:'',
        showAdd:false,
        showEdit:false,
        showDelete:false,
        selectIds:[]
      }
    },

    created:function () {
      this.$nextTick(function () {
        this.queryStaffList(1);//用户查询
      })
    },

    methods:{

      //查询且分页
      queryStaffList(page){

        this.loading = true;
        let param = this.queryForm;
        param.page = page;
        param.rows = this.detail_paging.size;

        this.$api.funeral.queryStaff(param).then(res =>{
            this.loading = false;
            if(res.status == 200){
              let itemList = res.data.data;
              itemList.forEach(data =>{
                data.super_admin = (data.super_admin && data.super_admin == true)?'是' : '否';
              });
              this.userList = itemList;

              this.detail_paging.size = res.data.paging.page_size;
              this.detail_paging.page = res.data.paging.current_page;
              this.detail_paging.total_elements = res.data.paging.records;

            }
          }).catch(error =>{
          this.loading = false;
        })
      },

      //刷新
      resetQueryStaffList(){

        this.queryForm = {
        };
        this.queryStaffList(1);
      },

      //勾选删除的数据
      deleteStaff(){

        this.selectIds = [];
        let dataList = this.$refs.multipleTable.selection;
        if(dataList.length == 0){
          this.$message.error("请至少选择一条数据");
        }else {
          this.selectIds = dataList.map(item => item.id);
          this.showDelete = true;
        }
      },

      //删除提示
      deleteData(){

        this.loading = true;
        let params = {
          ids:this.selectIds.join(",")
        }
        this.$api.funeral.deleteStaff(params).then(res =>{
          this.loading = false;
          if(res.status == 200){
            this.$message.success("删除成功");
            this.successDialog()
          }
        }).catch(error =>{
          this.loading = false;
        })
      },

      //编辑用户
      editModel(row){
        this.staff = row;
        this.showEdit = true;
      },

      //改变每页数量
      sizeDetailChange(size) {
        this.detail_paging.size = size;
        this.queryStaffList(this.detail_paging.page);
      },

      //改变页数
      currentDetailChange(page) {
        this.detail_paging.page = page;
        this.queryStaffList(page);
      },

      //取消关闭弹窗
      closeDialog() {

        this.showAdd = false;
        this.showEdit = false;
      },

      //成功关闭弹窗
      successDialog(){
        this.showAdd = false;
        this.showEdit = false;
        this.showDelete = false;
        this.queryStaffList(this.detail_paging.page);
      }
    }
  }
</script>

<style scoped>

</style>
