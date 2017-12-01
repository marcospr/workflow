package helper;

public class TypeHelper {
	public <T> Boolean isTypeFrom(final Class<T> clazz, final Object object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		return object.getClass().isAssignableFrom(clazz);
	}
}
