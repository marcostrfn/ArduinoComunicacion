

#include <RXTXJavaComunicacion.h>

#include <LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27, 20, 4); // set the LCD address to 0x20 for a 16 chars and 2 line display



RXTXJavaComunicacion rxtx(9600);

byte inComingByte;
byte outComingByte;

int myInt;
float myFloat;
int recibidos;

void setup() {

  rxtx.init();

  myInt = 0;
  myFloat = .0;
  inComingByte = 0;
  outComingByte = B00000001;

  lcd.init(); // initialize the lcd
  // Print a message to the LCD.
  lcd.backlight();
  lcd.setCursor(0, 0);
  lcd.print("Marcos Trampal");
  lcd.setCursor(0, 1);
  lcd.print("Arduino");
  lcd.setCursor(0, 2);
  lcd.print("Test");


}


void modoBytes() {
  myInt++;
  myFloat = myFloat + 2;
  rxtx.sendByte(outComingByte);
  rxtx.sendInt(myInt);
  rxtx.sendFloat(myFloat);

  char buffer[50];
  sprintf(buffer, "Arduino %i %i", myInt, 6);
  rxtx.sendString(buffer);

}

void modoString() {
  int sensorValue = analogRead(A0);
  rxtx.sendString("El resultado de la lectura: ");
  rxtx.sendByte(0x0D);
  rxtx.sendByte(0x0A);
}

void modoRead() {
  outComingByte = inComingByte;
  //char buffer[20];
  //sprintf(buffer, "Recibido: %s %4d", outComingByte, recibidos++);  
  lcd.setCursor(0, 3);
  lcd.print("Recibido ");
  lcd.print(outComingByte, DEC);
  lcd.print(" ");
  lcd.print(recibidos++);   
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
