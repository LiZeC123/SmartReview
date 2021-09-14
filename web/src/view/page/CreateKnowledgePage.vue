<template>
  <div>
    <!--    <knowledge-form :do-submit="doSubmit" :show-message="showMessage" :do-show="doShow"-->
    <!--                    @submit="createKnowledge" @after-show="afterShow"></knowledge-form>-->


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
            <label class="form-check-label" :for="t.comp">{{ t.name }}</label>
          </div>
        </div>

        <hr/>

        <component :is="currentComp" :reset="reset" :submit="submit" @on-submit="updateExtend"></component>

        <hr/>

        <knowledge-link :reset="reset" :submit="submit" @on-submit="updateLink"></knowledge-link>

        <hr/>

        <knowledge-tag :reset="reset" :submit="submit" @on-submit="updateTag"></knowledge-tag>

      </div>
    </div>


    <button type="submit" class="btn btn-primary float-end me-4" @click="sendSubmit">创建知识点</button>
  </div>
</template>

<script>
import EnglishWordBook from "../../components/EnglishWordBook";
import LeetCodeNote from "../../components/LeetCodeNote";
import KnowledgeLink from "../../components/KnowledgeLink";
import KnowledgeTag from "../../components/KnowledgeTag";
import $ from "jquery";

export default {
  name: "CreateKnowledgePage",
  components: {EnglishWordBook, LeetCodeNote, KnowledgeLink, KnowledgeTag},
  data: function () {
    return {
      showMessage: "知识点已进入自动复习队列",
      currentId: 0,
      types: [],
      links: [],
      tags: [],
      extend: {},
      reset: 0,
      submit: 0,
      ss: {
        link: false,
        tag: false,
        extend: false
      }
    }
  },
  methods: {
    updateLink: function (links) {
      this.links = links
      this.ss.link = true
      this.doSumbit()
    },
    updateTag: function (tags) {
      this.tags = tags
      this.ss.tag = true
      this.doSumbit()
    },
    updateExtend: function (extend) {
      this.extend = extend
      this.ss.extend = true
      this.doSumbit()
    },
    resetSS: function () {
      this.ss.link = false;
      this.ss.tag = false;
      this.ss.extend = false;
    },
    sendSubmit: function () {
      this.resetSS()
      this.submit += 1;
    },
    doSumbit: function () {
      let done = this.ss.link && this.ss.tag && this.ss.extend;
      if (!done) {
        console.log("Not Done Return.")
        return;
      }

      let knowledge;
      if (this.currentId === 1) {
        knowledge = this.createEnglishWordBook();
      } else if (this.currentId === 2) {
        knowledge = this.submitLeetCodeNote();
      }

      console.log(knowledge);

      this.axios.post('/knowledge/create', knowledge).then(response => {
        if (response.data.success) {
          this.reset += 1
          this.show()
        }
      })
    },
    createEnglishWordBook: function () {
      return {
        "appType": this.currentId,
        "link": this.links,
        "tag": this.tags,
        "words": this.extend
      }
    },
    submitLeetCodeNote: function () {
      return {}
    },
    show: function () {
      const alert = $('#submitAlert')
      alert.addClass("show").css("display", "block");
      setTimeout(() => alert.removeClass("show").css("display", "none"), 1500);
      //this.resetForm();
    },
  },
  computed: {
    currentComp: function () {
      if (this.types[this.currentId] !== undefined) {
        return this.types[this.currentId].comp
      } else {
        console.log("Return Default Comp.")
        return "EnglishWordBook"
      }
    },
  },
  created() {
    this.axios.get('/appType/getAllTypes').then(response => {
      this.types = response.data.data;
      this.currentId = 1;
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