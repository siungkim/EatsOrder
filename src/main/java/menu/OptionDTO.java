package menu;

public class OptionDTO {
	private int option_id;
	private int group_id;
	private String option_name;
	private int price;
	private int enable;

	public OptionDTO() {
		super();
	}

	public OptionDTO(int option_id, int group_id, String option_name, int price, int enable) {
		this.option_id = option_id;
		this.group_id = group_id;
		this.option_name = option_name;
		this.price = price;
		this.enable = enable;
	}

	public int getOption_id() {
		return option_id;
	}

	public void setOption_id(int option_id) {
		this.option_id = option_id;
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getOption_name() {
		return option_name;
	}

	public void setOption_name(String option_name) {
		this.option_name = option_name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int is_enable() {
		return enable;
	}

	public void set_enable(int enable) {
		this.enable = enable;
	}
}
