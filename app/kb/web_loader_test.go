package kb

import (
	"fmt"
	"testing"
)

func TestHelloName(t *testing.T) {
	url := "https://lizec.top/note/%E5%8D%95%E8%AF%8D%E6%9C%AC.html"

	contents, err := LoadWebRawFile(url)

	if err != nil {
		t.Fatalf("Error when LoadWebRawFile for url %s\n%v", url, err)
	}

	fmt.Println("解析后内容如下:")
	for _, line := range contents {
		fmt.Println(line)
	}
}

// TestHelloEmpty calls greetings.Hello with an empty string,
// checking for an error.
//func TestHelloEmpty(t *testing.T) {
//	msg, err := Hello("")
//	if msg != "" || err == nil {
//		t.Fatalf(`Hello("") = %q, %v, want "", error`, msg, err)
//	}
//}
