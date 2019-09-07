<template>
  <a-modal :title="title"
           :width="1200"
           :visible="visible"
           @cancel="handleCancel"
           cancelText="关闭">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="姓名">
              <a-input placeholder="请输入姓名" v-model="queryParam.name"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="身份证号码">
              <a-input placeholder="请输入身份证号码" v-model="queryParam.idno"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="人员类型">
              <a-input placeholder="请输入人员类型" v-model="queryParam.type"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="矿山类型">
              <a-input placeholder="请输入矿山类型" v-model="queryParam.mineType"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="手机">
              <a-input placeholder="请输入手机" v-model="queryParam.phone"></a-input>
            </a-form-item>
          </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button type="primary" icon="download" @click="handleExportXls('报名信息')">导出</a-button>

    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">
      </a-table>
    </div>
    <!-- table区域-end -->


  </a-modal>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "TrainPersonModal",
    mixins:[JeecgListMixin],
    components: {
    },
    data () {
      return {
        title: '报名信息管理页面',
          visible: false,
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
           },
		   {
            title: 'name',
            align:"center",
            dataIndex: 'name'
           },
		   {
            title: 'idno',
            align:"center",
            dataIndex: 'idno'
           },
		   {
            title: '人员类型',
            align:"center",
            dataIndex: 'type'
           },
		   {
            title: '矿山类型',
            align:"center",
            dataIndex: 'mineType'
           },
		   {
            title: 'phone',
            align:"center",
            dataIndex: 'phone'
           },
		   {
            title: '学历',
            align:"center",
            dataIndex: 'education'
           },
		   {
            title: '职务',
            align:"center",
            dataIndex: 'post'
           },
		   {
            title: '公司名称',
            align:"center",
            dataIndex: 'company'
           },
		   {
            title: '户籍地址',
            align:"center",
            dataIndex: 'homeAddr'
           },
		   {
            title: '从业地址',
            align:"center",
            dataIndex: 'jobAddr'
           },
		   {
            title: '接收证书邮寄地址',
            align:"center",
            dataIndex: 'sendAddr'
           },
		   {
            title: '电子邮箱',
            align:"center",
            dataIndex: 'email'
           },
		   {
            title: '培训id',
            align:"center",
            dataIndex: 'trainId'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/siit/trainPerson/list",
          delete: "/siit/trainPerson/delete",
          deleteBatch: "/siit/trainPerson/deleteBatch",
          exportXlsUrl: "siit/trainPerson/exportXls",
          importExcelUrl: "siit/trainPerson/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
        handleCancel () {
            this.close()
        },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>