package Pregel;

public class Message<T> {

	public T from = null; 
	public T to = null;
	public String info = null;
	
	public Message(T from, T to, String info) {
		// TODO Auto-generated constructor stub
		this.from = from;
		this.to = to;
		this.info = info;
	}

	public void setFrom(T from) {
		this.from = from;
	}
	
	public void setTo(T to) {
		this.to = to;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	@Override
	public String toString() {
		String string = from + "->" + to + "::" + info;
		return string;
	}
	
}
