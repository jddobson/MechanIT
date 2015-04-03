#include <Wire.h>
#include <SoftwareSerial.h>   //Software Serial Port

#define RxD A2
#define TxD A3

static const byte SYNC = 0;
static const byte CDTC = 1;
static const byte RDTC = 2;

SoftwareSerial blueSerial(RxD,TxD);

int sleep = 0;
byte newByte;
char buf[32];

void setup() { 
  Serial.begin(38400);
  Serial.write("ATZ\r"); //reset software
  delay(500);
  while (Serial.available()) Serial.read();
  Serial.write("ATE0\r"); // local echo turned off
  delay(100);
  while (Serial.available()) Serial.read();
  Serial.write("ATS0\r"); //turn off spaces
  delay(100);
  pinMode(RxD, INPUT);
  pinMode(TxD, OUTPUT);
} 

void loop() { 
  blueSerial.begin(38400); //Set Bluetooth BaudRate to default baud rate 38400
  //if (blueSerial.available() == 1) {
    //newByte = blueSerial.read();
    //if (newByte == SYNC) {
      int i = 0;
      while ((Serial.read() != '>') && (i < 50)) {
        i++;
      }
      Serial.write("0131\r");
      //while (Serial.available()) {
        byte rcv = Serial.readBytesUntil('\r', buf, sizeof(buf));
        //if ((buf[rcv-7] == '1') && (buf[rcv-6] == '3') && (buf[rcv-5] == '1')) {
          blueSerial.write(buf, rcv);
          delay(2000);
        //}
      //}
    //}else if (newByte == CDTC) {      
    //  Serial.write("04\r");
    //} else if (newByte == RDTC) {
    
    //  Serial.write("03\r");
      
      //byte rcv = Serial.readBytesUntil('\r', buf, sizeof(buf));
      //blueSerial.write(buf, rcv);
    //}
  //}
  blueSerial.flush();
  blueSerial.end();
}
