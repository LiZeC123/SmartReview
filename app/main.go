package main

import (
	user2 "github.com/LiZeC123/SmartReview/app/user"
	"log"
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

	user := r.Group("/api/user")
	{
		user.POST("/login", user2.Login)
		user.GET("/getCurrentUserName", user2.GetCurrentUserName)
	}

	knowledge := r.Group("/api/knowledge")
	{
		knowledge.Use(user2.Auth())
		knowledge.GET("/queryRecentReview", QueryRecentReview)
		knowledge.GET("/generateWordMarkdown", GenerateWordMarkdown)
		knowledge.GET("/migrate", Migrate)
	}

	quote := r.Group("/api/quote")
	{
		quote.Use(user2.Auth())
		quote.POST("/createQuote", CreateQuote)
		quote.POST("/createTask", CreateTask)

		quote.GET("/queryCounts", QueryCounts)
		quote.GET("/queryQuotes", QueryQuotes)
		quote.GET("/queryTasks", QueryTasks)

		quote.POST("/consumeQuote", ConsumeQuote)
		quote.POST("/finishTask", FinishTask)

		quote.POST("/deleteQuote", DeleteQuote)
		quote.POST("/deleteTask", DeleteTask)
	}

	_ = r.Run("localhost:8792")
}

func main() {
	appServer()
}
