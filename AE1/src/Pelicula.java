
public class Pelicula {
	
	private String id;
    private String titulo;
    private String director;
    private double precio;

    public Pelicula(String id, String titulo, String director, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDirector() {
        return director;
    }

    public double getPrecio() {
        return precio;
    }

	@Override
	public String toString() {
		return "Pelicula [id=" + id + ", titulo=" + titulo + ", director=" + director + ", precio=" + precio + "]";
	}


}
