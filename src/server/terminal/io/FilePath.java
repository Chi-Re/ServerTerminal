package server.terminal.io;

import server.terminal.io.files.FileType;
import server.terminal.util.exception.SeverRuntimeException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static server.terminal.ChireCore.log;

public class FilePath {
    /**File文件变量*/
    public File file;
    /**为了使用内部数据而添加*/
    public URL fileURL;
    /**关于文件是否为内部*/
    protected FileType type;

    public FilePath(File file){
        try {
            this.file = file;
            this.fileURL = file.toURI().toURL();
            this.type = FileType.local;
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    public FilePath(String path){
        try {
            this.file = new File(path);
            this.fileURL = this.file.toURI().toURL();
            this.type = FileType.local;
        } catch (MalformedURLException e){
            throw new RuntimeException(e);
        }
    }

    public FilePath(String path, FileType type){
        this.type = type;
        if (isInternal()) {
            //TODO WTF，居然忘了这个，file将为null
            //this.file = null;
            this.fileURL = this.getClass().getClassLoader().getResource(path);
            //无法使用的痛
            this.file = new File(this.fileURL.getFile());
        } else if (isLocal()){
            try {
                this.file = new File(path);
                this.fileURL = this.file.toURI().toURL();
            } catch (MalformedURLException e){
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("什么类型都不是？？？好好好");
        }
    }

    public File file() {
        return this.file;
    }

    /**打印时返回路径*/
    public String toString() {
        return this.file.getPath().replace('\\', '/');
    }

    public Boolean isInternal(){
        return FileType.internal == this.type;
    }
    public Boolean isLocal(){
        return FileType.local == this.type;
    }

    /**注意:内部文件的fileURL无法使用child*/
    public FilePath child(String path){
        if (isInternal()) throw new RuntimeException("内部不支持");
        FilePath cFile = new FilePath(this.file.getPath() + "/" + path);
        //如果文件夹不存在就创建
        if (this.file.isDirectory()) cFile.mkdir();
        return cFile;
    }

    /**创建文件夹*/
    public void mkdir(){
        if  (!file.exists() && file.isDirectory()) {
            if (file.mkdirs()){
                log.info(this.file + " 文件创建");
            }
        }
    }

    public String[] getFiles(String ext){
        if (!this.file.isDirectory()) throw new RuntimeException("非文件夹无法获取子文件");
        return this.file.list(new FileExtFilter(ext));
    }

    public List<FilePath> getFilePaths(String ext){
        String[] files = getFiles(ext);
        List<FilePath> FPList = new ArrayList<>(files.length);
        for (var f : files){
            FilePath FPl = this.child(f);
            if (!FPl.file.isDirectory()) FPList.add(FPl);
        }
        return FPList;
    }

    public void writeString(String content, boolean line){
        this.writeString(content, true, line);
    }

    public void writeString(String content, boolean append, boolean line) {
        // 判断是否是文件夹,不是文件则抛出异常
        if(!this.file.isDirectory() && this.type != FileType.internal) {
            try {
                if(!this.file.exists()) {
                    boolean nl = this.file.createNewFile();
                }

                FileOutputStream fileOut = new FileOutputStream(this.file, append);
                OutputStreamWriter writer = new OutputStreamWriter(fileOut, StandardCharsets.UTF_8);
                writer.write(content);
                if (line) writer.write("\n");
                writer.close();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        } else {
            throw new SeverRuntimeException("文件夹和内部文件无法写入！");
        }
    }

    public String read(String encoding){
        try {
            if(this.file.isFile() && this.file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(this.file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lt;
                StringBuilder rline = new StringBuilder();
                while((lt = bufferedReader.readLine()) != null){
                    rline.append(lt);
                }
                read.close();
                return rline.toString();
            }else{
                throw new SeverRuntimeException("找不到指定的文件");
            }
        } catch (Exception e) {
            throw new SeverRuntimeException("读取文件内容出错");
        }
    }

    public String readStr(){
        return this.read("utf-8");
    }

    public void deleteFile() {
        if (this.file.exists()) {
            if (this.file.isDirectory()){
                throw new RuntimeException("无法删除文件夹");
            } else if (this.type == FileType.internal){
                throw new RuntimeException("内部文件无法删除");
            }

            boolean deleted = this.file.delete();
            if (!deleted) {
                throw new RuntimeException("无法删除文件: " + this.file.getPath());
            }
        }
    }

    //TODO 暂时没有需求deleteFolder
    //public void deleteFolder() {
    //    if (this.file.exists() && this.file.isDirectory()) {
    //        File[] files = this.file.listFiles();
    //
    //        for (File f : files) {
    //            if (f.isDirectory()) {
    //                new FilePath(f).deleteFolder();
    //            } else {
    //                f.delete();
    //            }
    //        }
    //        this.file.delete();
    //    }
    //}

    static class FileExtFilter implements FilenameFilter {
        private final String ext;
        FileExtFilter(String ext){
            this.ext=ext;
        }

        public boolean accept(File dir, String name){
            return name.endsWith(ext);
        }
    }
}
