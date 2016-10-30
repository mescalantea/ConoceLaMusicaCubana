package com.github.mescalantea.conocelamusicacubana.model;

public class Score
{
	private String player;
	private int percent;

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public Score(String player, int percent) {
		this.player = player;
		this.percent = percent;
	}

	public Score() {}

}
