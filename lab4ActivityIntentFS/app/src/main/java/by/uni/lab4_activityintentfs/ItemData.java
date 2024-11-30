package by.uni.lab4_activityintentfs;

import java.util.ArrayList;

public class ItemData {
	private String title;
	private String type;
	private String year;
	private String author;
	private String pg;
	private String description;
	private ArrayList<String> tags;

	public ItemData(String title, String type, String year, String author, String pg, String description, ArrayList<String> tags) {
		this.title = title;
		this.type = type;
		this.year = year;
		this.author = author;
		this.pg = pg;
		this.description = description;
		this.tags = tags;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
}
