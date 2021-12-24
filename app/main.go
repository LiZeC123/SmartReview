package main

import (
	"io"
	"net/http"
	"os"

	_ "github.com/CodyGuo/godaemon"
	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt"
)

func main() {
	// 全局配置需要在开始的时候执行
	gin.DisableConsoleColor()
	f, _ := os.Create("gin.log")
	gin.DefaultWriter = io.MultiWriter(f, os.Stdout)

	r := gin.Default()
	r.SetTrustedProxies(nil)

	user := r.Group("/api/user")
	{
		user.GET("/login", login)
	}

	r.Run("localhost:8792")
}

type UserLogin struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}

func login(c *gin.Context) {
	var login UserLogin
	if err := c.ShouldBindJSON(&login); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"success": true, "token": "<token>"})
}

