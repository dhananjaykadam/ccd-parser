package com.ccd.models;

public class Code {
	private String codeSystemName, codeSystem, code;

	/**
	 * @return the codeSystemName
	 */
	public String getCodeSystemName() {
		return codeSystemName;
	}

	/**
	 * @param codeSystemName the codeSystemName to set
	 */
	public void setCodeSystemName(String codeSystemName) {
		this.codeSystemName = codeSystemName;
	}

	/**
	 * @return the codeSystem
	 */
	public String getCodeSystem() {
		return codeSystem;
	}

	/**
	 * @param codeSystem the codeSystem to set
	 */
	public void setCodeSystem(String codeSystem) {
		this.codeSystem = codeSystem;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Code [codeSystemName=" + codeSystemName + ", codeSystem=" + codeSystem + ", code=" + code + "]";
	}

}
