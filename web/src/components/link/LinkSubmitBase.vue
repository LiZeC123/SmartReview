<template>
  <div class="mb-3">
    <div class="row g-2">
      <div class="col">
        <div class="form-floating">
          <input type="text" class="form-control" id="txtLinkName" aria-describedby="txtLinkNameHelp"
                 placeholder="资源名称" v-model="newLink.name" @keydown.enter="createLink">
          <small id="txtLinkNameHelp" class="form-text text-muted">与此知识点关联的资源链接</small>
          <label for="txtLinkName">资源名称</label>
        </div>
      </div>
      <div class="col">
        <div class="form-floating">
          <input type="text" class="form-control" id="txtLinkUrl" aria-describedby="txtLinkUrlHelp"
                 placeholder="资源链接" v-model="newLink.url" @keydown.enter="createLink">
          <small id="txtLinkUrlHelp" class="form-text text-muted">与此知识点关联的资源链接</small>
          <label for="txtLinkUrl">资源链接</label>
        </div>
      </div>
    </div>

    <!--    放置一个空按钮, 从而阻止其他按钮相应enter键  -->
    <button style="display: none"></button>


    <table class="table" v-if="commitLinks.length !== 0 || builtinLinks.length !== 0">
      <thead>
      <tr>
        <th>资源名称</th>
        <th>资源链接</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>

      <!-- 渲染内置的链接 -->
      <tr v-for="link in builtinLinks" v-bind:key="link.name">
        <td>{{ link.name }}</td>
        <td><a :href="link.url" target="_blank">{{ link.url }}</a></td>
        <td :class="'text-center'">
          <button class="btn btn-secondary disabled">删除</button>
        </td>
      </tr>

      <!-- 渲染用户提交的链接 -->
      <tr v-for="(link,index) in commitLinks" v-bind:key="link.name">
        <td>{{ link.name }}</td>
        <td><a :href="link.url" target="_blank">{{ link.url }}</a></td>
        <td :class="'text-center'">
          <button class="btn btn-danger" @click="deleteLink(index)">删除</button>
        </td>
      </tr>

      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "LinkSubmitBase",
  props: {
    builtinLinks: Array
  },
  data: function () {
    return {
      newLink: {
        name: "",
        url: ""
      },
      commitLinks: [],
    };
  },
  methods: {
    createLink: function () {
      this.commitLinks.push(this.newLink);
      this.newLink = {name: "", url: ""};
      this.commit();
    },
    deleteLink: function (index) {
      this.commitLinks.splice(index, 1);
      this.commit();
    },
    commit: function () {
      let links = [];

      for(let link of this.builtinLinks) {
        links.push(link);
      }

      for(let link of this.commitLinks) {
        links.push(link);
      }

      this.$emit('link-change', links);
    }
  },
  watch: {
    'builtinLinks': function (newValue) {
      if (newValue.length === 0) {
        this.commitLinks = [];
        this.newLink = {name: "", url: ""};
      }

      this.commit();
    }
  }
}
</script>

<style scoped>

</style>