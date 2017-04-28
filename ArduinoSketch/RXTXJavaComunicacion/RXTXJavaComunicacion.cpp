

#include "RXTXJavaComunicacion.h"





RXTXJavaComunicacion::RXTXJavaComunicacion(int velocidad)
{   
   _velocidad = velocidad;
}

void RXTXJavaComunicacion::init()
{
	Serial.begin(_velocidad);   
}

void RXTXJavaComunicacion::sendByte(byte v)
{
	Serial.write(0x01);
	Serial.write(v);	
}

void RXTXJavaComunicacion::sendInt(int v)
{   
   _myInt.i = v;
   Serial.write(0x02);
   Serial.write(_myInt.b,sizeof(_myInt.b));
}

void RXTXJavaComunicacion::sendFloat(float v)
{  
   _myFloat.f = v;
   Serial.write(0x03);
   Serial.write(_myFloat.b,sizeof(_myFloat.b));
}

void RXTXJavaComunicacion::sendString(String s)
{	
	int l = s.length();	
	Serial.write(0x05);
	_myInt.i = l;
	Serial.write(_myInt.b,sizeof(_myInt.b));
	Serial.print(s);

}




