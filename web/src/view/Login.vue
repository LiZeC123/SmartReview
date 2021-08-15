<template>
  <div class="my-login-page h-100">
    <div class="container d-flex align-items-center h-100">
      <div class="card-wrapper m-auto">
        <div class="brand m-auto mb-2">
          <img alt="logo" src="../assets/logo.png">
        </div>
        <div class="card fat">
          <div class="card-body">
            <h4 class="card-title">Login</h4>
            <form method="POST" class="my-login-validation" novalidate="" onsubmit="return false;">
              <div class="form-group">
                <label for="email">E-Mail Address</label>
                <input id="email" type="email" class="form-control" name="username" value=""
                       v-model="email" required autofocus>
                <div class="invalid-feedback">
                  Email is invalid
                </div>
              </div>

              <div class="form-group">
                <label for="password">Password
                  <a href="forgot.html" class="float-end">
                    Forgot Password?
                  </a>
                </label>
                <input id="password" type="password" class="form-control" name="password"
                       v-model="password" required data-eye>
                <div class="invalid-feedback">
                  Password is required
                </div>
              </div>

              <div class="form-group mt-4">
                <button class="btn btn-primary btn-block" @click="submit">
                  Login
                </button>
              </div>
              <div class="mt-4 text-center">
                Don't have an account? <a href="register.html">Create One</a>
              </div>
            </form>
            <div class="footer">
              Copyright &copy; 2021 &mdash; LiZeC
            </div>
          </div>

        </div>

      </div>


    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data: function () {
    return {
      email: "",
      password: ""
    };
  },
  methods: {
    submit: function () {
      this.$axios({
        method: 'post',
        url: "user/login",
        params: {
          "email": this.email,
          "password": this.password
        }
      }).then(response => {
        if (response.data.success) {
          const token = response.data.data;
          this.$store.commit('set_token', token);
          this.$router.push('/home/recent');
        }
      });
    }
  }
}
</script>

<style scoped>

.my-login-page {
  background-color: #f7f9fb;
  font-size: 14px;
}

.my-login-page .brand {
  width: 90px;
  height: 90px;
  overflow: hidden;
  border-radius: 50%;
  margin: 40px auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, .05);
  position: relative;
  z-index: 1;
}

.my-login-page .brand img {
  width: 100%;
}

.my-login-page .card-wrapper {
  width: 400px;
}

.my-login-page .card {
  border-color: transparent;
  box-shadow: 0 4px 8px rgba(0, 0, 0, .05);
}

.my-login-page .card.fat {
  padding: 10px;
}

.my-login-page .card .card-title {
  margin-bottom: 30px;
}

.my-login-page .form-control {
  border-width: 2.3px;
}

.my-login-page .form-group label {
  width: 100%;
  padding: 10px;
}

.my-login-page .btn.btn-block {
  width: 100%;
  padding: 12px 10px;
}

.my-login-page .footer {
  margin: 40px 0;
  color: #888;
  text-align: center;
}

@media screen and (max-width: 425px) {
  .my-login-page .card-wrapper {
    width: 90%;
    margin: 0 auto;
  }
}

@media screen and (max-width: 320px) {
  .my-login-page .card.fat {
    padding: 0;
  }

  .my-login-page .card.fat .card-body {
    padding: 15px;
  }
}
</style>