package br.unicamp.cepetro.unisim.mero.ui.app.action;

/**
 * Uma <code>AbstractAction</code> vinculada a uma condi��o <code>boolean</code>.
 *
 * <p>
 * <code>ConditionalAction</code> possui uma refer�ncia para outra <code>AbstractAction</code>, quem
 * efetivamente implementa a a��o. Mas o c�digo dessa a��o s� ser� executado quando a condi��o
 * <code>boolean</code> for satisfat�ria (<code>true</code>).
 * </p>
 *
 * <p>
 * Para implementar esse componente utilizamos o padr�o de projeto <strong>Decorator</strong>.
 * </p>
 *
 * @see BooleanExpression
 *
 * @author rocha
 */
public final class ConditionalAction extends AbstractAction {

	/**
	 * Refer�ncia para a a��o que dever� executar de acordo com a condi��o <code>boolean</code>.
	 */
	private AbstractAction action;

	/**
	 * Refer�ncia para condi��o <code>boolean</code>.
	 */
	private BooleanExpression expression;

	private ConditionalAction() {}

	/**
	 * Avalia a condi��o <code>boolean</code> para processar ou n�o a a��o.
	 *
	 * @throws <code>IllegalArgumentException</code> caso n�o tenha a��o e/ou condi��o
	 *         <code>boolean</code> vinculada.
	 */
	@Override
	protected void action() {
		if (action == null) {
			throw new IllegalArgumentException(
					"Indique a A��o que deve ser executada, utilize o m�todo addAction.");
		}

		if (expression == null) {
			throw new IllegalArgumentException(
					"Indique a express�o condicional da A��o, utilize o m�todo addConditional.");
		}

		if (expression.conditional()) {
			action.actionPerformed();
		}
	}

	/**
	 * @return Constr�i e retorna uma inst�ncia de <code>ConditionalAction</code> sem a��o e condi��o
	 *         definida.
	 */
	public static ConditionalAction build() {
		return new ConditionalAction();
	}

	/**
	 * Adiciona uma a��o a <code>ConditionalAction</code>.
	 *
	 * @param action a��o que deve ser processada.
	 * @return <code>ConditionalAction</code> com uma a��o definida.
	 */
	public ConditionalAction addAction(final AbstractAction action) {
		this.action = action;
		return this;
	}

	/**
	 * Adiciona a condi��o que determina se a a��o deve ou n�o ser processada.
	 *
	 * @param expression condi��o <code>boolean</code> avaliada por <code>ConditionalAction</code>.
	 * @return <code>ConditionalAction</code> com a condi��o <code>boolean</code> definida.
	 */
	public ConditionalAction addConditional(final BooleanExpression expression) {
		this.expression = expression;
		return this;
	}

}
