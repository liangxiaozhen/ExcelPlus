package com.walmart.model;

public class MyExcel {
    private Long id;

    private String username;

    private String englishname;

    private String number;

    private String userid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEnglishname() {
        return englishname;
    }

    public void setEnglishname(String englishname) {
        this.englishname = englishname == null ? null : englishname.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

	@Override
	public String toString() {
		return "MyExcel [id=" + id + ", username=" + username
				+ ", englishname=" + englishname + ", number=" + number
				+ ", userid=" + userid + "]";
	}
    
    
}