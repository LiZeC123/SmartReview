<template>
  <div>
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

        <component :is="currentComp" :reset="reset" :submit="submit" @on-submit="doSumbit"></component>

      </div>
    </div>


    <button type="submit" class="btn btn-primary float-end me-4" @click="sendSubmit">添加知识点</button>
  </div>
</template>

<script>
import BaseKnowledge from "../../components/BaseKnowledge";
import EnglishWordBook from "../../components/EnglishWordBook";
import LeetCodeNote from "../../components/LeetCodeNote";
import $ from "jquery";

export default {
  name: "CreateKnowledgePage",
  components: {BaseKnowledge, EnglishWordBook, LeetCodeNote},
  data: function () {
    return {
      showMessage: "知识点已进入自动复习队列",
      currentId: 0,
      types: [{"id": 0, "comp": EnglishWordBook, "name": "单词本"}, {"id": 1, "comp": BaseKnowledge, "name": "基本类型"}],
      reset: 0,
      submit: 0,
    }
  },
  methods: {
    sendSubmit: function () {
      this.submit += 1;
    },
    doSumbit: function (extend) {
      let knowledge;
      if (this.currentId === 0) {
        knowledge = this.createEnglishWordBook(extend);
      } else if (this.currentId === 1) {
        knowledge = this.createBaseKnowledge(extend);
      } else if (this.currentId === 2) {
        knowledge = this.submitLeetCodeNote(extend);
      }

      console.log("Knowledge is",  knowledge);

      this.axios.post('/knowledge/create', knowledge).then(response => {
        if (response.data) {
          console.log("Response Data is", response.data)
          this.reset += 1
          this.show()
        }
      })
    },
    createBaseKnowledge: function (extend) {
      return {
        "appType": this.currentId,
        "title": extend.title,
        "content": extend.content,
      }
    },
    createEnglishWordBook: function (extend) {
      return {
        "appType": this.currentId,
        "title": "",
        "content": extend.content,
      }
    },
    submitLeetCodeNote: function (extend) {
      return {"extend": extend}
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