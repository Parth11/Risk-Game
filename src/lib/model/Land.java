package lib.model;

public abstract class Land implements Comparable<Land> {
	private String name;
	
	public Land(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Land land) {
		return this.getName().compareTo(land.getName());
	}
}
