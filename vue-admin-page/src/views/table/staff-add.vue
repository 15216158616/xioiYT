<template>
  <div>
    <el-form label-width="120px" size="mini" :model="formItem" ref="formItem" :rules="formRules">

      <el-form-item label="姓名" prop="name">
        <el-input v-model="formItem.name" placeholder="请输入姓名" class="input-pl"></el-input>
      </el-form-item>

      <el-form-item label="性别">
        <el-radio style="margin-right: 5px" v-for="item in $store.state.genderList"
                  v-model="formItem.gender" :label="item.value">{{item.label}}</el-radio>
      </el-form-item>

      <el-form-item label="联系电话">
        <el-input v-model="formItem.phone" placeholder="请输入联系电话" class="input-pl"></el-input>
      </el-form-item>

      <el-form-item label="人员类型">
        <el-select v-model="formItem.type" placeholder="请选择" class="input-pl">
          <el-option v-for="item in $store.state.staffTypeList"
                     :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <div style="margin-top: 10px;text-align: right">
      <el-button :loading="loading" type="primary" size="mini" @click="addStaff">保存</el-button>
      <el-button size="mini" @click="cancel">取消</el-button>
    </div>
  </div>
</template>

<script>

  export default {

    data(){
      return{
        formItem:{

        },
        loading:false,
        formRules:{
          name:[
            {required:true,message:"请输入姓名",trigger:'blur'}
          ]
        }
      }
    },

    created:function () {
      this.$nextTick(function () {

      })
    },

    methods:{

        addStaff(){

        this.$refs.formItem.validate(valid =>{
          if(valid){
            this.loading = true;
            let params = this.formItem;
            this.$api.funeral.addStaff(params).then(res =>{
              this.loading = false;
              if(res.status == 200){
                this.$message.success("新增成功");
                this.$emit('successDialog')
              }
            }).catch(error =>{
              this.loading = false;
            })
          }
        })
      },

      cancel(){
        this.$emit('closeDialog')
      }
    }
  }
</script>

<style scoped>

  .input-pl{
    width: 50%
  }
</style>
