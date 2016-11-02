
public class VetCosult {

	private String palavra;
	private Float valor;

	public VetCosult(String palavra, Float valor) {
		super();
		this.palavra = palavra;
		this.valor = valor;
	}

	/**
	 * @return the palavra
	 */
	public String getPalavra() {
		return palavra;
	}

	/**
	 * @param palavra
	 *            the palavra to set
	 */
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	/**
	 * @return the valor
	 */
	public Float getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(Float valor) {
		this.valor = valor;
	}

}
