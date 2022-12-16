package application;

public class Cafe {
	private String id;
	private String name;
	private String size;
	private String price;
	private String note;
	
	public Cafe() {
		
	}
	
	public Cafe(String id, String name, String size, String price, String note) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.price = price;
		this.note = note;
		}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
