<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">

          <a-col :md="6" :sm="8">
            <a-form-item label="培训名称">
              <a-input placeholder="请输入培训名称" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="详细信息">
              <a-input placeholder="请输入详细信息" v-model="queryParam.content"></a-input>
            </a-form-item>
          </a-col>
        <template v-if="toggleSearchStatus">
        <a-col :md="6" :sm="8">
            <a-form-item label="报名开始时间">
              <a-input placeholder="请输入报名开始时间" v-model="queryParam.entryBeginDate"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="报名截止时间">
              <a-input placeholder="请输入报名截止时间" v-model="queryParam.entryEndDate"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="费用">
              <a-input placeholder="请输入费用" v-model="queryParam.fee"></a-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('培训管理')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
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

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item>
                <a @click="personList(record)">报名人员</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <train-modal ref="modalForm" @ok="modalFormOk"></train-modal>
    <train-person-modal v-if="trainPersonModalVisible" ref="trainPersonModal"></train-person-modal>
  </a-card>
</template>

<script>
  import TrainModal from './modules/TrainModal'
  import TrainPersonModal from './modules/TrainPersonModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'

  export default {
    name: "TrainList",
    mixins:[JeecgListMixin],
    components: {
      TrainModal,
        TrainPersonModal
    },
    data () {
      return {
        description: '培训管理管理页面',
          trainPersonModalVisible:false,
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
            title: '培训名称',
            align:"center",
            dataIndex: 'title'
           },
		   {
            title: '详细信息',
            align:"center",
            dataIndex: 'content'
           },
		   {
            title: '报名开始时间',
            align:"center",
            dataIndex: 'entryBeginDate'
           },
		   {
            title: '报名截止时间',
            align:"center",
            dataIndex: 'entryEndDate'
           },
		   {
            title: '费用',
            align:"center",
            dataIndex: 'fee'
           },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
		url: {
          list: "/siit/train/list",
          delete: "/siit/train/delete",
          deleteBatch: "/siit/train/deleteBatch",
          exportXlsUrl: "siit/train/exportXls",
          importExcelUrl: "siit/train/importExcel",
       },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
    methods: {
        personList: function(record){
          this.trainPersonModalVisible = true;

        }

    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>