package com.danmall.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Log4j
public class FileUploadUtils {
	
	/*
	 * 파일 업로드
	 * 
	 * @Params
	 * String uploadPath: 기본 파일 업로드 경로
	 * String originName: 원본 파일명
	 * MultipartFile multipartFile: 첨부파일 정보
	 * 
	 * @return
	 * String uploadedFileName : 날짜 경로 + 파일 이름 (ex.\\2020\\03\\13\\uuid+fileName)
	 * 
	 */
	public static String uploadFile(String uploadPath, String originName, MultipartFile multipartFile) throws Exception{
	
		// 파일 경로 설정 ex) 날짜경로
		String savedPath = getDateFolder(uploadPath); // 날짜포맷의 폴더명. ex.\\2020\\03\\13
		// 파일명 설정 ex) uuid_파일명
		UUID uuid = UUID.randomUUID(); // 파일명 중복방지 목적
		String savedName = uuid.toString() + "_" + originName; // 고유한 파일명 생성
		// 설정한 정보로 빈 파일 생성
		File saveFile = new File(uploadPath + savedPath, savedName);
		
		multipartFile.transferTo(saveFile); // 기본 파일 작업

		String uploadedFileName = "";
		
		// 기본 첨부파일이 이미지, 일반파일에 따라서 썸네일 작업 진행.
		if(checkImageType(saveFile)) {
			// 썸네일 작업
			uploadedFileName = makeThumbNail(uploadPath,savedPath, savedName, multipartFile);
		} else {
			// 일반파일
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		
		return uploadedFileName;
	}
	
	// 날짜 포맷의 폴더명 생성. uploadFolder -> "C:\\dev\\upload"
	private static String getDateFolder(String uploadFolder) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		// 반환값으로 년월일 에 해당하는 폴더를 계층적으로 생성을 하는 목적 때문에, 각 운영체제에서 관리하는 경로 구분자로 바꿔야 한다.
		// 이 코드가 어느 운영체제에서 실행될지 모르니깐
		String osGetSepStr = str.replace("-", File.separator); // "2020-03-03" -> "-"만 바꾼다
		
		makeDir(uploadFolder, osGetSepStr); // 날짜명 폴더 생성 메서드
		
		return osGetSepStr; // "2020-04-15" -> "2020\04\15"
	}

	// 업로드 폴더와 날짜 포맷의 폴더명을 이용한 실제 생성 작업
	private static void makeDir(String uploadFolder, String osGetSepStr) {
		
		File uploadPath = new File(uploadFolder, osGetSepStr);
		
		// uploadFolderPath에 폴더명이 존재하지 않으면
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); // "C:\\dev\\upload\\" + "2020\04\15"
		}
		
	}
	
	// 특정한 파일이 이미지 타입인지 검사하는 메서드
	private static boolean checkImageType(File file) {
		
		String contentType;
		
		try {
			// 해당 파일의 MIME을 확인하고자 할 때 사용.
			contentType = Files.probeContentType(file.toPath()); // 입출력 관련 메서드
			
			return contentType.startsWith("image"); // contentType 안에 image/jpeg, image/png, image/gif,...등등이 들어있음.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/*
	 * uploadPath : 업로드폴더명(C:\\dev\\upload\\)
	 * savedPath : 날짜폴더명 (2021\\04\\09)
	 * uploadFileName : UUID를 적용한 파일명
	 * multipartFile : 첨부된 파일을 참조
	 */
	private static String makeThumbNail(String uploadPath, String savedPath, String uploadFileName, MultipartFile multipartFile) throws Exception {
		
		String thumbNailName = uploadPath + savedPath + File.separator + "s_" + uploadFileName;
		
		// 1)uploadPath->"C:\\upload\\tmp" + "2021/03/31" 2)uploadFileName->"s_" + "adfadf3eaw1fda23daf_abc.txt"
		FileOutputStream thumbnail = new FileOutputStream(new File(thumbNailName));
		
		// 썸네일 이미지 생성.(pom.xml에서 thumbnailator 라이브러리에서 기능 사용)
		// 원본 - multipartFile.getInputStream() : 입력스트림
		// 타켓 - thumbnail : 출력스트림
		Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
		thumbnail.close();
		
		
		return thumbNailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	// 이미지 파일 삭제
	public static void deleteFile(String uploadPath, String fileName) {
		
		// 2021/04/12/s_800b891b-a773-4cde-9c0a-1cdecd719a25_chacol_t.jfif
		
		//  날짜경로+ UUID_fileName
		String front = fileName.substring(0, 11); 	// 날짜 경로			2021/04/12/
		String end = fileName.substring(13); 		// UUID_fileName	800b891b-a773-4cde-9c0a-1cdecd719a25_chacol_t.jfif
		String origin = front + end;				// 원본 경로			2021/04/12/800b891b-a773-4cde-9c0a-1cdecd719a25_chacol_t.jfif
		
		new File(uploadPath+origin.replace('/', File.separatorChar)).delete(); 		// 원본 파일 지우기
		new File(uploadPath+fileName.replace('/', File.separatorChar)).delete(); 	// 썸네일 파일 지우기
	}

	
	/*
	 * 썸네일 파일명 -> 원래 파일명
	 * ex) 2020/03/20/s_UUID파일명 -> /2020/03/20/UUID파일명
	 */
	public static String thumbToOriginName(String thumbnailName) {
		String front = thumbnailName.substring(0, 11); 	// 날짜 경로
		String end = thumbnailName.substring(13); 		// UUID_fileName
		
		return front+end;
		
	}
	
	private static String makeIcon(String uploadPath, String path, String fileName) throws Exception{
		String iconName = uploadPath + path + File.separator + fileName;
		
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
	
	// 이미지를 출력하는 기능(이미지 업로드 폴더가 프로젝트 외부에 존재하기 때문에)
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName){
		
		File file = new File(uploadPath + fileName); // 이미지파일을 대상으로 File객체 생성 //수정
		
		log.info("file: " + file);
		
		ResponseEntity<byte[]> result = null;
		
		// 패킷의 header(body와 관련된 정보들)와 body(실제 데이터)로 구성
		// body : @ResponseBody가 이미지소스를 가리키는 <byte[]>를 body 저장한다.
		// 브라우저에서 서버로부터 보내온 패킷(header+body) 중 header파트에 내용을 참고하여, body파트를 해석하게 된다.
		// header을 건드는 작업 : HttpHeaders header = new HttpHeaders();
		// body 안에 들어가는 내용 : byte[]
		HttpHeaders header = new HttpHeaders();
		
		// 클라이언트에게 보낼 데이터의 MIME정보(image/png, image/jpeg, image/gif)를 패킷의 Header파트부분에 설정.
		// probeContentType() : 적절한 MIME 타입 데이터를 Http의 헤더 메시지에 포함할 수 있도록 처리합니다.
		try {
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
