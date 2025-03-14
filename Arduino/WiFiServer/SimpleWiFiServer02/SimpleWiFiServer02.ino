/*
 WiFi Web Server LED,MOTOR,DISPLAY 프로토콜 
 HTML 
 ssid - MoCon
 password - MoConLab1111

 프로토콜 
 버퍼 사이즈 : 미정
 0번지 : 0xAA (start)
 1,2,3 : LED1 RGB
 4,5,6 : LED2 RGB
 7,8,9 : LED3 RGB
 10,11,12 : LED4 RGB
 13,14,15 : LED5 RGB
 16,17,18 : LED6 RGB
 19,20,21 : LED7 RGB
 22,23,24 : LED8 RGB
 25,26,27 : LED9 RGB
 28,29,30 : LED10 RGB

 31번지 motor / 0x00 정지 0x01 동작
 32번지 display / 0x00정지 0x01 동작
 끝번지 : 0xED (end)

 */

#include <WiFi.h>
#include <EEPROM.h>

#define EEPROM_SIZE 64
//#define int value=0


const char *ssid = "MoCon";
const char *password = "MoConLab1111";
//String HTTP_METHOD = "GET"; // 또는 "POST"
String PATH_NAME   = "";

WiFiServer server(80);  // 포트 80에서 웹 서버 실행

void setup() {
  Serial.begin(115200);
  EEPROM.begin(EEPROM_SIZE);

  int value = EEPROM.read(0);
  delay(10);

  // We start by connecting to a WiFi network

  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  EEPROM.write(0, 42);
  // int value = 1234;
  EEPROM.put(10, value);
  EEPROM.commit();


  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("");
  Serial.println("WiFi connected.");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  server.begin();
}

void loop() {
  NetworkClient client = server.accept();  // 클라이언트가 연결되어있는지 확인

  if (client) {                     // 클라이언트가 연결됐다면
    Serial.println("New Client.");
  
      // new client라고 프린트
    String currentLine = "";        // 클라이언트로부터 들어오는 데이터를 저장할 문자열(String) 생성
    while (client.connected()) {    // 클라이언트가 연결 되어있는동안
      if (client.available()) {     // 클라이언트로 부터 읽을수 있는 데이터가 있다면
        char c = client.read();     // 문자열을 읽는다.
        Serial.write(c);            // 시리얼 모니터에 그 문자열을 프린트
        if (c == '\n') {            // 그 문자열이 개행문자라면

          //만약 현재 줄이 비어있다면 연속으로 두개의 개행문자를 받은것
          //이것은 클라이언트의 HTTP요청이 끝났다는의미, 따라서 HTTP응답을 보내야한다
          if (currentLine.length() == 0) {
            // HTTP 헤더 전송
            //client.println(HTTP_METHOD + " " + PATH_NAME + " HTTP/1.1");
            client.println("HTTP/1.1 200 OK");
            client.println("Content-type:text/html");
            client.println();
           

            // HTML 본문 전송 (제목 추가됨)
            client.println("<!DOCTYPE html>");   //HTML 문서 시작할때 <!DOCTYPE html>로 시작  //웹 브라우저가 최신 HTML5 기준으로 렌더링하도록 지시
            client.println("<html lang='ko'>");  //HTML 문서의 시작을 알림  //lang='ko' → 문서의 기본 언어를 한국어(ko)로 설정


            //제목
            client.println("<head>");                    //웹 브라우저에는 직접 표시되지 않지만, 문서 제목, 인코딩, 스타일 등을 설정
            client.println("<meta charset='UTF-8'>");    //문서의 문자 인코딩을 UTF-8로 설정 UTF-8은 한글, 영어, 일본어 등 모든 언어를 지원하는 표준 인코딩 방식
            client.println("<title>MoCon Web</title>");  // 🔹 HTML 제목 추가
            client.println("</head>");                   // head 영역의 설정이 끝남

            // 전체적인 스타일 적용 (폰트 크기 확대, 입력 칸 및 버튼 크기 증가)
            client.println("<style>");
            client.println("body { font-size: 70px; text-align: left; }");                                                   // 전체 글자 크기 확대
            client.println("h1 { font-size: 80px; text-align: center; }");                                                   // MoCon Web 제목만 가운데 정렬
            client.println("p { font-size: 30px; }");                                                                        // 단락 텍스트 크기 증가
            client.println("button { width: 170px; height: 80px; font-size: 40px; margin: 10px; cursor: pointer;  }");       // 버튼 크기 증가
            client.println(".active { background-color: #4CAF50; color: white; }");                                          // 활성화(ON) 상태 스타일
            client.println("table { width: 100%; max-width: 1200px; margin: auto; border-collapse: collapse; }");            // 테이블 크기 조정 및 테두리 맞춤
            client.println("td { width: 130px; height: 70px; text-align: center; vertical-align: middle; padding: 5px; }");  // 셀 크기를 input에 맞춤
            client.println("input { width: 120px; height: 60px; font-size: 30px; text-align: left; }");                      // 입력 칸 크기 증가

            client.println("</style>");



            client.println("<script>");

            // 각 시나리오 선택 버튼에 대해 개별적인 푸시락(Push Lock) 기능을 설정
             client.println("function toggleScenario(btn, label) {");
             client.println("  if (btn.classList.contains('active')) {");
             client.println("    btn.classList.remove('active');");
             client.println("    btn.innerHTML = label;");
             client.println("  } else {");
             client.println("    btn.classList.add('active');");
             client.println("    btn.innerHTML = label + '됨';");  // 상태 변경
             client.println("  }");
             client.println("}");




// Placeholder for the action functions that are called
            // const int scenarioCount2 = 5; 

            //   for (int i = 1; i <= scenarioCount2; i++) {
            //   client.println("function selected(i) {");
  
            //   client.println("}"

            //   client.println("function saved(i) {");
            //   client.println("}");

            //   client.println("function send(i) {");
            //   client.println("}");

            //   client.println("function delete(i) {");
            //   client.println("}");
            //   }
          
            // client.println("function toggleScenario1(btn) {");
            // client.println("  if (btn.classList.contains('active')) {");
            // client.println("    btn.classList.remove('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  } else {");
            // client.println("    btn.classList.add('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  }");
            // client.println("}");

            // client.println("function toggleScenario2(btn) {");
            // client.println("  if (btn.classList.contains('active')) {");
            // client.println("    btn.classList.remove('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  } else {");
            // client.println("    btn.classList.add('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  }");
            // client.println("}");

            // client.println("function toggleScenario3(btn) {");
            // client.println("  if (btn.classList.contains('active')) {");
            // client.println("    btn.classList.remove('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  } else {");
            // client.println("    btn.classList.add('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  }");
            // client.println("}");

            // client.println("function toggleScenario4(btn) {");
            // client.println("  if (btn.classList.contains('active')) {");
            // client.println("    btn.classList.remove('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  } else {");
            // client.println("    btn.classList.add('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  }");
            // client.println("}");

            // client.println("function toggleScenario5(btn) {");
            // client.println("  if (btn.classList.contains('active')) {");
            // client.println("    btn.classList.remove('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  } else {");
            // client.println("    btn.classList.add('active');");
            // client.println("    btn.innerHTML = '선택';");
            // client.println("  }");
            // client.println("}");

            //hex로 변환
            client.println("function saveHEX(led) {");
            client.println("  let r = parseInt(document.getElementById('led' + led + '_R').value || 0).toString(16).toUpperCase().padStart(2, '0');");
            client.println("  let g = parseInt(document.getElementById('led' + led + '_G').value || 0).toString(16).toUpperCase().padStart(2, '0');");
            client.println("  let b = parseInt(document.getElementById('led' + led + '_B').value || 0).toString(16).toUpperCase().padStart(2, '0');");
            client.println("  let d = parseInt(document.getElementById('delay' + led).value || 0).toString(16).toUpperCase().padStart(2, '0');");
            client.println("  document.getElementById('hex' + led).innerText = 'R' + r + ' G' + g + ' B' + b + ' D' + d;");
            client.println("}");

            // LED별 전송 버튼 개별 설정
            /*
            client.println("function sendLED1() { alert('LED 1 값 전송'); }");
            client.println("function sendLED2() { alert('LED 2 값 전송'); }");
            client.println("function sendLED3() { alert('LED 3 값 전송'); }");
            client.println("function sendLED4() { alert('LED 4 값 전송'); }");
            client.println("function sendLED5() { alert('LED 5 값 전송'); }");
            client.println("function sendLED6() { alert('LED 6 값 전송'); }");
            client.println("function sendLED7() { alert('LED 7 값 전송'); }");
            client.println("function sendLED8() { alert('LED 8 값 전송'); }");
            client.println("function sendLED9() { alert('LED 9 값 전송'); }");
            client.println("function sendLED10() { alert('LED 10 값 전송'); }");
            */
            client.println("</script>");


            client.println("<body>");  //웹 브라우저 화면에 표시되는 실제 콘텐츠가 포함될 부분

         // 제목 (가운데 정렬)
            client.println("<h1><b>MoCon Web</b></h1>");                                      // 🔹 화면에 표시할 제목 추가 <h1> 태그 → 웹 페이지의 가장 큰 제목 <h2> > <h3>
            client.println("<p>시나라오 ([선택] 누를시 해당 시나리오에 정보 저장가능)</p>");  //<p> 태그 → 단락(paragraph) 태그
            const int scenarioCount2 = 5; 

              for (int i = 1; i <= scenarioCount2; i++) {
                  client.print("<div>");  
                  client.print("시나리오 " + String(i) + " ");
                  
                  client.print("<button onclick='toggleScenario(this, \"선택\"); selected(" + String(i) + ")'>선택</button> ");
                  client.print("<button onclick='toggleScenario(this, \"저장\"); saved" + String(i) + "()'>저장</button> ");
                  client.print("<button onclick='toggleScenario(this, \"전송\"); send" + String(i) + "()'>전송</button> ");
                  client.print("<button onclick='toggleScenario(this, \"삭제\"); delete" + String(i) + "()'>삭제</button> ");
                  
                  client.println("</div><br>");  
              }
            // client.println("시나리오1 <button onclick='toggleScenario1(this);selected()'>선택</button> <button onclick='toggleScenario6(this);saved1()'>저장</button>  <button onclick='toggleScenario11(this);send1()'>전송</button> <button onclick='toggleScenario16(this);delete1()'>삭제</button>");
            // client.println("시나리오2 <button onclick='toggleScenario2(this)'>선택</button> <button onclick='toggleScenario7(this);saved2()'>저장</button>  <button onclick='toggleScenario12(this);send2()'>전송</button> <button onclick='toggleScenario17(this);delete2()'>삭제</button>");
            // client.println("시나리오3 <button onclick='toggleScenario3(this)'>선택</button> <button onclick='toggleScenario8(this);saved3()'>저장</button>  <button onclick='toggleScenario13(this);send3()'>전송</button> <button onclick='toggleScenario18(this);delete3()'>삭제</button>");
            // client.println("시나리오4 <button onclick='toggleScenario4(this)'>선택</button> <button onclick='toggleScenario9(this);saved4()'>저장</button>  <button onclick='toggleScenario14(this);send4()'>전송</button> <button onclick='toggleScenario19(this);delete4()'>삭제</button>");
            // client.println("시나리오5 <button onclick='toggleScenario5(this)'>선택</button> <button onclick='toggleScenario10(this);saved5()'>저장</button>  <button onclick='toggleScenario15(this);send5()'>전송</button> <button onclick='toggleScenario20(this);delete5()'>삭제</button>");

            // client.println("<div>시나리오1 <button onclick='toggleScenario1(this)'>선택</button><button onclick='toggleScenario6(this);saved1()'>저장</button><button onclick='toggleScenario11(this);send1()'>전송</button><button16 onclick='toggleScenario16(this);delete1()'>삭제</button>"</div><br>);
            // client.println("<div>시나리오2 <button onclick='toggleScenario2(this)'>선택</button><button onclick='toggleScenario7(this);saved2()'>저장</button><button onclick='toggleScenario12(this);send2()'>전송</button><button onclick='toggleScenario17(this);delete2()'>삭제</button>"</div><br>);
            // client.println("<div>시나리오3 <button onclick='toggleScenario3(this)'>선택</button><button onclick='toggleScenario8(this);saved3()'>저장</button><button onclick='toggleScenario13(this);send3()'>전송</button><button onclick='toggleScenario18(this);delete3()'>삭제</button>"</div><br>);
            // client.println("<div>시나리오4 <button onclick='toggleScenario4(this)'>선택</button><button onclick='toggleScenario9(this);saved4()'>저장</button><button onclick='toggleScenario14(this);send4()'>전송</button><button onclick='toggleScenario19(this);delete4()'>삭제</button>"</div><br>);
            // client.println("<div>시나리오5 <button onclick='toggleScenario5(this)'>선택</button><button onclick='toggleScenario10(this);saved5()'>저장</button><button onclick='toggleScenario15(this);send5()'>전송</button><button onclick='toggleScenario20(this);delete5()'>삭제</button>"</div><br>);

            client.println("<br><br>");
            client.println("<p>LED (RGB 0~255, Dimming delay)</p>");  //<p> 태그 → 단락(paragraph) 태그
                                                       // LED 개별 설정
            // LED 개별 설정
            client.println("<p><b>LED 1 <button onclick='saveHEX(1)'>저장</button> <button onclick='DOLED1()'>실행</button> <span id='hex1'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led1_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led1_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led1_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay1' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 2 <button onclick='saveHEX(2)'>저장</button></b> <button onclick='DOLED2()'>실행</button><span id='hex2'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led2_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led2_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led2_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay2' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 3 <button onclick='saveHEX(3)'>저장</button></b> <button onclick='DOLED3()'>실행</button><span id='hex3'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led3_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led3_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led3_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay3' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 4 <button onclick='saveHEX(4)'>저장</button></b> <button onclick='DOLED4()'>실행</button><span id='hex4'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led4_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led4_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led4_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay4' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 5 <button onclick='saveHEX(5)'>저장</button></b> <button onclick='DOLED5()'>실행</button><span id='hex5'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led5_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led5_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led5_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay5' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 6 <button onclick='saveHEX(6)'>저장</button></b> <button onclick='DOLED6()'>실행</button><span id='hex6'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led6_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led6_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led6_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay6' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 7 <button onclick='saveHEX(7)'>저장</button></b> <button onclick='DOLED7()'>실행</button><span id='hex7'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led7_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led7_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led7_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay7' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 8 <button onclick='saveHEX(8)'>저장</button></b> <button onclick='DOLED8()'>실행</button><span id='hex8'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led8_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led8_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led8_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay8' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 9 <button onclick='saveHEX(9)'>저장</button></b> <button onclick='DOLED9()'>실행</button><span id='hex9'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led9_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led9_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led9_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay9' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 10 <button onclick='saveHEX(10)'>저장</button></b> <button onclick='DOLED10()'>실행</button><span id='hex10'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led10_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led10_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led10_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay10' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");

            client.println("<p><b>LED 11 <button onclick='saveHEX(11)'>저장</button></b> <button onclick='DOLED11()'>실행</button><span id='hex11'>R00 G00 B00 D00</span></b></p>");
            client.println("<table border='1'><tr>");
            client.println("<td><input id='led11_R' type='number' min='0' max='255' value='0' placeholder='R'></td>");
            client.println("<td><input id='led11_G' type='number' min='0' max='255' value='0' placeholder='G'></td>");
            client.println("<td><input id='led11_B' type='number' min='0' max='255' value='0' placeholder='B'></td>");
            client.println("<td><input id='delay11' type='number' min='0' max='5000' value='0' placeholder='D'></td>");
            client.println("</tr></table>");
            


            client.println("<br><br>");
            
            client.println("<p>Motor Stop Forward Backward </p>");  //<p> 태그 → 단락(paragraph) 태그

            client.println("<p><b>Motor1 <button onclick='motor1stop()'>정지</button> <button onclick='motor1+()'>정방향</button> <button onclick='motor1-()'>역방향</button>");
            client.println("<p><b><button onclick='Motor1_test_stop()'>T정지</button> <button onclick='Motor1_test_forward()'>T정방향</button> <button onclick='Motor1_test_backward()'>T역방향</button>");
            client.println("<br><br>");

            client.println("<p><b>Motor2 <button onclick='motor2stop()'>정지</button> <button onclick='motor2+()'>정방향</button> <button onclick='motor2-()'>역방향</button>");
            client.println("<p><b><button onclick='Motor2_test_stop()'>T정지</button> <button onclick='Motor2_test_forward()'>T정방향</button> <button onclick='Motor2_test_backward()'>T역방향</button>");
 

            client.println("<p><b>Motor3 <button onclick='motor3stop()'>정지</button> <button onclick='motor3+()'>정방향</button> <button onclick='motor3-()'>역방향</button>");
            client.println("<p><b><button onclick='Motor3_test_stop()'>T정지</button> <button onclick='Motor3_test_forward()'>T정방향</button> <button onclick='Motor3_test_backward()'>T역방향</button>");
   

            // JavaScript 추가 (나중에 서버로 값 전송 가능)
            client.println("<script>");
            client.println("function sendData() { alert('데이터가 전송되었습니다!'); }");
            client.println("</script>");

            client.println("</body>");
            client.println("</html>");

           // EEPROM.put(10, value);
            EEPROM.commit();
            Serial.print("saved  !!!!");

            break;
          } else {  // if you got a newline, then clear currentLine:
            currentLine = "";
          }
        } else if (c != '\r') {  // if you got anything else but a carriage return character,
          currentLine += c;      // add it to the end of the currentLine
        }

        // Check to see if the client request was "GET /H" or "GET /L":
        if (currentLine.endsWith("GET /Scenario1")) {
          Serial.print("Scenario1 select");
        }
        if (currentLine.endsWith("GET /send_S1")) {
          Serial.print("send Scenario data");
        }
        if (currentLine.endsWith("GET /delete_S1")) {
          Serial.print("delete Scenario data");
        }
      }
    }
    // close the connection:
    /*
    client.stop();
    Serial.println("Client Disconnected.");
    */
  }
}
