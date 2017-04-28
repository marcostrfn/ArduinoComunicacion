package arduino;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ArduinoTest implements ArduinoControl {

    private int LONGITUD_REGISTRO = 7; // longitud de registro esperada
    private byte[] buffer = new byte[LONGITUD_REGISTRO]; // buffer de entrada con longitud de registro
    private byte bb; // buffer para recibir byte
    private byte[] bf = new byte[4]; // buffer para recibir float
    private byte[] bi = new byte[2]; // buffer para recibir int
    private int CONTADOR_BYTE = 0;

    // listener
    private Counter c = new Counter();
    private Arduino a = new Arduino();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ArduinoTest t = new ArduinoTest();
    }

    public ArduinoTest() {
        a.setDATA_RATE(9600);
        a.setPORT_NAME("COM34");
        // a.setMODE_DATA(ArduinoVariables.MODO_BYTE);
        a.setMODE_DATA(ArduinoVariables.MODO_BYTE);
        a.initializeArduinoConnection(this);

        c.addListener(new Counter.Listener() {
            @Override
            public void afterValueChanged(Counter counter) {
                System.out.println("El valor " + counter.getValue());
                byte b = 0;
                char c = '1';

                if (counter.getValue() % 2==0) {
                    b = (byte) 50;
                    c = 'A';
                }else {
                    b = (byte) 49;
                    c = 'Z';
                }
                a.sendBytes(c);
                System.out.println();
            }
            public void afterValueFloatChanged(Counter counter) {
                System.out.println("El valor del Float " + counter.getFloatValue());
            }
        });
    }
    

    @Override
    public void getData(String s) {
        // TODO Auto-generated method stub
        System.out.println(s);
    }

    @Override
    public void getInt(int i) {
        // TODO Auto-generated method stub
        System.out.println(i);
    }

    @Override
    public void getByte(byte b) {
        // TODO Auto-generated method stub

        buffer[CONTADOR_BYTE++] = b;

        if (CONTADOR_BYTE == LONGITUD_REGISTRO) {
            CONTADOR_BYTE = 0;

            bb = Arrays.copyOfRange(buffer, 0, 1)[0];
            bi = Arrays.copyOfRange(buffer, 1, 3);
            bf = Arrays.copyOfRange(buffer, 3, 7);


            char bbAsChar = a.getByteAsChar(bb);
            int biAsInt = a.getArrayBufferAsInt(bi);
            float asFloat = a.getArrayBufferAsFloat(bf);

            System.out.println("Byte de inicio : " + bbAsChar + " : " + String.valueOf( (int) bbAsChar) + " : " +  Integer.toBinaryString( (int) bbAsChar ));
            if (bb == 49) {
                System.out.println("Byte de inicio es 1");
            }
            if (bbAsChar=='1') {
                System.out.println("Char de inicio es 1");
            }

            c.setFloatValue(asFloat);
            c.setValue(biAsInt);
           
        }

    }
  
}
