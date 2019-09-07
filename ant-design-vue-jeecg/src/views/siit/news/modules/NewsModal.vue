<template>
  <a-modal :title="title"
           :width="800"
           :visible="visible"
           :confirmLoading="confirmLoading"
           @ok="handleOk"
           @cancel="handleCancel"
           cancelText="关闭">

    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item :labelCol="labelCol"
                     :wrapperCol="wrapperCol"
                     label="新闻标题">
          <a-input placeholder="请输入新闻标题"
                   v-decorator="['title', {}]" />
        </a-form-item>

        <a-form-item :labelCol="labelCol"
                     :wrapperCol="wrapperCol"
                     label="是否置顶">
          <a-select placeholder="请输入新闻标题"
                    v-decorator="['title', {}]" />
        </a-form-item>

        <a-form-item label="封面图片"
                     :labelCol="labelCol"
                     :wrapperCol="wrapperCol">
          <a-upload listType="picture-card"
                    class="image-uploader"
                    :showUploadList="false"
                    :action="uploadAction"
                    :data="{'isup':1}"
                    :headers="headers"
                    :beforeUpload="beforeUpload"
                    @change="handleChange">
            <img v-if="picUrl"
                 :src="getImageView()"
                 alt="选择图片"
                 style="height:104px;max-width:300px" />
            <div v-else>
              <a-icon :type="uploadLoading ? 'loading' : 'plus'" />
              <div class="ant-upload-text">上传</div>
            </div>
          </a-upload>
        </a-form-item>
        <a-form-item :labelCol="labelCol"
                     :wrapperCol="wrapperCol"
                     label="新闻内容">
          <j-editor v-model="content_"></j-editor>

        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
import { httpAction } from '@/api/manage'
import pick from 'lodash.pick'
import moment from "moment"
import JEditor from '@/components/jeecg/JEditor'
import Vue from 'vue'
import { ACCESS_TOKEN } from "@/store/mutation-types"

export default {
  name: "NewsModal",
  components: {
    JEditor
  },
  data () {
    return {
      title: "操作",
      visible: false,
      content_: "",
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
      uploadLoading: false,
      headers: {},
      picUrl: "",

      confirmLoading: false,
      form: this.$form.createForm(this),
      validatorRules: {
      },
      url: {
        fileUpload: window._CONFIG['domianURL'] + "/sys/common/upload",
        imgerver: window._CONFIG['domianURL'] + "/sys/common/view",
        add: "/siit/news/add",
        edit: "/siit/news/edit",
      },
    }
  },
  created () {
  },
  methods: {
    add () {
      this.edit({});
    },
    edit (record) {
      debugger
      const that = this;
      this.form.resetFields();
      this.model = Object.assign({}, record);

      this.content_ = record.content;
      this.picUrl = record.indexImage;
      this.visible = true;
      this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model, 'title', 'content', 'stat'))

        if (record.id == null)
          that.content_ = '';
        //时间格式化
      });

    },
    created () {

      const token = Vue.ls.get(ACCESS_TOKEN);
      this.headers = { "X-Access-Token": token }
    },
    computed: {
      uploadAction: function () {
        return this.url.fileUpload;
      }
    },
    close () {
      this.$emit('close');
      this.visible = false;
    },
    handleOk () {
      const that = this;
      // 触发表单验证
      this.form.validateFields((err, values) => {
        if (!err) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if (!this.model.id) {
            httpurl += this.url.add;
            method = 'post';
          } else {
            httpurl += this.url.edit;
            method = 'put';
          }
          let formData = Object.assign(this.model, values);
          //时间格式化
          formData.content = this.content_;
          httpAction(httpurl, formData, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message);
              that.$emit('ok');
            } else {
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
            that.close();
          })



        }
      })
    },
    handleCancel () {
      this.close()
    },
    getImageView () {
      return this.url.imgerver + "/" + this.model.indexImage;
    },
    handleChange (info) {
      debugger
      this.picUrl = "";
      if (info.file.status === 'uploading') {
        this.uploadLoading = true;
        return
      }
      if (info.file.status === 'done') {
        var response = info.file.response;
        this.uploadLoading = false;
        console.log(response);
        if (response.success) {
          this.model.image = response.message;
          this.picUrl = response.message;
        } else {
          this.$message.warning(response.message);
        }
      }
    },
    beforeUpload: function (file) {
      var fileType = file.type;
      if (fileType.indexOf('image') < 0) {
        this.$message.warning('请上传图片');
        return false;
      }
      //TODO 验证文件大小
    },

  }
}
</script>

<style lang="less" scoped>
</style>