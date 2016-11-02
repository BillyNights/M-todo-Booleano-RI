
public class TotalTF_IDF {

	private String documento;
	private float valor;

	public TotalTF_IDF(String documento, float valor) {
		super();
		this.documento = documento;
		this.valor = valor;
	}

	public TotalTF_IDF() {

	}

	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return the valor
	 */
	public float getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(float valor) {
		this.valor = valor;
	}

}
