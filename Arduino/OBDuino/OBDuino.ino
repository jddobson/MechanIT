#include <OBD.h>
#include <Wire.h>
#include <SoftwareSerial.h>   //Software Serial Port

#define RxD A2
#define TxD A3

static const byte SYNC = 0;
static const byte CDTC = 1;
static const byte RDTC = 2;

COBD obd;

SoftwareSerial blueSerial(RxD,TxD);

static byte pidArray[]= {PID_RPM};

int sleep = 0;

void setup() 
{ 
  Serial.begin(38400);
  Serial.write("ATZ\r");
  delay(500);
  while (Serial.available()) Serial.read();
  Serial.write("ATE0\r");
  delay(100);
  while (Serial.available()) Serial.read();
  pinMode(RxD, INPUT);
  pinMode(TxD, OUTPUT);
} 

void loop() 
{
  //int pwr = obd.getVoltage();
  //if (pwr > 0) {
    //obd.wakeup();
    //sleep = 0;
    blueConnect();
    //if (obd.isValidPID(pidArray[0])){
    Serial.write("0131\r");
    while (Serial.available()) {
      char buf[32]; 
      byte rcv = Serial.readBytesUntil('\r', buf, sizeof(buf));
      if ((buf[rcv-11] == '1') && (buf[rcv-9] == '3') && (buf[rcv-8] == '1')) {
        blueSerial.write(buf, rcv);
        break;
      }
    }
    delay(3000);
    //} else {blueSerial.print('x');}
    //if (blueSerial.available()) {
    //  recCMD();
    //}else {
    //  int i = 0;
    //  while (sizeof(pidArray) > i) {
    //    getCode(pidArray[i]);
    //    i++;}
    //}
    blueSerial.end();
    //}else if (sleep == 0) {
    //sleep = 1;
    //obd.sleep();}
}
  
void blueConnect()
{
  blueSerial.begin(38400); //Set Bluetooth BaudRate to default baud rate 38400
  blueSerial.flush();
}

void recCMD()
{ 
  byte newByte;
  
  if (blueSerial.available() > 0) {
    newByte = blueSerial.read();
    if (newByte == CDTC) {
      obd.clearDTC();
    } else if (newByte == RDTC) {
      delay(100);
    }
  }
}

void getCode(byte pid)
{
  int value;
  
  if (obd.read(pid, value)) {
    blueSerial.print(value);
  }
}
