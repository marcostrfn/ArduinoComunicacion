package arduino;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ArduinoTest1 {

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
        ArduinoTest1 t = new ArduinoTest1();
    }

    public ArduinoTest1() {
        a.setDATA_RATE(9600);
        a.setPORT_NAME("COM34");
        // a.setMODE_DATA(ArduinoVariables.MODO_BYTE);
        a.setMODE_DATA(ArduinoVariables.MODO_BYTE);
        a.initializeArduinoConnection(c);


        c.addListener(new Counter.Listener() {

            public void afterValueIntChanged(Counter counter) {
                System.out.println("afterValueIntChanged " + counter.getIntValue());
                System.out.println("--------------------------");
            }

            public void afterValueFloatChanged(Counter counter) {
                System.out.println("afterValueFloatChanged " + counter.getFloatValue());
                System.out.println("--------------------------");
            }

            public void afterValueStringChanged(Counter counter) {
                System.out.println("afterValueStringChanged " + counter.getStringValue());
                System.out.println("--------------------------");
            }

            public void afterValueByteChanged(Counter counter) {
                System.out.println("afterValueByteChanged " + counter.getByteValue());
                char bbAsChar = a.getByteAsChar( counter.getByteValue() );
                System.out.println("Byte : " + bbAsChar + " : " + String.valueOf((int) bbAsChar) + " : " + Integer.toBinaryString((int) bbAsChar));
                System.out.println("--------------------------");
            }
            
        });
    }

}
