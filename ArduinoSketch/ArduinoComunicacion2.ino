

#include <RXTXJavaComunicacion.h>

RXTXJavaComunicacion rxtx(9600);

byte inComingByte;   
byte outComingByte;   


int myInt;
float myFloat;

void setup() {
  
  rxtx.init();
  
  myInt = 0;
  myFloat = .0;
  inComingByte = 0;
  outComingByte = B00000001; 
  
}


void modoBytes() {  
  myInt++;
  myFloat = myFloat + 2;  
  rxtx.sendByte(outComingByte);
  rxtx.sendInt(myInt);
  rxtx.sendFloat(myFloat);
}

void modoString() {
 int sensorValue = analogRead(A0);
 rxtx.sendString("El resultado de la lectura: "); 
 rxtx.sendByte(0x0D);
 rxtx.sendByte(0x0A);
}

void modoRead() { 
 outComingByte = inComingByte;
}


void loop() {
  // lectura por serie
  while (Serial.available() > 0)
  { 
    inComingByte = Serial.read();    
    modoRead();
  }
  modoBytes();  
  // modoString();  
  delay(1000);
}
