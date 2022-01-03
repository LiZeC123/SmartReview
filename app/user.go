package main

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"net/http"
)

type UserLogin struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}

type User struct {
	gorm.Model
	UserName string
	Email    string
	Password string
	Role     string
	Enable   bool
}

type TokenHeader struct {
	Token string `header:"Token"`
}

func init() {
	err := db.AutoMigrate(&User{})
	if err != nil {
		panic("User表自动迁移失败")
	}
}

func Login(c *gin.Context) {
	var login UserLogin
	if err := c.ShouldBindJSON(&login); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	var user User
	err := db.Where("email = ? and password = ?", login.Email, login.Password).First(&user).Error
	if err != nil {
		c.JSON(http.StatusForbidden, gin.H{"success": false})
		return
	}

	token, err := NewToken(user)
	if err != nil {
		panic("Token计算失败")
	}

	c.JSON(http.StatusOK, gin.H{"success": true, "token": token})
}

func GetCurrentUserName(c *gin.Context) {
	header := TokenHeader{}
	if err := c.ShouldBindHeader(&header); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	user, err := CheckToken(header.Token)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"username": user.UserName})
}

func GetUser(c *gin.Context) (*User, error) {
	header := TokenHeader{}
	if err := c.ShouldBindHeader(&header); err != nil {
		return nil, err
	}

	user, err := CheckToken(header.Token)
	if err != nil {
		return nil, err
	}

	return user, nil
}

func Auth() gin.HandlerFunc {
	return func(c *gin.Context) {
		token := c.Request.Header.Get("Token")
		_, err := CheckToken(token)
		if err != nil {
			c.JSON(http.StatusUnauthorized, gin.H{
				"stat": 1,
				"msg":  "禁止访问，请检查权限",
			})

			c.Abort()
			return
		}
		c.Next()
	}
}
