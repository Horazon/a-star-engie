package pl.horazon.gamedev.astarengine.load;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class FieldParser {

    File file;

    public FieldParser(File file) {
        this.file = file;
    }

    private int[][] tab = new int[0][0];

    private List<int[]> list = new ArrayList<>();

    public int[][] getMap(){

        int[][] ctab = new int[10][list.size()];

        int i=0;

        for(int[] tmp: list){
            ctab[i] = tmp;
            i++;
        }

        return ctab;
    }

    public void parse() throws IOException {
        List<Field> fields = new ArrayList<Field>();

        List<String> lines = FileUtils.readLines(file, "UTF-8");

        for (String line : lines) {
            int rowSize = line.length();

            int[] row = new int[rowSize];

            for(int i=0; i<rowSize; i++){
                row[i]=Integer.valueOf(String.valueOf(line.charAt(i))).intValue();
            }

            list.add(row);
            System.out.println(Arrays.toString(row));
        }

        for(Field f : fields){
            System.out.println(f);
        }
    }

    public static List<Integer> extractNumbers(String txt){

        if(StringUtils.isEmpty(txt)){
            return new ArrayList<Integer>();
        }

        txt = txt.replaceAll("[^-?0-9]+", " ");

        List<String> list = Arrays.asList(txt.trim().split(" "));

        List<Integer> intList = Lists.newArrayList();

        try{
            intList = list.stream()
                    .map(i -> Integer.valueOf(i))
                    .collect(Collectors.toList());
        } catch (NumberFormatException ex){
            ex.printStackTrace();        }

        return intList;
    }
}
