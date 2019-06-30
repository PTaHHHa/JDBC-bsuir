package library;

public class World {

	private int id;
    private String name;
    private String type;
    private int area;
    private int population;

    public World(int Id, String name, String type, int area, int population){
        this.id = Id;
    	this.name = name;
        this.type = type;
        this.area = population;
        this.population = area;
    }

    public int getId() {
    	return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }
}
