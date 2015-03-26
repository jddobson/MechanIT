#include <Wire.h>
#include <OBD.h>
#include <SoftwareSerial.h>   //Software Serial Port
#define RxD A2
#define TxD A3
 
SoftwareSerial blueSerial(RxD,TxD);
 
void setup() 
{ 
  Serial.begin(9600);
  pinMode(RxD, INPUT);
  pinMode(TxD, OUTPUT);
  blueConnect(); 
} 
 
void loop() 
{ 
  int bytes = blueSerial.available();
  int cnt = 0;
  char buffer[32];
  while (0 < bytes <= 32) {
    cnt = blueSerial.readBytes(buffer, bytes);
    while (cnt > 0) {
      cnt--;
      blueSerial.print(buffer[cnt]);
    }
  }
} 
 
void blueConnect()
{
  blueSerial.begin(38400); //Set BluetoothBee BaudRate to default baud rate 38400
  blueSerial.print("\r\n+STWMOD=0\r\n"); //set the bluetooth work in slave mode
  blueSerial.print("\r\n+STNA=btDevice\r\n"); //set the bluetooth name as "btDevice"
  blueSerial.print("\r\n+STPIN=0000\r\n");//Set SLAVE pincode"0000"
  blueSerial.print("\r\n+STOAUT=1\r\n"); // Permit Paired device to connect me
  blueSerial.print("\r\n+STAUTO=0\r\n"); // Auto-connection should be forbidden here
  delay(2000); // This delay is required.
  blueSerial.print("\r\n+INQ=1\r\n"); //make the slave bluetooth inquirable 
  Serial.println("The slave bluetooth is inquirable!");
  delay(2000); // This delay is required.
  blueSerial.flush();
}




