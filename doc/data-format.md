
1. Password is encrypted use AES/CBC/PKCS5Padding
2. There is a magic "PassKeeper" at the top of file
3. Each line is ended with `\r\n`

sample:
```
PassKeeper\r\n
item0\r\n
password0\r\n
item1\r\n
password1
```