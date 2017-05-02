package arduino;

public class ArduinoTesting1 {
    
    // arduinoListener
    private final ArduinoListener arduinoListener = new ArduinoListener();
    private ArduinoComunicacion arduino = new ArduinoComunicacion();

    private int contador = 0;
    private NewJFrame f;
    
    ArduinoTesting1(NewJFrame aThis) {
        f = aThis;
        init();
    }

    private void init() {
        
        arduino.setDATA_RATE(Integer.valueOf(f.getVelocidad()));
        arduino.setPORT_NAME(f.getPuerto());
        
        arduino.setMODE_DATA(ArduinoVariables.MODO_BYTE);
        arduino.initializeArduinoConnection(arduinoListener);


        arduinoListener.addListener(new ArduinoListener.Listener() {

            @Override
            public void afterValueIntChanged(ArduinoListener counter) {
                System.out.println("afterValueIntChanged " + counter.getIntValue());
                System.out.println("--------------------------");
                
                f.appendTexto("afterValueIntChanged " + counter.getIntValue());
                
            }

            @Override
            public void afterValueFloatChanged(ArduinoListener counter) {
                System.out.println("afterValueFloatChanged " + counter.getFloatValue());
                System.out.println("--------------------------");
                f.appendTexto("afterValueFloatChanged " + counter.getFloatValue());
            }

            @Override
            public void afterValueStringChanged(ArduinoListener counter) {
                byte b;
                System.out.println("afterValueStringChanged " + counter.getStringValue());
                System.out.println("--------------------------");
                
                f.appendTexto("afterValueStringChanged " + counter.getStringValue());
                
                b = (byte) contador++;
                arduino.sendBytes(b);
                
            }

            @Override
            public void afterValueByteChanged(ArduinoListener counter) {
                System.out.println("afterValueByteChanged " + counter.getByteValue());
                char bbAsChar = arduino.getByteAsChar( counter.getByteValue() );
                System.out.println("Byte : " + bbAsChar + " : " + String.valueOf((int) bbAsChar) + " : " + Integer.toBinaryString((int) bbAsChar));
                System.out.println("--------------------------");
                
                f.appendTexto("afterValueByteChanged " + counter.getByteValue());
                f.appendTexto("Byte : " + bbAsChar + " : " + String.valueOf((int) bbAsChar) + " : " + Integer.toBinaryString((int) bbAsChar));
                
            }
            
        });
    }



}
