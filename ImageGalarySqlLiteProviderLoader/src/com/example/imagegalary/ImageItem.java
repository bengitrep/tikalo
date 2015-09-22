package com.example.imagegalary;


import android.graphics.Bitmap;

public class ImageItem {
	private long id_db;

    private Bitmap image;
    private String title;
    
    private String id;
    private String posterName;
    
    private String year;
    private String minutes;
    private String description;

    

    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }
    
    
    /*
    public ImageItem(Bitmap image, String title, String id, String posterName) {
        super();
        this.image = image;
        this.title = title;
        this.id = id;
        this.posterName = posterName;
    }
    */

    public ImageItem(Bitmap image, String id, String title, String posterName, 
    		String year, String minutes, String description) {
        super();
        this.image = image;
        this.id = id; // movie id
        this.title = title;
        this.posterName = posterName;
        this.year = year;
        this.minutes = minutes;
        this.description = description;
    }
    
    
    public ImageItem() {
		// TODO Auto-generated constructor stub
	}


	public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPosterName() {
		return posterName;
	}


	public void setPosterName(String posterName) {
		this.posterName = posterName;
	}


	
//**************
	public long getId_db() {
		return id_db;
	}


	public void setId_db(long id_db) {
		this.id_db = id_db;
	}
//**************


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMinutes() {
		return minutes;
	}


	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
   
	
    
	
	
    
}
