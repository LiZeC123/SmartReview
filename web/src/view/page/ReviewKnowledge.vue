<template>
  <card-group :init-cards="initCard"></card-group>
</template>

<script>
import CardGroup from "../../components/CardGroup";

export default {
  name: "ReviewKnowledge",
  components: {CardGroup},
  methods: {
    initCard: function () {
      return this.axios.get('knowledge/queryList')
    },
  },
}
</script>

<style scoped>

</style>