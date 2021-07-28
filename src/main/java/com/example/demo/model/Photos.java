package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Photos")
public class Photos {
	
	@Id
	@JsonProperty("id")
	@Column(name = "Photo_Id", unique = true, nullable = false)
	private int photoId;
	

	@JsonProperty("albumId")
	@Column(name = "Album_Id", unique = false, nullable = false)
	private int albumId;
	
	@JsonProperty("title")
	@Column(name = "Title", unique = false, nullable = false)
	private String photoTitle;
	
	@JsonProperty("url")
	@Column(name = "Url", unique = false, nullable = false)
	private String photoUrl;
	
	@JsonProperty("thumbnailUrl")
	@Column(name = "Thumbnail_Url", unique = false, nullable = false)
	private String photoThumUrl;
	
	@JsonIgnore
	@Column(name = "Download_Time", unique = false, nullable = false)
	private LocalDateTime downloadTime;
	
	@JsonIgnore
	@Column(name = "Save_Path", unique = false, nullable = false)
	private String localPath;
	
	@JsonIgnore
	@Column(name = "Size", unique = false, nullable = false)
	private long size;
	
	public Photos() {
		
	}
	
	
	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getPhotoTitle() {
		return photoTitle;
	}

	public void setPhotoTitle(String photoTitle) {
		this.photoTitle = photoTitle;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoThumUrl() {
		return photoThumUrl;
	}

	public void setPhotoThumUrl(String photoThumUrl) {
		this.photoThumUrl = photoThumUrl;
	}

	public LocalDateTime getDownloadTime() {
		return downloadTime;
	}

	public void setDownloadTime(LocalDateTime downloadTime) {
		this.downloadTime = downloadTime;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "Photos [photoId=" + photoId + ", albumId=" + albumId + ", photoTitle=" + photoTitle + ", photoUrl="
				+ photoUrl + ", photoThumUrl=" + photoThumUrl + ", downloadTime=" + downloadTime + ", localPath="
				+ localPath + ", size=" + size + "]";
	}
	
}
