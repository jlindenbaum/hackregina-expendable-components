package com.android.hackregina;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class YelpV2API extends DefaultApi10a {

	@Override
	public String getAccessTokenEndpoint() {
		return null;
	}

	@Override
	public String getAuthorizationUrl(Token arg0) {
		return null;
	}

	@Override
	public String getRequestTokenEndpoint() {
		return null;
	}

}