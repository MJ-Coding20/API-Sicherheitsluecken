# Requirements:

- JDK 21
- Docker and Docker-Compose
- IntelliJ - (you can omit IntelliJ if you use Docker and Docker-compose manually)
- Python

# How to start the examples:

- start dockerd: dockerd
- Run the respective docker container by using the added IntelliJ-configurations

# How to run the PoCs:

## 1 - Unrestricted Resource Consumption - CWE-770

- Start API-Example1
- Test normal API functionality in browser: localhost:8001/api/fibonacci?index=5 
  - you can choose the index freely
  - The higher the index, the longer the api will take to answer
- Run the attack: run the script FibonacciDoS.py
  - the script will send asynchronous calls to the api resulting in DoS
  - result: if you retry your initial test in the browser, you will see the server won't answer like before
  - explanation: the server is now so busy with all those requests made by the script,
  there are no resources left for the additional call in your browser :(

## 2 - Broken Authentication - CWE-307

- Start API-Example2
- Analyse normal API functionality in browser: localhost:8002/login
  - input some random values to see the functionality of the login page
  - in the developer tools see that api-endpoint POST /api/login ist called
- Run the attack: run the script BruteForcePassword.py
  - the script will send multiple post-calls with different values for password
  - parameters for the brute force are configured with the variables min_length, max_length and charset
  - result: the script will output the brute-forced password in the end
  - explanation: because there are now restrictions how often you can ask the login-api if the credentials are correct,
  you can just guess every possible combination until you find the right one

## 3 - Broken Object Level Authorization - CWE 639

- Start API-Example3
- Analyse normal API functionality in browser: localhost:8003/profile
  - get your current profile by clicking on the button "Get profile"
  - in the developer tools in tab network you can see the api-endpoint GET /api/profile is called
- Run the attack: 
  - copy the api call to a new tab, then you can see the json result directly
  - change the key in the api-call manually to some other values
  - result: you can now get info about other profiles
  - explanation: because there is no authorisation check on the user controlled key "id", you can see other users information

## 4 - SSRF - CWE-918
Prerequisites:
you know from inside information, that the following url contains valuable information: http://192.168.4.4/passwordBackup.txt
However your attempts to open the file in your browser results in the answer "403 forbidden"
It seems like you are missing the authorization to view it, but you still want to see it, luckily for you there is a way...

- Start Compose up-Example4
- Analyse normal API functionality in browser: http://192.168.4.2:8080/catsite
  - open developer tools and tab network (if empty reload webpage now)
  - see, that the images are loaded via api-endpoint GET http://192.168.4.2:8080/api/picture?url=http://192.168.4.3/1.jpg
- Run the attack:
  - copy the image-loading endpoint in new tab: http://192.168.4.2:8080/api/picture?url=http://192.168.4.3/1.jpg
  - replace the content of GET-Parameter "url" with targeted system: http://192.168.4.2:8080/api/picture?url=http://192.168.4.4/passwordBackup.txt
  - result: you can now see the content of the file
  - explanation: even though you are not allowed to access the resource, the webserver 192.168.4.2 is;
  by manipulation the server via the url-parameter to get the resource for you can bypass the authorization

## 5 - Unsafe Consumption of APIs - CWE-319
Prequisites:
you need some network surveillance program - I used wireshark

- Start Compose up-Example5
- Due to the artificiality of the exercise some first steps must be made:
  - Open site: https://192.168.5.2/
  - accept the self signed certificate
- Analyse normal API functionality in browser: https://192.168.5.2/rechnung
  - by pressing the button 'Rechnung senden' you initiate the api-call
  - see in developer tools and tab network how the call is sent
- Run the attack:
  - Start you network surveillance tool
    - I recommend wireshark
    - Select the correct bridge

