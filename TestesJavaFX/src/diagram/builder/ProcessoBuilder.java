package diagram.builder;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

import diagram.Processo;

public class ProcessoBuilder {
	private double x;
	private double y;

	private static final double DEFAULT_DOUBLE_VALUE = 0.0d;
	private static final String MESSAGE_PARAMETER_INVALID = "Parametro {0} invalido, verifique.";

	public ProcessoBuilder() {}

	public Processo builder() {
		if (x == DEFAULT_DOUBLE_VALUE) {
			throw new InvalidParameterException(
					MessageFormat.format(MESSAGE_PARAMETER_INVALID, "Translate X"));
		}
		if (y == DEFAULT_DOUBLE_VALUE) {
			throw new InvalidParameterException(
					MessageFormat.format(MESSAGE_PARAMETER_INVALID, "Translate Y"));
		}
		return new Processo(x, y);

	}

	public ProcessoBuilder comTranslateX(final double X) {
		x = X;
		return this;
	}

	public ProcessoBuilder comTranslateY(final double Y) {
		y = Y;
		return this;
	}

}
