package controler;

import java.io.Serializable;

public class BusinessException extends Exception implements Serializable {
	private static final long serialVersionUID = 1L;

	public BusinessException(String erro) {
		super(erro);
	}
}