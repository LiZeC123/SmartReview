<template>
  <link-submit-base :builtin-links="updatedBuiltinLink"
                    @link-change="commit"> </link-submit-base>
</template>

<script>
import LinkSubmitBase from "@/components/link/LinkSubmitBase";

export default {
  components: {LinkSubmitBase},
  name: "LeetCodeNote",
  props:{
    title: String
  },
  computed:{
    updatedBuiltinLink: function() {
      return [];
    }
  },

  methods: {
    commit: function (links) {
      this.$emit('link-change', links);
    }
  },
}
</script>

<style scoped>

</style>