package com.employee.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.employee.dao.EmployeeDAO;
import com.employee.util.Constants;

public class BackupUtil {

	
	public BackupUtil() {
        new File(Constants.BACKUP_DIR).mkdirs();
    }

    public String backup(AppBackup backup) {
        File file = new File(Constants.BACKUP_FILE);
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(backup);
            LogUtil.info("Application backup written to " + file.getPath() + " (" + backup + ")");
            return file.getPath();
        } catch (IOException e) {
            LogUtil.error("Backup failed: " + e.getMessage());
            throw new RuntimeException("Backup failed: " + e.getMessage(), e);
        }
    }

    public AppBackup restore() {
        File file = new File(Constants.BACKUP_FILE);
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            AppBackup backup = (AppBackup) ois.readObject();
            LogUtil.info("Application backup restored from " + file.getPath());
            return backup;
        } catch (IOException | ClassNotFoundException e) {
            LogUtil.error("Restore failed: " + e.getMessage());
            throw new RuntimeException("Restore failed: " + e.getMessage(), e);
        }
    }
    
//    public static void main(String[] args) {
//		BackupUtil backupUtil=new BackupUtil();
//		EmployeeDAO e=new EmployeeDAO();
//		AppBackup a=new AppBackup(e.findAll());
//		System.out.print(backupUtil.backup(a));
//	}
}
