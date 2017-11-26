package diagram;

public interface Diagram extends Selectable, ConnectionPoints {
	Diagram getNext();

	void setNext(final Diagram diagram);
}
