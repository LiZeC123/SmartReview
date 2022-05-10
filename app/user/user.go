package user

import (
	"errors"
	"github.com/LiZeC123/SmartReview/app/db"
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"net/http"
)

type LoginInfo struct {
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
	err := db.Db.AutoMigrate(&User{})
	if err != nil {
		panic("User表自动迁移失败")
	}
}

func Login(login LoginInfo) (string, error) {

	var user User
	err := db.Db.Where("email = ? and password = ?", login.Email, login.Password).First(&user).Error
	if err != nil {
		return "", errors.New("用户名或密码错误")
	}

	token, err := NewToken(user)
	if err != nil {
		panic("Token计算失败")
	}

	return token, nil
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
