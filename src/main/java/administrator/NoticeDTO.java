package administrator;

import java.sql.Date;

public class NoticeDTO {
	private int number;
	private String writer;
	private Date regist_date;
	private String title;
	private String content;
	private int category;	// 1:일반공지, 2:이벤트공지, 3:FAQ

	public NoticeDTO() {
		super();
	}

	public NoticeDTO(int number, String writer, Date regist_date, String title, String content, int category) {
		this.number = number;
		this.writer = writer;
		this.regist_date = regist_date;
		this.title = title;
		this.content = content;
		this.category = category;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegist_date() {
		return regist_date;
	}

	public void setRegist_date(Date regist_date) {
		this.regist_date = regist_date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
