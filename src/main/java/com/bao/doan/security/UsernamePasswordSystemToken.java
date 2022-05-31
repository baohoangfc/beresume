package com.bao.doan.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

public class UsernamePasswordSystemToken extends UsernamePasswordAuthenticationToken  {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private String system;
	
	public UsernamePasswordSystemToken(Object principal, Object credentials,String system) {
		super(principal, credentials);
		this.system=system;
	}
	
	public UsernamePasswordSystemToken(Object principal, Object credentials,String system,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.system=system;
	}



	public String getSystem() {
		return this.system;
	}
}

