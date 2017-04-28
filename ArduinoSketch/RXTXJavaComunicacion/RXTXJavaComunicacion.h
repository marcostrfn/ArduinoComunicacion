/*
RXTXJavaComunicacion - Library for java RXTX comunicacttion
Created by Marcos Trampal, Abril 2017. Released into the public domain.
*/

#ifndef RXTXJavaComunicacion_h
#define RXTXJavaComunicacion_h

#include <Arduino.h>
#include "Tipos.h"  /* tipos para comunicacion */

class RXTXJavaComunicacion

{
   public: RXTXJavaComunicacion(int velocidad);
   void init();
   void sendInt(int v);
   void sendFloat(float v);
   void sendString(String s);
   void sendByte(byte v);

private:
   int _velocidad;
   IntUnion_t _myInt;
   FloatUnion_t _myFloat;
};

#endif