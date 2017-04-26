package arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.util.Arrays;

public class Arduino implements SerialPortEventListener {

    // Puerto de conexion
    public String PORT_NAME = "COM";
    // Default bits per second for COM port.
    public int DATA_RATE = 115200;
    // Modo de datos
    public int MODE_DATA = ArduinoVariables.MODO_STRING;
    /** The output stream to the port */
    private OutputStream output = null;
    private InputStream input = null;
    SerialPort serialPort;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    // manejar la cadena de informacion recibida de arduino
    private char[] aChar = new char[1024];
    private int numero = 0;
    // para interaccion con la clase que le llama
    private ArduinoTest miArduino;

    public int getDATA_RATE() {
        return DATA_RATE;
    }

    public void setDATA_RATE(int DATA_RATE) {
        this.DATA_RATE = DATA_RATE;
    }

    public String getPORT_NAME() {
        return PORT_NAME;
    }

    public void setPORT_NAME(String PORT_NAME) {
        this.PORT_NAME = PORT_NAME;
    }

    public int getMODE_DATA() {
        return MODE_DATA;
    }

    public void setMODE_DATA(int MODE_DATA) {
        this.MODE_DATA = MODE_DATA;
    }

    public void initializeArduinoConnection(ArduinoTest at) {
        miArduino = at;
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        // iterate through, looking for the port
        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();

            System.out.println("Port Name : " + currPortId.getName());
            if (PORT_NAME.equals(currPortId.getName())) {
                portId = currPortId;
                break;
            }
        }

        if (portId == null) {
            System.out.println("Could not find COM port.");
            return;
        }

        try {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);

            // open the streams
            output = serialPort.getOutputStream();
            input = serialPort.getInputStream(); // Se prepara input para recibir datos

            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public byte getCharAsByte(char c) {
        byte [] b = new byte[1];
        b[0] = (byte) ((byte) c & 0x00FF);
        return b[0];
    }

    public char getArrayBufferAsChar(byte[] bi) {
        return (char) ((bi[0] & 0xFF) | ((0 & 0xFF) << 8));
    }

    public char getByteAsChar(byte bi) {
        return (char) ((bi & 0xFF) | ((0 & 0xFF) << 8));
    }

    public int getArrayBufferAsInt(byte[] bi) {
        return (bi[0] & 0xFF) | ((bi[1] & 0xFF) << 8) | ((0 & 0xFF) << 16) | ((0 & 0xFF) << 24);
    }

    public float getArrayBufferAsFloat(byte[] bf) {
        int bfAsInt = (bf[0] & 0xFF) | ((bf[1] & 0xFF) << 8) | ((bf[2] & 0xFF) << 16) | ((bf[3] & 0xFF) << 24);
        return Float.intBitsToFloat(bfAsInt);
    }

    public void sendBytes(char c) {
        try {
            System.out.println("Enviando " + c);               
            output.write(getCharAsByte(c));
        } catch (IOException e) {
            System.out.println("Error sending data");
        }
    }

    public void sendBytes(byte b) {
        try {
            System.out.println("Enviando " + Integer.valueOf(b));
            output.write(b);
        } catch (IOException e) {
            System.out.println("Error sending data");
        }
    }

    public void sendData(String data) {
        try {
            output.write(data.getBytes());
        } catch (IOException e) {
            System.out.println("Error sending data");
        }
    }

    private byte RecibirDatos() throws IOException {
        byte output = 0;
        output = (byte) input.read();
        return output;
    }

    private int RecibirDatosInt() throws IOException {
        int output = 0;
        output = input.read();
        return output;
    }

    public void serialEvent(SerialPortEvent oEvent) {
        // TODO Auto-generated method stub
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {

                switch (MODE_DATA) {
                    case ArduinoVariables.MODO_STRING:
                        int datosString;
                        datosString = RecibirDatosInt(); // Se invoca la función RecibirDatos()

                        switch (datosString) {
                            case 10:
                                break;
                            case 13:                                
                                char[] fChar = new char[numero];
                                for (int i = 0; i < numero; i++) {
                                    fChar[i] = aChar[i];
                                }
                                for (int i = 0; i < 1024; i++) {
                                    aChar[i] = 0;
                                }
                                numero = 0;
                                miArduino.getData(String.valueOf(fChar));
                                break;
                            default:                                
                                aChar[numero++] = (char) datosString;
                                break;
                        }
                        break;
                    case ArduinoVariables.MODO_INT:
                        int dataInt;
                        dataInt = RecibirDatosInt(); // Se invoca la funci�n RecibirDatos()
                        miArduino.getInt(dataInt);
                        break;

                    case ArduinoVariables.MODO_BYTE:
                        byte dataByte;
                        dataByte = RecibirDatos(); // Se invoca la funci�n RecibirDatos()
                        miArduino.getByte(dataByte);
                        break;
                    default:
                        break;
                }







            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }


}
