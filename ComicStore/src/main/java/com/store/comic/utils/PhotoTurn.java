package com.store.comic.utils;

import java.util.Base64;

import com.store.comic.domain.BooksBean;
import com.store.comic.dto.PhotoTypeDto;



public class PhotoTurn {
	public static PhotoTypeDto base64ToByte(String base) {
		PhotoTypeDto dto=new PhotoTypeDto();
		String mimeType=null;
		byte[] photoByte=null;
		//data:image/png;base64,
//		System.out.println("base本體:"+base);
		if(base!=null) {
			
			int index = base.indexOf(",");
			if(index!=-1) {
				
				mimeType=base.substring(0,index+1);
				base=base.substring(index+1);

				Base64.Decoder decoder = Base64.getDecoder();
				photoByte = decoder.decode(base);
				if(mimeType!=null&&photoByte!=null) {
					dto.setMimeType(mimeType);
					dto.setPhoto(photoByte);
					return dto;
				}
			}
		}
		return null;
	}
	public static String byteToBase64(BooksBean book) {
		if(book!=null) {
			Base64.Encoder encoder = Base64.getEncoder();
			String photo = encoder.encodeToString(book.getPhoto());
			String mimeType=book.getMimeType();
			photo=mimeType+photo;
			return photo;
		}
		return null;
	}
}
