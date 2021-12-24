package main

import (
	"github.com/gin-gonic/gin"
	"net/http"
)

func QueryRecentReview(c *gin.Context) {
	empty := make([]string, 0)
	c.JSON(http.StatusOK, empty)
}
