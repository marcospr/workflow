package diagram;

import java.security.InvalidParameterException;
import java.text.MessageFormat;

public class ProcessoBuilder {
	private double X;
	private double Y;

	private static final double DEFAULT_DAOBLE_VALUE = 0.0d;
	private static final String MESSAGE_PARAMETER_INVALID = "Parametro {0} invalido, verifique.";

	public ProcessoBuilder() {}

	public Processo builder() {
		if (X == DEFAULT_DAOBLE_VALUE) {
			throw new InvalidParameterException(
					MessageFormat.format(MESSAGE_PARAMETER_INVALID, "Translate X"));
		}
		if (Y == DEFAULT_DAOBLE_VALUE) {
			throw new InvalidParameterException(
					MessageFormat.format(MESSAGE_PARAMETER_INVALID, "Translate Y"));
		}
		return new Processo(X, Y);

	}

	public ProcessoBuilder comTranslateX(final double X) {
		this.X = X;
		return this;
	}

	public ProcessoBuilder comTranslateY(final double Y) {
		this.Y = Y;
		return this;
	}

}
