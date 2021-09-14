<template>
  <div class="appTypeGroup">
    <label>应用类型</label>
    <div class="form-check form-check-inline mx-2" v-for="t in types" :key="t.id">
      <input class="form-check-input" type="radio" :value="t.id" :id="t.comp"
             v-model="currentId">
      <label class="form-check-label" :for="t.comp">{{t.name}}</label>
    </div>
  </div>
</template>

<script>
export default {
  name: "KnowledgeType",
  props:{},
  data: function (){
    return {
      currentId: 0,
      types: [],
    }
  },
  created() {
    this.axios.get('/appType/getAllTypes').then(response => {
      this.types = response.data.data;
    });
  },
  methods: {

  },
  watch: {

  }
}
</script>

<style scoped>

</style>