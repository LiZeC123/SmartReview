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
          <label class="form-check-label" :for="t.comp">{{ t.name }}</label>
        </div>
      </div>

      <hr/>

      <component :is="currentComp" @on-submit="updateExtend"></component>

    </div>
  </div>
</template>

<script>
import LeetCodeNote from "./LeetCodeNote";
import $ from "jquery";
import EnglishWordBook from "./EnglishWordBook";

export default {
  name: "KnowledgeForm",
  components: {EnglishWordBook, LeetCodeNote},
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
      initLinks: [],
      reset: 0,
    }
  },
  methods: {
    updateExtend: function (extend) {
      this.extend = extend
    },
    resetForm: function () {
      this.title = "";
      this.content = "";
    }
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