<template>
  <div class="row py-3 my-5 mx-2">

    <div id="submitAlert" class="alert alert-success alert-dismissible fade" role="alert">
      <strong>提交成功</strong> {{ showMessage }}
    </div>

    <div class="mx-auto container">
      <div class="appTypeGroup">
        <label>应用类型</label>
        <div class="form-check form-check-inline mx-2" v-for="t in types" :key="t.id">
          <input class="form-check-input" type="radio" :value="t.id" :id="t.comp"
                 v-model="currentId">
          <label class="form-check-label" :for="t.comp">{{t.name}}</label>
        </div>
      </div>

      <hr/>

<!--      <div class="form-floating my-3">-->
<!--        <input type="text" class="form-control" id="textInputTitle" aria-describedby="textInputTitleHelp"-->
<!--               placeholder="知识点标题" v-model="title">-->
<!--        <small id="textInputTitleHelp"-->
<!--               class="form-text text-muted">简要的概括知识点的主要内容</small>-->
<!--        <label for="textInputTitle">知识点标题</label>-->
<!--      </div>-->

<!--      <div class="form-floating mb-3">-->
<!--        <textarea class="form-control" id="textInputContent" placeholder="知识点正文"-->
<!--                  style="height: 100px" v-model="content"></textarea>-->
<!--        <label for="textInputContent">知识点正文</label>-->
<!--      </div>-->

      <english-word-book> </english-word-book>


      <knowledge-link :reset="reset" :submit="submit" @on-submit="updateLink"> </knowledge-link>

      <hr/>

      <knowledge-tag :reset="reset" :submit="submit" @on-submit="updateTag"></knowledge-tag>

    </div>
  </div>
</template>

<script>
import LeetCodeNote from "@/components/link/LeetCodeNote";
import SimpleBaseLink from "@/components/link/SimpleBaseLink";
import $ from "jquery";
import KnowledgeTag from "./KnowledgeTag";
import KnowledgeLink from "./KnowledgeLink";
import EnglishWordBook from "./EnglishWordBook";

export default {
  name: "KnowledgeForm",
  components: {EnglishWordBook, KnowledgeLink, KnowledgeTag},
  props: {
    doSubmit: Boolean,
    doShow: Boolean,
    showMessage: String,
    knowledge: Object,
    submit: Number,
  },
  data: function () {
    return {
      currentId: 0,
      types: [],
      title: "",
      content: "",
      links: [],
      initLinks: [],
      reset: 0,
    }
  },
  methods: {
    updateLink: function (links) {
      this.links = links
      //子模块完成提交操作后, 再提交完整的数据
      //this.submitKnowledge();
    },
    updateTag: function (tags) {
      this.tags = tags
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
    this.$axios.get('/appType/getAllTypes').then(response => {
      this.types = response.data.data;
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