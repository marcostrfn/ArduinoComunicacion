package arduino;

public interface ArduinoControl {
	public abstract void getData(String s);
	public abstract void getInt(int i);
	public abstract void getFloat(float f);
        public abstract void getByte(byte b);
}
