package main

import (
	"github.com/LiZeC123/SmartReview/app/kb"
	"github.com/LiZeC123/SmartReview/app/user"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"log"
	"net/http"
	"os"

	_ "github.com/CodyGuo/godaemon"
	"github.com/gin-gonic/gin"
)

func isRelease() bool {
	return os.Getenv("GIN_MODE") == "release"
}

func initDatabase(release bool) {
	var config gorm.Config
	if !release {
		config.Logger = logger.Default.LogMode(logger.Info)
	}

	db, err := gorm.Open(sqlite.Open("data/SmartReview.db"), &config)

	if err != nil {
		panic("failed to connect database")
	}

	user.Init(db)
	kb.Init(db)
}

func initLog(release bool) {
	if release {
		gin.DisableConsoleColor()
		f, _ := os.Create("data/gin.log")
		gin.DefaultWriter = f
		log.SetOutput(f)
	}
}

func appServer() {
	release := isRelease()
	initDatabase(release)
	initLog(release)

	r := gin.Default()
	_ = r.SetTrustedProxies(nil)

	u := r.Group("/api/user")
	{
		u.POST("/login", login)
		u.GET("/getCurrentUserName", getCurrentUserName)
	}

	k := r.Group("/api/knowledge")
	{
		k.Use(user.Auth())
		k.GET("/migrate", migrate)
		k.GET("/queryRecentReview", queryRecentReview)
		k.POST("/finishReview", finishReview)
	}

	_ = r.Run("localhost:8792")
}

func login(c *gin.Context) {
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

func getCurrentUserName(c *gin.Context) {
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

func migrate(c *gin.Context) {
	kb.Migrate()
	c.String(http.StatusOK, "Accepted.")
}

func queryRecentReview(c *gin.Context) {
	c.JSON(http.StatusOK, kb.QueryRecentReview())
}

func finishReview(c *gin.Context) {
	r := kb.ReviewRequest{}
	if err := c.ShouldBindJSON(&r); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	kb.FinishReview(r)
	c.String(http.StatusOK, "Accepted.")
}

func main() {
	appServer()
}
