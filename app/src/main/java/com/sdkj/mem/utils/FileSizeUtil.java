package com.sdkj.mem.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdkj.mem.bean.CheckRecord;
import com.sdkj.mem.bean.User;


import android.os.Environment;
import android.util.Log;

public class FileSizeUtil implements FileFilter{
	 public static final int SIZETYPE_B = 1;// 获取文件大小单位为B的double值
	 public static final int SIZETYPE_KB = 2;// 获取文件大小单位为KB的double值
	 public static final int SIZETYPE_MB = 3;// 获取文件大小单位为MB的double值
	 public static final int SIZETYPE_GB = 4;// 获取文件大小单位为GB的double值
	 /**
	  * 获取文件指定文件的指定单位的大小
	  * 
	  * @param filePath
	  *            文件路径
	  * @param sizeType
	  *            获取大小的类型1为B、2为KB、3为MB、4为GB
	  * @return double值的大小
	  */
	 public static double getFileOrFilesSize(String filePath, int sizeType) {
	  File file = new File(filePath);
	  long blockSize = 0;
	  try {
	   if (file.isDirectory()) {
	    blockSize = getFileSizes(file);
	   } else {
	    blockSize = getFileSize(file);
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	   Log.e("获取文件大小", "获取失败!");
	  }
	  return FormetFileSize(blockSize, sizeType);
	 }
	 /**
	  * 调用此方法自动计算指定文件或指定文件夹的大小
	  * 
	  * @param filePath
	  *            文件路径
	  * @return 计算好的带B、KB、MB、GB的字符串
	  */
	 public static String getAutoFileOrFilesSize(String filePath) {
	  File file = new File(filePath);
	  long blockSize = 0;
	  try {
	   if (file.isDirectory()) {
	    blockSize = getFileSizes(file);
	   } else {
	    blockSize = getFileSize(file);
	   }
	  } catch (Exception e) {
	   e.printStackTrace();
	   Log.e("获取文件大小", "获取失败!");
	  }
	  return FormetFileSize(blockSize);
	 }
	 /**
	  * 获取指定文件大小
	  * 
	  * @return
	  * @throws Exception
	  */
	 public static long getFileSize(File file) throws Exception {
	  long size = 0;
	  if (file.exists()) {
	   FileInputStream fis = null;
	   fis = new FileInputStream(file);
	   size = fis.available();
	  } else {
	   file.createNewFile();
	   Log.e("获取文件大小", "文件不存在!");
	  }
	  return size;
	 }
	 /**
	  * 获取指定文件夹
	  * 
	  * @param f
	  * @return
	  * @throws Exception
	  */
	 public static long getFileSizes(File f) throws Exception {
	  long size = 0;
	  File flist[] = f.listFiles();
	  for (int i = 0; i < flist.length; i++) {
	   if (flist[i].isDirectory()) {
	    size = size + getFileSizes(flist[i]);
	   } else {
	    size = size + getFileSize(flist[i]);
	   }
	  }
	  return size;
	 }
	 /**
	  * 转换文件大小
	  * 
	  * @param fileS
	  * @return
	  */
	 public static String FormetFileSize(long fileS) {
	  DecimalFormat df = new DecimalFormat("#.00");
	  String fileSizeString = "";
	  String wrongSize = "0B";
	  if (fileS == 0) {
	   return wrongSize;
	  }
	  if (fileS < 1024) {
	   fileSizeString = df.format((double) fileS) + "B";
	  } else if (fileS < 1048576) {
	   fileSizeString = df.format((double) fileS / 1024) + "KB";
	  } else if (fileS < 1073741824) {
	   fileSizeString = df.format((double) fileS / 1048576) + "MB";
	  } else {
	   fileSizeString = df.format((double) fileS / 1073741824) + "GB";
	  }
	  return fileSizeString;
	 }
	 /**
	  * 转换文件大小,指定转换的类型
	  * 
	  * @param fileS
	  * @param sizeType
	  * @return
	  */
	 private static double FormetFileSize(long fileS, int sizeType) {
	  DecimalFormat df = new DecimalFormat("#.00");
	  double fileSizeLong = 0;
	  switch (sizeType) {
	  case SIZETYPE_B:
	   fileSizeLong = Double.valueOf(df.format((double) fileS));
	   break;
	  case SIZETYPE_KB:
	   fileSizeLong = Double.valueOf(df.format((double) fileS / 1024));
	   break;
	  case SIZETYPE_MB:
	   fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576));
	   break;
	  case SIZETYPE_GB:
	   fileSizeLong = Double.valueOf(df
	     .format((double) fileS / 1073741824));
	   break;
	  default:
	   break;
	  }
	  return fileSizeLong;
	 }
	 
	 
	 /*** 获取文件夹大小 ***/
	 public static long getFileDirSize(File f) throws Exception {
	 long size = 0;
	 File flist[] = f.listFiles();
	 for (int i = 0; i < flist.length; i++) {
	 if (flist[i].isDirectory()) {
	 size = size + getFileSize(flist[i]);
	 } else {
	 size = size + flist[i].length();
	 }
	 }
	 return size;
	 } 
	 
	 /*** 获取文件个数 ***/
	 public static long getlist(File f) {// 递归求取目录文件个数
	 long size = 0;
	 File flist[] = f.listFiles();
	 size = flist.length;
	 for (int i = 0; i < flist.length; i++) {
	 if (flist[i].isDirectory()) {
	 size = size + getlist(flist[i]);
	 size--;
	 }
	 }
	 return size;
	 } 
	 
	 
    public static  synchronized boolean saveFile(boolean isRoot,String str,String fileName) {
		boolean isOk = false;
        String filePath = null;  
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  
       // filePath = Constant.Data_PATH + File.separator + fileName;
        if (isRoot) {  
            filePath = "/"+fileName;  
        } else  
        	filePath = Constant.Data_PATH + File.separator + fileName;  
          
        try {  
            File file = new File(filePath);  
            if (!file.exists()) {  
                File dir = new File(file.getParent());  
                dir.mkdirs();  
                file.createNewFile();  
            }  
            FileOutputStream outStream = new FileOutputStream(file);  
            outStream.write(str.getBytes());  
            outStream.close();
			isOk = true;
        } catch (Exception e) {  
            e.printStackTrace();  
        }

		return isOk;
    }
    
    //List文件
    public static boolean  saveListFile(List<User> users,String fileName) {
		boolean isOk = true;
        String filePath = null;  
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  
//        if (hasSDCard) {  
//            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + fileName;  
//        } else  
//            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + fileName;  
        
        filePath = Constant.Data_PATH + File.separator + fileName;
        Gson gson = new Gson(); 
        try {  
            File file = new File(filePath);  
            if (!file.exists()) {  
                File dir = new File(file.getParent());  
                dir.mkdirs();  
                file.createNewFile();  
            }else{
            	String jsonString = ReadTxtFile(filePath);
            	Type listType = new TypeToken<LinkedList<User>>() {
        		}.getType();
        		
        		 LinkedList<User> fUsers = gson.fromJson(jsonString, listType);
        		//List fUsers = gson.fromJson(jsonString, listType);
        		users.addAll(fUsers);
            } 
            String str = gson.toJson(users);
            FileOutputStream outStream = new FileOutputStream(file);  
            outStream.write(str.getBytes());  
            outStream.close();  
        } catch (Exception e) {  
            isOk = false;
			e.printStackTrace();
        }
		return  isOk;
    }
    
    //List文件
    public static synchronized boolean saveUserInfo(CheckRecord myuser,String fileName) {
		boolean isOk = false;
        String filePath = null;  
        List<CheckRecord> users = new ArrayList<CheckRecord>();
        filePath = Constant.Data_PATH + fileName;
//        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);  
//        if (hasSDCard) {  
//            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + fileName;  
//        } else  
//            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + fileName;  
       // Gson gson = new Gson(); 
        Gson gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.PROTECTED) // <---
        .create();
        try {  
            File file = new File(filePath);  
            if (!file.exists()) {  
                File dir = new File(file.getParent());  
                dir.mkdirs();  
                file.createNewFile();  
            }else{
            	String jsonString = ReadTxtFile(filePath);
            	if(!jsonString.equals("-1")){
            		
            		if(StringEmpty.isGoodJson(jsonString)){
            	Type listType = new TypeToken<LinkedList<CheckRecord>>() {
        		}.getType();
        		
        		 LinkedList<CheckRecord> fUsers = gson.fromJson(jsonString, listType);
        		users.addAll(fUsers);
            		}
            	}
            } 
            if(users.size()>0){

            	for(int p=0;p<users.size();p++){
					CheckRecord user = users.get(p);
            		if(user.getTaskid().equals(myuser.getTaskid())){
            			//用户存在
            			users.remove(user);
            		}
            	}
            }
            users.add(myuser);
            String str = gson.toJson(users);
            FileOutputStream outStream = new FileOutputStream(file);  
            outStream.write(str.getBytes());  
            outStream.close();
			isOk = true;
        } catch (Exception e) {  
            e.printStackTrace();  
        }

		return isOk;
    }
    
    
    //过滤掉隐藏文件
	@Override
	public boolean accept(File dirName) {
		if(!dirName.getName().startsWith(".")){
		    return true;
		}else{
			return false;
		}
	}
 
    
	//读取文本文件中的内容
    public static String ReadTxtFile(String strFilePath)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
            //打开文件
            File file = new File(path);
            //如果path是传递过来的参数，可以做一个非目录的判断
            if (file.isDirectory())
            {
                Log.d("TestFile", "The File doesn't not exist.");
                content ="-1";
            }
            else
            {
                try {
                	long last = System.currentTimeMillis();
                	System.out.println("开始读文件****"+last);
                    InputStream instream = new FileInputStream(file);
                    if (instream != null)
                    {
                        InputStreamReader inputreader = new InputStreamReader(instream);
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line;
                        //分行读取
                        while (( line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        } 
                        long end = System.currentTimeMillis();
                        System.out.println("结束读文件****"+end);
                        System.out.println("读文件用时****end-last="+(end-last));
                        instream.close();
                    }
                }
                catch (java.io.FileNotFoundException e)
                {
                    Log.d("TestFile", "The File doesn't not exist.");
                    content ="-1";
                }
                catch (IOException e)
                {
                     Log.d("TestFile", e.getMessage());
                     content ="-1";
                }
            }
            return content;
    }

   
    /**  
     * 复制单个文件  
     * @param oldPath String 原文件路径 如：c:/a.txt  
     * @param newPath String 复制后路径 如：f:/a.txt  
     * @return boolean  
     */   
   public static String copyFile(String oldPath, String newPath) {  
	   InputStream inStream = null;
	   FileOutputStream fs = null;
	   String error = "文件copy错误";
	   String fileName="";
       try {   
           int bytesum = 0;   
           int byteread = 0;   
           File oldfile = new File(oldPath);   
           if (!oldfile.exists()) {return error;}  
           if (!oldfile.isFile()) {return error;}  
           if (!oldfile.canRead()) {return error;}  
           if (oldfile.exists()) { //文件存在时   
               inStream = new FileInputStream(oldPath); //读入原文件   
				File folder = new File(newPath);
				if (!folder.exists() || !folder.isDirectory()) {
					folder.mkdirs();
				} 
				//如果已经存在下载的文件，则先删除这些文件
//					deleteFile(getFilePath());
//					deleteFile(getTempFilePath());
				fileName=newPath+"/"+oldPath.substring(oldPath.lastIndexOf("/")+1);
				Log.e("file", "file:"+fileName);
				File newFile = new File(fileName);
				if(newFile.exists()){
					newFile.delete();
				}
				newFile.createNewFile();
               fs = new FileOutputStream(newFile);   
               byte[] buffer = new byte[1444];   
               int length;   
               while ( (byteread = inStream.read(buffer)) != -1) {   
                   bytesum += byteread; //字节数 文件大小   
                   System.out.println(bytesum);   
                   fs.write(buffer, 0, byteread);   
               }   
               //inStream.close();   
               fs.flush();
           }   
           return fileName;
       }   
       catch (Exception e) {   
           System.out.println("复制单个文件操作出错");   
           e.printStackTrace();   
  
       }  finally{
    	   
    	   try {
			if(inStream != null){
				   inStream.close();
			   }
			
			if(fs != null){
				fs.close();
			   }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       } 
       return error;
  
   }   
   
   
   public static String copyExamFile(String oldPath, String newPath) {  
	   InputStream inStream = null;
	   FileOutputStream fs = null;
	   String error = "文件copy错误";
	   String fileName="";
       try {   
           int bytesum = 0;   
           int byteread = 0;   
           File oldfile = new File(oldPath);   
           if (!oldfile.exists()) {return error;}  
           if (!oldfile.isFile()) {return error;}  
           if (!oldfile.canRead()) {return error;}  
           if (oldfile.exists()) { //文件存在时   
               inStream = new FileInputStream(oldPath); //读入原文件   
				File folder = new File(newPath);
				if (!folder.exists() || !folder.isDirectory()) {
					folder.mkdirs();
				} 
				//如果已经存在下载的文件，则先删除这些文件
//					deleteFile(getFilePath());
//					deleteFile(getTempFilePath());
				fileName=newPath+"/exam.txt";
				Log.e("file", "file:"+fileName);
				File newFile = new File(fileName);
				if(newFile.exists()){
					newFile.delete();
				}
				newFile.createNewFile();
               fs = new FileOutputStream(newFile);   
               byte[] buffer = new byte[1444];   
               int length;   
               while ( (byteread = inStream.read(buffer)) != -1) {   
                   bytesum += byteread; //字节数 文件大小   
                   System.out.println(bytesum);   
                   fs.write(buffer, 0, byteread);   
               }   
               //inStream.close();   
               fs.flush();
           }   
           return fileName;
       }   
       catch (Exception e) {   
           System.out.println("复制单个文件操作出错");   
           e.printStackTrace();   
  
       }  finally{
    	   
    	   try {
			if(inStream != null){
				   inStream.close();
			   }
			
			if(fs != null){
				fs.close();
			   }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       } 
       return error;
  
   }   
    
}