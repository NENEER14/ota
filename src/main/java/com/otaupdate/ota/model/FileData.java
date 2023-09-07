package com.otaupdate.ota.model;

import java.util.Date;

public class FileData {
    
    private String fileName;
    private int version;
    private Date date;
	private String encodedFile;


    public FileData(String fileName, int version, Date date, String encodedFile) {
        this.fileName = fileName;
        this.version = version;
        this.date = date;
		this.encodedFile = encodedFile;
    }

    public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getEncodedFile() {
		return encodedFile;
	}
	public void setEncodedFile(String encodedFile) {
		this.encodedFile = encodedFile;
	}
}
