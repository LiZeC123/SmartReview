package user

import (
	"crypto/rand"
	"fmt"
	"time"

	"github.com/golang-jwt/jwt"
)

var hmacSampleSecret [256]byte

func init() {
	fmt.Println("Init Random Key")
	_, _ = rand.Read(hmacSampleSecret[:])
}

func NewToken(user User) (string, error) {
	maxAge := 60 * 60 * 24 * 14
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"id":   user.ID,
		"name": user.UserName,
		"exp":  time.Now().Add(time.Duration(maxAge) * time.Second).Unix(),
	})

	return token.SignedString(hmacSampleSecret[:])
}

func CheckToken(tokenString string) (*User, error) {
	token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
		if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
			return nil, fmt.Errorf("unexpected signing method: %v", token.Header["alg"])
		}

		return hmacSampleSecret[:], nil
	})

	if err != nil {
		return nil, err
	}

	if claims, ok := token.Claims.(jwt.MapClaims); ok && token.Valid {
		var user User
		user.ID = uint(claims["id"].(float64))
		user.UserName = claims["name"].(string)

		return &user, nil
	} else {
		return nil, err
	}
}
