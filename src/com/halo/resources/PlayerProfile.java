package com.halo.resources;

import java.util.ArrayList;
import java.util.List;


public class PlayerProfile {

	private List<String> rankList;
	private List<String> playList;

	public PlayerProfile() {
		this.rankList = new ArrayList<String>();
		this.playList = new ArrayList<String>();
	}

	public void addRank(String rank) {
		rankList.add(rank);
	}

	public List<String> getRankList() {
		return this.rankList;
	}

	public void addPlayList(String playlist) {
		playList.add(playlist);
	}

	public List<String> getPlayList() {
		return this.playList;
	}
}
