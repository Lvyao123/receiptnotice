package com.receipt.notice;

import java.util.Map;

/**
 * @author youtui
 */
public interface AsyncResponse {
	 void onDataReceivedSuccess(String[] returnstr);
    void onDataReceivedFailed(String[] returnstr, Map<String, String> postedmap);
}
