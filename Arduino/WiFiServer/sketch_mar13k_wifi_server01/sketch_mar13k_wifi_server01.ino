#include <WiFi.h>
#include <ESPAsyncWebServer.h>

extern const char* ssid = "MoCon"; // CHANGE IT
extern const char* password = "MoConLab1111"; // CHANGE IT

float getTemperature() {
  // YOUR SENSOR IMPLEMENTATION HERE
  // simulate the temperature value
  float temp_x100 = random(0, 10000);  // a ramdom value from 0 to 10000
  return temp_x100 / 100;              // return the simulated temperature value from 0 to 100 in float
}
AsyncWebServer server(80);





void setup() {
  Serial.begin(115200);

  // Connect to Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi...");
  }
  Serial.println("Connected to WiFi");

  // Print the ESP32's IP address
  Serial.print("ESP32 Web Server's IP address: ");
  Serial.println(WiFi.localIP());

  // Define a route to serve the HTML page
  server.on("/", HTTP_GET, [](AsyncWebServerRequest* request) {
    Serial.println("ESP32 Web Server: New request received:");  // for debugging
    Serial.println("GET /");        // for debugging
    request->send(200, "text/html", "<html><body><h1>Hello, ESP32!</h1></body></html>");
  });

  // Start the server
  server.begin();
}

void loop() {
 WiFiClient client = server.available();   // Listen for incoming clients

  if (client) {                             // If a new client connects,
    currentTime = millis();
    previousTime = currentTime;
    Serial.println("New Client.");          // print a message out in the serial port
    String currentLine = "";                // make a String to hold incoming data from the client
    while (client.connected()) {  // loop while the client's connected
      currentTime = millis();
      if (client.available()) {             // if there's bytes to read from the client,
        char c = client.read();             // read a byte, then
        Serial.write(c);                    // print it out the serial monitor
        header += c;
        if (c == '\n') {                    // if the byte is a newline character
          // if the current line is blank, you got two newline characters in a row.
          // that's the end of the client HTTP request, so send a response:
             
          if (currentLine.length() == 0) {
            // HTTP headers always start with a response code (e.g. HTTP/1.1 200 OK)
            // and a content-type so the client knows what's coming, then a blank line:
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println("Connection: close");
            client.println();
            
            // HTML 본문 전송 (제목 추가됨)
            client.println("<!DOCTYPE html>");   //HTML 문서 시작할때 <!DOCTYPE html>로 시작  //웹 브라우저가 최신 HTML5 기준으로 렌더링하도록 지시
            client.println("<html lang='ko'>");  //HTML 문서의 시작을 알림  //lang='ko' → 문서의 기본 언어를 한국어(ko)로 설정
            String html = "<!DOCTYPE HTML>";
            html += "<html lang='ko'>"
            html += "<html>";
            html += "<head>";
            html += "<link rel=\"icon\" href=\"data:,\">";
            html += "</head>";
            html += "<p>";
            html += "Temperature: <span style=\"color: red;\">";
            html += temperature;
            html += "&deg;C</span>";
            html += "</p>";
            html += "</html>";

            request->send(200, "text/html", html);


            client.println("</body></html>");
            
            // The HTTP response ends with another blank line
            client.println();
            // Break out of the while loop
            break;
          } else { // if you got a newline, then clear currentLine
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }
      }
    }
    // Clear the header variable
    header = "";
    // Close the connection
    
    // client.stop();
    // Serial.println("Client disconnected.");
    // Serial.println("");
  }
}

