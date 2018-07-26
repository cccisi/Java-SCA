package dataloader;

import com.csvreader.CsvReader;

import java.io.IOException;
import java.util.ArrayList;

import static dataloader.FileLoader.path;

/**
 * @Auther: cccis
 * @Date: 7/16/2018 23:26
 * @Description:
 */
public class CsvLoader {
    public static ArrayList<Float> readCsv(String filePath){

        //trace数值，以mV为单位
        ArrayList<Float> trace = new ArrayList<>();
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(path + "\\" + filePath);

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                String[] strs = csvReader.getRawRecord().split(",");
                for (int i = 0; i < 10000; i++) {
                    trace.add(Float.valueOf(strs[i].substring(0,5)));
                }
                // 读这行的某一列
//                System.out.println(csvReader.get("Link"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return trace;
    }
}
