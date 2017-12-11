package br.unicamp.cepetro.unisim.mero.ui.app.action;

/**
 * Componente representa uma a��o, normalmente vinculada a interven��o do usu�rio nos componentes de
 * interface gr�fica, para solicitar uma opera��o ao sistema.
 *
 ** <p>
 * Utiliza o design pattern <code>Template Method</code> para definir um estrutura/template com
 * c�digo complementar (e opcional) a a��o:
 * </p>
 * *
 * <ul>
 * <li><code>preAction()</code>: Acionando antes da execu��o de <code>action()</code>.</li>
 * <li><code>posAction()</code>: Acionando ap�s a execu��o (com sucesso) de
 * <code>action()</code>.</li>
 * <li><code>actionFailure()</code>: Acionando caso a execu��o de <code>action()</code> falhe.</li>
 * </ul>
 *
 * @author rocha
 */
public abstract class AbstractAction {
	/**
	 * M�todo principal, define o processamento da <code>AbstractAction</code>.
	 */
	protected abstract void action();

	/**
	 * M�todo acionado <strong>antes</string> de <code>action()</code>.
	 * <p>
	 * Caso uma exce��o (<code>RuntimeException</code>) seja lan�ada, a execu��o de toda a
	 * <code>AbstractAction</code> � interrompida.
	 * </p>
	 */
	protected void preAction() {}

	/**
	 * M�todo executado ap�s a conclus�o de <code>action()</code>.
	 */
	protected void posAction() {}

	/**
	 * M�todo � acionado quando alguma falha ocorre durante a execu��o de <code>action</code>,
	 * <code>preAction</code> ou <code>posAction</code>.
	 */
	protected void actionFailure() {}

	/**
	 * M�todo respons�vel por organizar e executar a cadeia de m�todos de <code>AbstractAction</code>.
	 * 
	 * @throws <code>RuntimeException</code> caso algum erro ocorra.
	 */
	public final void actionPerformed() {
		try {
			preAction();
			action();
			posAction();
		} catch (Exception ex) {
			actionFailure();
			throw new RuntimeException(ex);
		}
	}
}
