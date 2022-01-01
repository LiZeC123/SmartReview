package main

import (
	"github.com/gin-gonic/gin"
	"gorm.io/gorm"
	"net/http"
	"strconv"
)

type Quote struct {
	gorm.Model
	UID   uint
	Name  string
	Init  float64
	Used  float64
	T     float64
	Value float64
	Next  float64
}

func init() {
	err := db.AutoMigrate(&Quote{})
	if err != nil {
		panic("Quote表自动迁移失败")
	}
}

func CreateQuote(c *gin.Context) {
	user, err := GetUser(c)
	if err != nil {
		c.String(http.StatusOK, "请登录后重试")
		return
	}

	name, ok := c.GetQuery("name")
	if !ok {
		c.String(http.StatusOK, "请指令配额名称")
		return
	}

	T, ok := c.GetQuery("T")
	if !ok {
		c.String(http.StatusOK, "请指令配额周期")
		return
	}

	floatT, err := strconv.ParseFloat(T, 64)
	if err != nil {
		c.String(http.StatusOK, "请指定浮点类型的周期值")
		return
	}

	quote := Quote{UID: user.ID, Name: name, T: floatT}
	db.Create(&quote)

	c.JSON(http.StatusOK, quote)
}

func ListQuote(c *gin.Context) {

}

func UseQuote(c *gin.Context) {

}

func DelQuote(c *gin.Context) {

}

func GetQuoteByUser(UID uint) {

	var quotes []Quote

	db.Where("UID = ?", UID).Find(&quotes)
}
