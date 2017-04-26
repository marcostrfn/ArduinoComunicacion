

// para datos llegando por el puerto serie.
byte inComingByte;   
byte outComingByte;   

typedef union
{
  int i;
  byte b[2];
} INTUNION_t;

typedef union
{
  float f;
  byte b[4];
} FLOATUNION_t;

INTUNION_t myInt;
FLOATUNION_t myFloat;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  myInt.i = 0;
  myFloat.f = .0;
  inComingByte = 0;
  outComingByte = B00000001; 
}


void modoBytes() {  
  myInt.i++;
  myFloat.f = myFloat.f + 2;  
  Serial.write(outComingByte);
  Serial.write(myInt.b,sizeof(myInt.b));
  Serial.write(myFloat.b,sizeof(myFloat.b));
}

void modoString() {
 int sensorValue = analogRead(A0);
 Serial.print("El resultado de la lectura: ");
 Serial.println(sensorValue); 
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
