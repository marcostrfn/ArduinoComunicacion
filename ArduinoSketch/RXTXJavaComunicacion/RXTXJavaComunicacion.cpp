

#include "RXTXJavaComunicacion.h"





RXTXJavaComunicacion::RXTXJavaComunicacion(int velocidad)
{   
   _velocidad = velocidad;
}

void RXTXJavaComunicacion::init()
{
	Serial.begin(_velocidad);   
}

void RXTXJavaComunicacion::sendInt(int v)
{   
   _myInt.i = v;
   Serial.write(_myInt.b,sizeof(_myInt.b));
}

void RXTXJavaComunicacion::sendFloat(float v)
{  
   _myFloat.f = v;
   Serial.write(_myFloat.b,sizeof(_myFloat.b));
}

void RXTXJavaComunicacion::sendString(String s)
{	
	int l = s.length();
	char charBuf[l];
    s.toCharArray(charBuf, l);

	for (int i=0;i<l;i++) {
		Serial.write(charBuf[i]);
	}   
}

void RXTXJavaComunicacion::sendByte(byte v)
{
	Serial.write(v);	
}


