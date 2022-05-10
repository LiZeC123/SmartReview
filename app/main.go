package main

import (
	"github.com/LiZeC123/SmartReview/app/kb"
	"github.com/LiZeC123/SmartReview/app/user"
	"log"
	"net/http"
	"os"

	_ "github.com/CodyGuo/godaemon"
	"github.com/gin-gonic/gin"
)

func initLog() {
	gin.DisableConsoleColor()
	f, _ := os.Create("data/gin.log")
	gin.DefaultWriter = f
	log.SetOutput(f)
}

func appServer() {

	GinMode := os.Getenv("GIN_MODE")
	if GinMode == "release" {
		initLog()
	}

	r := gin.Default()
	_ = r.SetTrustedProxies(nil)

	u := r.Group("/api/user")
	{
		u.POST("/login", Login)
		u.GET("/getCurrentUserName", GetCurrentUserName)
	}

	k := r.Group("/api/knowledge")
	{
		k.Use(user.Auth())
		k.GET("/migrate", Migrate)
		k.GET("/queryRecentReview", QueryRecentReview)
		//knowledge.GET("/generateWordMarkdown", GenerateWordMarkdown)
	}

	q := r.Group("/api/quote")
	{
		q.Use()
		q.POST("/createQuote", CreateQuote)
		q.POST("/createTask", CreateTask)

		q.GET("/queryCounts", QueryCounts)
		q.GET("/queryQuotes", QueryQuotes)
		q.GET("/queryTasks", QueryTasks)

		q.POST("/consumeQuote", ConsumeQuote)
		q.POST("/finishTask", FinishTask)

		q.POST("/deleteQuote", DeleteQuote)
		q.POST("/deleteTask", DeleteTask)
	}

	_ = r.Run("localhost:8792")
}

func Login(c *gin.Context) {
	var loginInfo user.LoginInfo
	if err := c.ShouldBindJSON(&loginInfo); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})

	}

	token, err := user.Login(loginInfo)
	if err != nil {
		c.JSON(http.StatusForbidden, gin.H{"success": false})
	} else {
		c.JSON(http.StatusOK, gin.H{"success": true, "token": token})
	}
}

func GetCurrentUserName(c *gin.Context) {
	header := user.TokenHeader{}
	if err := c.ShouldBindHeader(&header); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	u, err := user.CheckToken(header.Token)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"username": u.UserName})
}

func Migrate(c *gin.Context) {
	kb.Migrate()
	c.String(http.StatusOK, "Accepted.")
}

func QueryRecentReview(c *gin.Context) {
	c.JSON(http.StatusOK, kb.QueryRecentReview())
}

func main() {
	appServer()
}
