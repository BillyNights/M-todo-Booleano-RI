
public class Documento {

	private String nome;
	private int repeticoes;

	public Documento(String nome, int repeticoes) {
		this.nome = nome;
		this.repeticoes = repeticoes;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the repeticoes
	 */
	public int getRepeticoes() {
		return repeticoes;
	}

	/**
	 * @param repeticoes
	 *            the repeticoes to set
	 */
	public void setRepeticoes(int repeticoes) {
		this.repeticoes = repeticoes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + repeticoes;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Documento)) {
			return false;
		}
		Documento other = (Documento) obj;
		if (nome == null) {
			if (other.nome != null) {
				return false;
			}
		} else if (!nome.equals(other.nome)) {
			return false;
		}
		if (repeticoes != other.repeticoes) {
			return false;
		}
		return true;
	}

}
