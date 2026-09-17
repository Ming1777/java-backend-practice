package day22;

import java.util.*;

public class GroupAnagrams0917 {

    public static void main(String[] args) {
        String[] words = {"eat", "tea", "eat", "", "", "a"};

        Map<String, List<String>> groups = new HashMap<>();

        for (String word : words) {
            char[] letters = word.toCharArray();
            Arrays.sort(letters);
            String key = new String(letters);

            groups.putIfAbsent(key, new ArrayList<>());


            groups.get(key).add(word);
        }

        System.out.println(groups.values());
    }
}