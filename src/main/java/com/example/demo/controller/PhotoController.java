package com.example.demo.controller;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.io.IOUtils;

import com.example.demo.model.Photos;
import com.example.demo.repository.PhotosInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class PhotoController {
	
	
	@Autowired
	private PhotosInterface PhotoInterface;
	
	public void DownloadAndSetPhoto(Photos Photo) {
		BufferedImage Image =null;
        try{
 
            URL url =new URL(Photo.getPhotoUrl());
            Image = ImageIO.read(url);
            File ImageFolder=new File("Images"+File.separator);
            System.out.println(ImageFolder.getCanonicalPath());
            if(!ImageFolder.exists()) {
            	if(!ImageFolder.mkdirs()) {
            		System.out.println("cant create folder");
            	}
            	 else {
                 	System.out.println("Folder Created");
                 }
            	
            }
            else {
            	System.out.println("Folder Exists");
            }
            String LocalPath="Images"+File.separator+ Photo.getPhotoTitle()+ ".jpg";
            Photo.setLocalPath(LocalPath);
            File ImageFile=new File(LocalPath);
            
            
            if(!ImageFile.exists()) {
            	if(!ImageFile.createNewFile()) {
            		System.out.println("Cant Create File");
            	}
            	else {
            		System.out.println("File created at path: "+ImageFile.getAbsolutePath() );
            	}
            }
            else {
            	System.out.println("File Exists");
            }
            ImageIO.write(Image, "jpg",ImageFile);
            Photo.setSize(ImageFile.length());
            Photo.setDownloadTime(LocalDateTime.now());
            PhotoInterface.save(Photo);
            
        }catch(IOException e){
            e.printStackTrace();
        }
		
	}
	
	
	
	
	@RequestMapping("/Initialize")
	public String Initialize() {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("https://shield-j-test.s3.amazonaws.com/photo.txt", String.class);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Photos> PhotosJsonList = mapper.readValue(response, new TypeReference<List<Photos>>(){});
			for(Photos Photo:PhotosJsonList) {
				DownloadAndSetPhoto(Photo);
				System.out.println(Photo);
			}
			
			
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "Program Was Initialized";
	}
	
	
	@RequestMapping("/AllPhotos")
	@ResponseBody
	public List<Photos> GetAllPhotos() {
		return PhotoInterface.findAll();
	}
	
	
	@RequestMapping("/Albums/{albumId}")
	@ResponseBody
	public List<Photos> GetAllPhotosForAlbum(@PathVariable("albumId") int albumId) {
		return PhotoInterface.findByAlbumId(albumId);
	}
	
	@GetMapping(value="/GetPhoto/{photoId}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Object> GetPhoto(@PathVariable("photoId") int photoId) throws IOException {
		Photos photos=PhotoInterface.findById(photoId).orElseGet(null);
		
		File file = new File(photos.getLocalPath());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/txt")).body(resource);

		return responseEntity;
	 
	    }
	
		
	
	
	
	
	
}
