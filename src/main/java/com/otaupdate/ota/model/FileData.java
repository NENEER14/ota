package com.otaupdate.ota.model;

import java.util.Date;

public class FileData {
    
    private String fileName;
    private String filePath;
    private int version;
    private Date date;


    public FileData(String fileName, String filePath, int version, Date date) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.version = version;
        this.date = date;
    }

    public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
