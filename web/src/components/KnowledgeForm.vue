<template>
  <div class="row py-3 my-5 mx-2">

    <div id="submitAlert" class="alert alert-success alert-dismissible fade" role="alert"
         style="position: fixed; top: 56px;left: 0; z-index: 99;display: none">
      <strong>创建成功</strong> 知识点已进入自动复习队列
    </div>

    <form id="createForm" class="mx-auto" onsubmit="return false;">
      <div class="appTypeGroup">
        <label>应用类型</label>

        <div class="form-check form-check-inline mx-2" v-for="t in appType.types" :key="t.id">
          <input class="form-check-input" type="radio" :value="t.id" id="radioEnglish"
                 v-model="appType.currentId">
          <label class="form-check-label" for="radioEnglish">{{t.name}}</label>
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

      <component :is="appType.types[appType.currentId].comp" :title="title" @link-change="updateLink"></component>

      <div class="form-floating mb-3">
        <input type="text" class="form-control" id="textInputTag" aria-describedby="textInputTagHelp"
               placeholder="标签" v-model="tag">
        <small id="textInputTagHelp" class="form-text text-muted">与此知识点关联的标签</small>
        <label for="textInputTag">标签</label>
      </div>

      <button type="submit" class="btn btn-primary float-end" @click="submitInfo">创建知识点</button>

    </form>
  </div>
</template>

<script>
import EnglishWordBook from "@/components/link/EnglishWordBook";
import LeetCodeNote from "@/components/link/LeetCodeNote";
import $ from "jquery"

export default {
  name: "KnowledgeForm",
  components: {LeetCodeNote, EnglishWordBook},
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
      tag: [],
    }
  },
  created() {
    this.$axios.get('/appType/getAllTypes').then(response => {
      this.appType.types = response.data.data;
    });

  },
  methods: {
    updateLink: function (links) {
      this.links = links;
    },
    submitInfo: function () {
      const knowledge = {
        "appType": this.appType.currentId,
        "title": this.title,
        "content": this.content,
        "link": JSON.stringify(this.links),
        "tag": this.tag
      }
      console.log(knowledge);
      this.$axios.post('/knowledge/create', knowledge).then(response => {
        if (response.data.success) {
          showMessage();
          this.title = "";
          this.content = "";
        }
      })
    },
  },
  watch: {
    appType: function (newType) {
      switch (newType) {
        case "EnglishWordBook":
          this.tag = ["英语单词本"];
          break;
        case "LeetCodeNote":
          this.tag = ["力扣题解"];
          break;
        default:
          console.warn("未定义的类型", newType)
      }
    }
  }
}

function showMessage() {
  const alert = $('#submitAlert')
  alert.addClass("show").css("display", "block");
  setTimeout(() => alert.removeClass("show").css("display", "none"), 1500);
}
</script>

<style scoped>

</style>