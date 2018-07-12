package zgc.org.demo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Nick
 * Time2018/7/12 15:41
 * Description
 */
public class SudokuData {
    private List<Integer[][]> data = null;

    public List<Integer[][]> getData() {
        if (data == null) {
            data = new ArrayList<>();
        }
        return data;
    }

    public void setData(List<Integer[][]> data) {
        this.data = data;
    }
}
