/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author marcos
 */
public class ArduinoListener {

    private int intValue = 0;
    private float floatValue = 0;
    private byte byteValue = 0;
    private String stringValue;
    private final List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public interface Listener {

        void afterValueIntChanged(ArduinoListener counter);

        void afterValueFloatChanged(ArduinoListener counter);

        void afterValueStringChanged(ArduinoListener counter);

        void afterValueByteChanged(ArduinoListener counter);
    }

    private void fireAfterValueIntChanged() {
        for (Listener listener : listeners) {
            listener.afterValueIntChanged(this);
        }
    }

    private void fireAfterValueFloatChanged() {
        for (Listener listener : listeners) {
            listener.afterValueFloatChanged(this);
        }
    }

    private void fireAfterValueStringChanged() {
        for (Listener listener : listeners) {
            listener.afterValueStringChanged(this);
        }
    }

    private void fireAfterValueByteChanged() {
        for (Listener listener : listeners) {
            listener.afterValueByteChanged(this);
        }
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
        fireAfterValueFloatChanged();
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
        fireAfterValueIntChanged();
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
         fireAfterValueByteChanged();
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
         fireAfterValueStringChanged();
    }







    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
