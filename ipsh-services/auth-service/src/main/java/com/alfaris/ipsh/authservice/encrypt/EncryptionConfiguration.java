package com.alfaris.ipsh.authservice.encrypt;

/**
 * Project Name : CIBMobile
 * 	@author Interland 
 * Date Created : 03-Feb-2020
 * 
 */
final class EncryptionConfiguration extends Encryption {
	public EncryptionConfiguration() {
		setEncryptionMode(4);
		setConfiguration("DEFAULT");
	}

	public EncryptionConfiguration(int mode) {
		setEncryptionMode(mode);
		setConfiguration("DEFAULT");
	}

	public EncryptionConfiguration(String applicationKey) {
		setEncryptionMode(4);
		setConfiguration(applicationKey);
	}

	public EncryptionConfiguration(int mode, String applicationKey) {
		setEncryptionMode(mode);
		setConfiguration("C." + applicationKey);
	}
}