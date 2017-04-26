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
public class Counter {

    private int value = 0;
    private float floatValue = 0;
    private final List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public interface Listener {
        void afterValueChanged(Counter counter);
        void afterValueFloatChanged(Counter counter);
    }

    private void fireAfterValueChanged() {
        for (Listener listener : listeners) {
            listener.afterValueChanged(this);
        }
    }

    private void fireAfterValueFloatChanged() {
        for (Listener listener : listeners) {
            listener.afterValueFloatChanged(this);
        }
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
        fireAfterValueFloatChanged();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        fireAfterValueChanged();
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }
}
