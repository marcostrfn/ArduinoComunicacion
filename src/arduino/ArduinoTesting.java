package arduino;

public class ArduinoTesting {
    
    // arduinoListener
    private final ArduinoListener arduinoListener = new ArduinoListener();
    private ArduinoComunicacion arduino = new ArduinoComunicacion();

    private int contador = 0;
    
    public static void main(String[] args) {
        ArduinoTesting t = new ArduinoTesting();
    }

    public ArduinoTesting() {
        
        arduino.setDATA_RATE(9600);
        arduino.setPORT_NAME("COM5");
        arduino.setMODE_DATA(ArduinoVariables.MODO_BYTE);
        arduino.initializeArduinoConnection(arduinoListener);


        arduinoListener.addListener(new ArduinoListener.Listener() {

            @Override
            public void afterValueIntChanged(ArduinoListener counter) {
                System.out.println("afterValueIntChanged " + counter.getIntValue());
                System.out.println("--------------------------");
            }

            @Override
            public void afterValueFloatChanged(ArduinoListener counter) {
                System.out.println("afterValueFloatChanged " + counter.getFloatValue());
                System.out.println("--------------------------");
            }

            @Override
            public void afterValueStringChanged(ArduinoListener counter) {
                byte b;
                System.out.println("afterValueStringChanged " + counter.getStringValue());
                System.out.println("--------------------------");
                
                b = (byte) contador++;
                arduino.sendBytes(b);
                
            }

            @Override
            public void afterValueByteChanged(ArduinoListener counter) {
                System.out.println("afterValueByteChanged " + counter.getByteValue());
                char bbAsChar = arduino.getByteAsChar( counter.getByteValue() );
                System.out.println("Byte : " + bbAsChar + " : " + String.valueOf((int) bbAsChar) + " : " + Integer.toBinaryString((int) bbAsChar));
                System.out.println("--------------------------");
            }
            
        });
    }

}
