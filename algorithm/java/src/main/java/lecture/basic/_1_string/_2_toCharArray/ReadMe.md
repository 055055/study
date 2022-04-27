#Problem

~~~
input: String s = "in(f(lea)r)n)";
output: "in(f(lea)r)n)"

input: s = "a)b(c)d"
output: "ab(c)d"

input: String s = "(a(b(c)d)";
output: "(a(bc)d)"

input: s = "))(("
output: ""

~~~

#Note
최소 괄호의 술르 제거하여 '(' ')'처럼 유효한 parentheses string 리턴
1. 빈 문자열이거나 소문자 만 포함


#문제 format

~~~
class Solution {
    public String solve(String str) {
    }
}

~~~
