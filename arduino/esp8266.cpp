#include<ESP8266WiFi.h>
#include<FirebaseArduino.h>

#define FIREBASE_HOST"esp8266-7cd96-default-rtdb.firebaseio.com"
#define FIREBASE_AUTH"jCG4oe8wSWZSHbFYNgJL5RnWf58zVPSLS5PmBV0L"
#define WIFI_SSID"Con"
#define WIFI_PASSWORD"Chishuy123"
void setup(){
  Serial.begin(9600);
  WiFi.begin(WIFI_SSID,WIFI_PASSWORD);
  Serial.print("connecting to");
  Serial.print(WIFI_SSID);
  while (WiFi.status()!= WL_CONNECTED){
    Serial.print(".");
    delay(500);
    }
    Serial.println();
    Serial.print("Connected to ");
    Serial.println(WIFI_SSID);
    Serial.print("IP Address is: ");
    Serial.println(WiFi.localIP());
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    delay(1000);  
    pinMode(5, OUTPUT);  
    pinMode(4, INPUT_PULLUP);
   // handle error
   if (Firebase.failed()) {
     Serial.print("setting /number failed:");
     Serial.println(Firebase.error());  
     return;
 }
}
void relay (){
  digitalWrite(5, HIGH); // may bom
  Firebase.setString("State-water" , "đang bơm nước");
  delay(1800000);       
  digitalWrite(5, LOW);
  Firebase.setString("State-water" , "dừng bơm nước");    
  delay(1800000); 
}
void loop(){
  int state = digitalRead(4); // pin 4 sensor
  Serial.print("state : ");
  Serial.println(state);
  if (state == true){
     Firebase.setString("State-sensor" , "hết nước");
  }
  else if (state == false) {
     Firebase.setString("State-sensor" , "còn nước");
  }
  relay();
}