<template>
  <div id="app" class="h-100">
    <nav class="navbar navbar-expand-md navbar-light bg-light fixed-top">
      <div class="container-fluid">
        <img alt="icon" class="me-2" src="../assets/logo.png" style="height: 38px">

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarsExampleDefault"
                aria-controls="navbarsExampleDefault" aria-expanded="false"
                aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse mx-2" id="navbarsExampleDefault">
          <form class="d-flex">
            <input class="form-control me-3" type="search" style="width: 243px"
                   placeholder="搜索知识库"
                   aria-label="Search">
          </form>
          <ul class="navbar-nav me-auto mb-2 mb-md-0">
            <li class="nav-item active">
              <router-link class="nav-link" to="/home/recent">最近复习</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/home/create">创建知识</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/home/knowledge">知识管理</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/home/tag">标签管理</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/home/export">导出数据</router-link>
            </li>
          </ul>

          <ul class="navbar-nav">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="dropdown01"
                 data-bs-toggle="dropdown"
                 aria-expanded="false">用户</a>
              <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="dropdown01">
                <li class="dropdown-item">当前用户: <strong>{{ username }}</strong></li>
                <li>
                  <hr class="dropdown-divider">
                </li>
                <li><a class="dropdown-item" href="#">用户设置</a></li>
                <li><a class="dropdown-item" href="#" @click="logout">退出登录</a></li>
              </ul>
            </li>
          </ul>

        </div>
      </div>
    </nav>

    <router-view></router-view>

  </div>
</template>

<script>
import router from "@/router";

export default {
  name: "Main",
  data: function () {
    return {
      username: "用户",
      role: "普通权限"
    }
  },
  methods: {
    logout: function () {
      localStorage.clear();
      this.$store.commit("del_token");
      router.push({path: '/login'}).then(()=>{});
    },
  },
  created() {
    this.$axios.get("user/getCurrentUserName").then(response => {
      if (response.data.success) {
        this.username = response.data.data
      }
    });
  }
}
</script>

<style scoped>

</style>