package com.github.mescalantea.conocelamusicacubana.model;


import java.util.List;

public class Question
{
	
	private int questionId;
	private Category category;
	private String answer;
	private List<String>options;
	private String clue;
	private int file;

	public int getQuestionId()
	{
		return questionId;
	}
	
	public void setQuestionId(int questionId)
	{
		this.questionId = questionId;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public void setCategory(Category category)
	{
		this.category = category;
	}

	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public int getFile() {
		return file;
	}

	public void setFile(int file) {
		this.file = file;
	}
	
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getOptions() {
		return options;
	}

	public void setOptions(List<String> options) {
		this.options = options;
	}

	public Question(){}

}
