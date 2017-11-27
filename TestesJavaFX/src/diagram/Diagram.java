package diagram;

public interface Diagram extends Selectable, ConnectionPoints {
	DiagramContainer getNext();

	void setNext(final DiagramContainer diagram);

}
