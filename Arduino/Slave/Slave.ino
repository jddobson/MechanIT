#include <Wire.h>
#include <OBD.h>
#include <SoftwareSerial.h>   //Software Serial Port
#define RxD A2
#define TxD A3
COBD obd;

SoftwareSerial blueSerial(RxD,TxD);

static byte pidArray[]= {PID_DISTANCE};

static const byte CDTC = 1;
static const byte SLEEP = 2;

void setup() 
{ 
  obd.begin();
  obd.init();
  pinMode(RxD, INPUT);
  pinMode(TxD, OUTPUT);
} 

void loop() 
{
  blueConnect();
  if (blueSerial.isListening()) {
    if (blueSerial.available()) {
      recCMD();
    }else {
      int i = 0;
      while (sizeof(pidArray) > i) {
        getCode(pidArray[i]);
        i++;}
    }
  }
  blueSerial.end();
}
  
void blueConnect()
{
  blueSerial.begin(38400); //Set Bluetooth BaudRate to default baud rate 38400
  blueSerial.print("\r\n+STWMOD=0\r\n"); //set the bluetooth work in slave mode
  blueSerial.print("\r\n+STNA=btDevice\r\n"); //set the bluetooth name as "btDevice"
  blueSerial.print("\r\n+STPIN=0000\r\n");//Set SLAVE pincode"0000"
  blueSerial.print("\r\n+STOAUT=1\r\n"); // Permit Paired device to connect me
  blueSerial.print("\r\n+STAUTO=0\r\n"); // Auto-connection should be forbidden here
  delay(2000); // This delay is required.
  blueSerial.print("\r\n+INQ=1\r\n"); //make the slave bluetooth inquirable
  delay(2000); // This delay is required.
  blueSerial.flush();
}

void recCMD()
{ 
  byte newByte;
  
  if (blueSerial.available() > 0) {
    newByte = blueSerial.read();
    
    }
  }
}

void getCode(byte pid)
{
  int value;
  
  // send a query command
  obd.sendQuery(pid);
  pid = 0; // this lets PID also obtained and filled from response
  // receive and parse the response
  if (obd.getResult(pid, value) && blueSerial.available()) {
      blueSerial.print(value);
  }
}
