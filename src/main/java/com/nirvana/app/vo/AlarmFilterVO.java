package com.nirvana.app.vo;

import java.util.List;

public class AlarmFilterVO {
	private List<String> names;
	private List<AlarmCommunityVO> data;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<AlarmCommunityVO> getData() {
		return data;
	}

	public void setData(List<AlarmCommunityVO> data) {
		this.data = data;
	}

}
