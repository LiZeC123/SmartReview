package main

import (
	"fmt"
	"io"
	"os"

	_ "github.com/CodyGuo/godaemon"
	"github.com/gin-gonic/gin"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

var db = initDatabase()

func initDatabase() *gorm.DB {
	db, err := gorm.Open(sqlite.Open("data/SmartReview.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}

	return db
}

func initLog() {
	gin.DisableConsoleColor()
	f, _ := os.Create("gin.log")
	gin.DefaultWriter = io.MultiWriter(f)
}

func appServer() {

	ff, _ := os.Create("tmp.log")
	GinMode := os.Getenv("GIN_MODE")
	fmt.Fprintf(ff, "GinMode=%s", GinMode)
	if GinMode == "release" {
		fmt.Fprintf(ff, "initLog")
		initLog()
	}

	r := gin.Default()
	// _ = r.SetTrustedProxies(nil)

	user := r.Group("/api/user")
	{
		user.POST("/login", Login)
		user.GET("/getCurrentUserName", GetCurrentUserName)
	}

	knowledge := r.Group("/api/knowledge")
	{
		knowledge.GET("/queryRecentReview", QueryRecentReview)
		knowledge.GET("/generateWordMarkdown", GenerateWordMarkdown)
		knowledge.GET("/migrate", Migrate)
	}

	_ = r.Run("localhost:8792")
}

func main() {
	appServer()
}
