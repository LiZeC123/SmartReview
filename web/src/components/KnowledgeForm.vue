<template>
  <div class="row py-3 my-5 mx-2">

    <div id="submitAlert" class="alert alert-success alert-dismissible fade" role="alert">
      <strong>提交成功</strong> {{ showMessage }}
    </div>

    <div class="mx-auto container">
      <div class="appTypeGroup">
        <label>应用类型</label>
        <div class="form-check form-check-inline mx-2" v-for="t in appType.types" :key="t.id">
          <input class="form-check-input" type="radio" :value="t.id" :id="t.comp"
                 v-model="appType.currentId">
          <label class="form-check-label" :for="t.comp">{{t.name}}</label>
        </div>
      </div>

      <div class="form-floating my-3">
        <input type="text" class="form-control" id="textInputTitle" aria-describedby="textInputTitleHelp"
               placeholder="知识点标题" v-model="title">
        <small id="textInputTitleHelp"
               class="form-text text-muted">简要的概括知识点的主要内容</small>
        <label for="textInputTitle">知识点标题</label>
      </div>

      <div class="form-floating mb-3">
        <textarea class="form-control" id="textInputContent" placeholder="知识点正文"
                  style="height: 100px" v-model="content"></textarea>
        <label for="textInputContent">知识点正文</label>
      </div>

      <component :is="appType.types[appType.currentId].comp" :title="title" :links="getInitLinks" :submit="doSubmit" :clear="clear"
                 @link-change="updateLink"></component>

      <hr/>

      <div class="form-floating mb-3">
        <h6>勾选此知识点的标签</h6>
        <div class="form-check form-check-inline" v-for="tag in tagList" :key="tag.id">
          <input class="form-check-input" type="checkbox" id="inlineCheckbox1" :value="tag.name" v-model="tags">
          <label class="form-check-label" for="inlineCheckbox1">{{ tag.name }}</label>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import EnglishWordBook from "@/components/link/EnglishWordBook";
import LeetCodeNote from "@/components/link/LeetCodeNote";
import SimpleBaseLink from "@/components/link/SimpleBaseLink";
import $ from "jquery";

export default {
  name: "KnowledgeForm",
  components: {SimpleBaseLink, LeetCodeNote, EnglishWordBook},
  props: {
    doSubmit: Boolean,
    doShow: Boolean,
    showMessage: String,
    knowledge: Object,
  },
  data: function () {
    return {
      appType: {
        types: {1:{comp: "EnglishWordBook"}}, // 设置一个初始值
        currentId: 1,
        currentComp: "EnglishWordBook"
      },
      title: "",
      content: "",
      links: [],
      initLinks: [],
      tags: [],
      tagList: [],
    }
  },
  methods: {
    updateLink: function (links) {
      this.links = links;
      //子模块完成提交操作后, 再提交完整的数据
      this.submitKnowledge();
    },
    submitKnowledge: function () {
      const knowledge = {
        "appType": this.appType.currentId,
        "title": this.title,
        "content": this.content,
        "link": JSON.stringify(this.links),
        "tag": this.tags
      }

      this.$emit('submit', knowledge);
    },
    resetForm: function () {
      this.title = "";
      this.content = "";
      this.tags = [];
    }
  },
  computed: {
    linkType: function () {
      if (this.knowledge !== undefined) {
        return SimpleBaseLink;
      }
      switch (this.appType) {
        case "Base":
          return SimpleBaseLink;
        case "EnglishWordBook":
          return EnglishWordBook;
        case "LeetCodeNote":
          return LeetCodeNote;
        default:
          return SimpleBaseLink;
      }
    },
    getInitLinks: function () {
      return this.initLinks;
    },
    clear: function () {
      return this.title === "";
    }
  },
  watch: {
    'doShow': function (newValue) {
      if (newValue === true) {
        const alert = $('#submitAlert')
        alert.addClass("show").css("display", "block");
        setTimeout(() => alert.removeClass("show").css("display", "none"), 1500);
        this.resetForm();
        this.$emit('after-show');
      }
    },
    'knowledge': function (knowledge) {
      if (knowledge !== undefined) {
        this.title = knowledge.title;
        this.content = knowledge.content;
        this.initLinks = knowledge.link;
        this.tags = knowledge.tag;
      }
    }
  },
  created() {
    this.$axios({
      method: "get",
      url: "/tag/selectAll",
    }).then(response => {
      if (response.data.success) {
        this.tagList = response.data.data;
      }
    });
    this.$axios.get('/appType/getAllTypes').then(response => {
      this.appType.types = response.data.data;
    });
  }
}
</script>

<style scoped>
#submitAlert {
  position: fixed;
  top: 56px;
  left: 0;
  z-index: 99;
  display: none;
}
</style>